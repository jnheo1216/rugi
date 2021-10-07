<template>
  <div class="game-body">
    <div class="col-8 row" style="  height: 92vh;">
      <div class="col">
        <div class="p-3 col">
          <UserList v-if="Object.keys($store.state.room).length" />
        </div>
        <div class="col">
          <component :is="currentView"></component>
        </div>
      </div>
    </div>
    <div class="col-4 p-3">
      <Chat :chatList="chatList" />
    </div>
  </div>
</template>

<script>
import UserList from '@/components/user/UserList.vue'
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
    Chat,
    UserList
  },
  data: function() {
    return {
      //currentView의 default값은 lobby로 해야 할 것인가?
      currentView: 'Lobby',
      // 채팅 정보를 받아서 자식인 Chat 컴포넌트에 넘겨주기 위함
      chatList: [],
      roomSubscription: null,
      chatSubscription: null,
      quizSubscription: null,
      roomId : this.$route.query["room"]
    }
  },
  methods: {
    connect: async function() {
      // 여기서 방을 먼저 조회할까..?
      // 방을 조회하고 풀방이면 강퇴해버리기
      const findResult = await this.sendFindRoom() // 방 조회 코드. 풀방 조회를 위함 (낭비지만 시간이 없음)
      if (!findResult) {
        this.$router.push({ name: "Home", query: { room: this.roomId } });
        return
      }



      let socket = new SockJS(process.env.VUE_APP_STOMP_URL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        async () => { // 소켓 연결 성공시 실행되는 콜백
        // stomp객체를 어느 곳에서든 쓸 수 있도록 state에 저장한다.
          this.$store.dispatch("setStompClient", this.stompClient);
          this.connected = true
          await this.chatSubscribe() // 메시지 구독 코드
          await this.roomSubscribe() // 방 구독 코드
          await this.sendJoinRequest()
          this.sendJoinMessage() // 입장메시지 코드
          this.addEvent()
        },
        error => {
          console.log('소켓 연결 실패', error)
        }
      )
    },
    // 소켓 구독 메소드 1
    roomSubscribe: async function () {
      this.roomSubscription = await this.stompClient.subscribe('/sub/info/room/' + this.roomId, info => {
        let response = JSON.parse(info.body)
        console.log(response);

        if (!Object.keys(this.$store.state.room).length || (this.$store.state.room["timestamp"] < response["timestamp"])) {
          let superUser = response["superUser"]
          console.log(this.$store.state.room.id);
          // 여기서 내가 superuser인지 확인해서 변수 할당해주고 delete 해주기
          this.$store.dispatch('setSuper', superUser)
          delete response["superUser"]
          // 받아온 룸정보 데이터 가지고 다시 룸 랜더링 해주는 로직 필요 GameSetting, Init, Play각 필요한 시점별로 달라짐
          this.$store.dispatch('setRoom', response)
          console.log('여기서 방 정보 변경되는게 구독되는것', this.$store.state.room.status)
        }else{
          console.log("시간이 앞서지 않아 갱신되지 않았음");
        }
        console.log("roomSubscribe End", this.$store.state.room.id);
      })
    },
    // 소켓 구독 메소드 2
    chatSubscribe: async function () {
      this.chatSubscription = await this.stompClient.subscribe('/sub/chat/room/' + this.roomId, chat => {
        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        console.log("chat : ", JSON.parse(chat.body));
        const {writer, message} = JSON.parse(chat.body)
        this.chatList.push({writer, message})
      })
    },
    sendFindRoom: async function () {
      return await axios({
        method: 'get',
        url: '/room',
        params: {
          roomId: this.roomId
        }
      }).then((res) => {
        console.log('방 입장시 입장 데이터 받아오기', res.data)
        return true
      }).catch((err) => {
        console.log(err.response)
        return false
      })
    },
    // 유저 목록 변경을 위한 http 요청
    sendJoinRequest: async function () {
      await axios({
        method: 'post',
        url: '/room/user',
        data: {
          avatar: this.$store.state.avatar,
          nickname: this.$store.state.nickname,
        },
        params: {
          roomId: this.roomId
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
          roomId: this.roomId,
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
    // 방을 떠나는 함수를 실행시켜주는 메소드
    leaveRoom: async function () {
      console.log("Send message: bye")
      // 구독을 먼저 끊어주어야 한다.
      // leaveRequest로 뒤늦게 방정보를 받아와서 비워진 room객체가 재할당 되는 것을 막기 위함
      await this.roomSubscription.unsubscribe();
      await this.chatSubscription.unsubscribe();
      await this.quizSubscription.unsubscribe();
      await this.leaveMessage() // 퇴장한다는 소켓메시지
      console.log(this.roomId);
      await this.leaveRequest() // http 요청으로 방을 퇴장하는 요청도 보내주어야 한다.
      this.$store.dispatch("setRoom", {})
      this.$store.dispatch("setStompClient", "");
    },
    // 방을 떠났다는 메시지를 모두에게 보내주는 Socket 메소드
    leaveMessage: function () {
      this.$store.state.stompClient.send(
        '/pub/chat/message',
        JSON.stringify({
          roomId: this.roomId,
          writer: this.$store.state.nickname,
          message: `${this.$store.state.nickname}님이 떠났습니다.`
        })
      )
    },
    // 방에서 유저를 제거하고, 갱신된 새로운 데이터를 모든 유저에게 뿌려주는 Http 메소드
    leaveRequest: async function () {
      console.log(this.roomId);
      await axios({
        method: 'delete',
        url: '/room/user',
        params: {
          roomId: this.roomId
        }
      }).then((res) => {
        console.log('방 퇴장', res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
  },
  computed: {
    checkCurrentView: function () {
      return this.$store.getters.currentView
    }
  },
  watch: {
    checkCurrentView(val) {
      console.log('현재 cruuentView를', val, '로 변화')
      this.currentView = val
    }
  },
  created: async function  () {
    console.log("1", this.$route.query["room"]);
    console.log("Game.vue Created Start: "+this.$store.state.room);
    this.connect()
  },
  beforeUnmount: function () {
    console.log("unMount")
    this.leaveRoom()
  }
}
</script>

<style>
.game-body {
  background-color: white;
  /* display: flex;
  flex-direction: column; */
  align-items: center;
  align-self: center;
  justify-content: center;
  width: 96vw;
  height: 92vh;
  border-radius: 1rem;
  /* display: flex !important;
  flex-direction: row;
  align-items: center; */
}
</style>
