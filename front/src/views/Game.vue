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
    // 새로 서버가 갱신되는 경우 axios의 헤더가 날아간다
    // 1. 새로고침
    // 2. 홈으로 이동 후 vuex를 비우고 새로고침 다시
    enterSession: function () {
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
        // 실제로는 소켓에서 받아오고, 여기는 필요없음. 소켓에서 받아서 방을 갱신해주는 로직 짜기
        this.$store.dispatch('setRoom', res.data.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
    connect() {
      // const serverURL = 'https://j5b106.p.ssafy.io:443/stomp/room'
      const serverURL = 'http://localhost:8080/stomp/room'
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      //console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => { // 소켓 연결 성공시 실행되는 콜백
        // stomp객체를 어느 곳에서든 쓸 수 있도록 state에 저장한다.
          this.$store.dispatch("setStompClient", this.stompClient);
          this.connected = true
          console.log(frame);
          this.roomSubscribe() // 방 구독 코드
          this.chatSubscribe() // 메시지 구독 코드
          this.dodoon(socket) // 두둥 등장 코드
        },
        error => {
          console.log('소켓 연결 실패', error)
        }
      )
    },
    viewChange : function (viewName) {
      this.currentView = viewName
    },
    roomSubscribe: function () {
      this.stompClient.subscribe('/sub/room_info/room/' + this.$route.query["room"], info => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        let recvData = JSON.parse(info.body)
        // 받아온 룸정보 데이터 가지고 다시 룸 랜더링 해주는 로직 필요 GameSetting, Init, Play각 필요한 시점별로 달라짐
        this.roomInfo = recvData.message // 수정필요함
        this.$store.dispatch('setRoom', this.roomInfo)
      })
    },
    chatSubscribe: function () {
      this.stompClient.subscribe('/sub/chat/room/' + this.$route.query["room"], chat => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        console.log("chat : ", JSON.parse(chat.body));
        const {writer, message} = JSON.parse(chat.body)
        this.chatList.push({writer, message})
      })
    },
    dodoon: function (socket) {
      // 여긴 두둥등장하는 부분
      if (this.stompClient && this.stompClient.connected) {
        const greeting = {
          roomId: this.$route.query["room"],
          writer: this.nickname,
          message: socket._transport.url
        }
        console.log(greeting)
        this.stompClient.send('/pub/chat/enter', JSON.stringify(greeting))
      }
    }
  },
  created: function () {
    this.connect()
    this.enterSession()
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
