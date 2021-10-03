<template>
  <div class="gameBody">
    <div>
      <component :is="currentView" @viewChange="viewChange">
      </component>
      <div>{{ roomInfo }}</div>
    </div>
    <div>
      <Chat :chatList="chatList" />
    </div>
  </div>
</template>

<script>
import Lobby from '@/components/game/Lobby.vue'
import GameInit from '@/components/game/GameInit.vue'
import GamePlay from '@/components/game/GamePlay.vue'
import GameResult from '@/components/game/GameResult.vue'
import Chat from '@/components/game/Chat.vue'

import axios from'axios'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
export default {
  name: 'Game',
  components: {
    Lobby,
    GameInit,
    GamePlay,
    GameResult,
    Chat
  },
  data: function() {
    return {
      //currentView의 default값은 lobby로 해야 할 것인가?
      currentView: 'Lobby',
      roomInfo: {},
      // 채팅 정보를 받아서 자식인 Chat 컴포넌트에 넘겨주기 위함
      chatList: [],
    }
  },
  methods: {
    connect() {
      console.log("connect 시작");
      let socket = new SockJS(process.env.VUE_APP_STOMP_URL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => { // 소켓 연결 성공시 실행되는 콜백
        // stomp객체를 어느 곳에서든 쓸 수 있도록 state에 저장한다.
          this.$store.dispatch("setStompClient", this.stompClient);
          this.connected = true
          console.log(frame);
          this.roomSubscribe() // 방 구독 코드
          this.chatSubscribe() // 메시지 구독 코드
          this.sendJoinMessage() // 입장메시지 코드
          this.addEvent()
        },
        error => {
          console.log('소켓 연결 실패', error)
        }
      )
      console.log("connect 종료");
    },
    // 동적 컴포넌트 관련 메소드
    viewChange : function (viewName) {
      this.currentView = viewName
    },
    // 소켓 구독 메소드 1
    roomSubscribe: function () {
      this.stompClient.subscribe('/sub/room_info/room/' + this.$route.query["room"], info => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        let recvData = JSON.parse(info.body)
        // 받아온 룸정보 데이터 가지고 다시 룸 랜더링 해주는 로직 필요 GameSetting, Init, Play각 필요한 시점별로 달라짐
        this.roomInfo = recvData.message // 수정필요함
        this.$store.dispatch('setRoom', this.roomInfo)
      })
    },
    // 소켓 구독 메소드 2
    chatSubscribe: function () {
      this.stompClient.subscribe('/sub/chat/room/' + this.$route.query["room"], chat => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        console.log("chat : ", JSON.parse(chat.body));
        const {writer, message} = JSON.parse(chat.body)
        this.chatList.push({writer, message})
      })
    },
    // 유저 목록 변경을 위한 http 요청
    sendJoinRequest: function () {
      axios({
        method: 'post',
        url: '/room/user',
        data: {
          avatar: this.$store.state.avatar,
          nickname: this.$store.state.nickname,
        },
        params: {
          roomId: this.$route.query["room"]
        }
      }).then((res) => {
        console.log('방 입장시 입장 데이터 받아오기', res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
    // 유저가 입장했음을 알리는 메시지를 모두에게 보내는 Socket 요청
    sendJoinMessage: function () {
      console.log("Send message: hi")
      this.$store.state.stompClient.send(
        '/pub/chat/message',
        JSON.stringify({
          roomId: this.$route.query["room"],
          writer: this.$store.state.nickname,
          message: `${this.$store.state.nickname}님이 입장했습니다.`
        })
      )
    },
    // 브라우저 종료시 방을 떠나도록 하는 이벤트 추가
    addEvent: function () {
      window.addEventListener('beforeunload', this.leaveRoom);
    },
    // 브라우저 종료가 아닌 다른 방식으로 나갈 경우 이벤트를 제거해주어야 한다.
    // 미구현 상태
    removeEvent: function () {
      // window.removeEventListener('beforeunload', this.sendLeave);
    },
    // 방을 떠나는 함수를 실행시켜주는 메소드
    leaveRoom: function () {
      console.log("Send message: bye")
      this.leaveMessage() // 퇴장한다는 소켓메시지
      this.leaveRequest() // http 요청으로 방을 퇴장하는 요청도 보내주어야 한다.

    },
    // 방을 떠났다는 메시지를 모두에게 보내주는 Socket 메소드
    leaveMessage: function () {
      this.$store.state.stompClient.send(
        '/pub/chat/message',
        JSON.stringify({
          roomId: this.$route.query["room"],
          writer: this.$store.state.nickname,
          message: `${this.$store.state.nickname}님이 떠났습니다.`
        })
      )
    },
    // 방에서 유저를 제거하고, 갱신된 새로운 데이터를 모든 유저에게 뿌려주는 Http 메소드
    leaveRequest: function () {
      axios({
        method: 'delete',
        url: '/room/user',
        params: {
          roomId: this.$route.query["room"]
        }
      }).then((res) => {
        console.log('방 퇴장', res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    }
  },
  // 시작 시 소켓연결과 유저 입장, 유저입장메시지 등을 수행한다
  created: async function  () {
    // 순서가 바뀌면 안된다...?아닌데
    console.log("소켓 연결 시작 전");
    await this.connect()
    console.log("소켓 연결 종료, http 요청 전");
    this.sendJoinRequest()
    console.log("http 요청 후");
  },
  unmounted: function () {
    this.$store.dispatch("setRoom", {})
  }
}
</script>

<style>
.gameBody {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
