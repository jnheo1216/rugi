<template>
  <div class="gameBody">
    <div>
      <component :is="currentView" @viewChange="viewChange">
      </component>
      <button @click="roomUpdate">socket test</button>
      <div>{{ roomInfo }}</div>
    </div>
    <div>
      <Chat />
    </div>
    <div v-if="currentView==='Lobby'">

      <Button
        icon="pi pi-arrow-left"
        class="p-button-rounded p-button-text"
        @click="roomUpdate"
      >
      Room Setting!
      </Button>

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
      roomId: this.$store.state.roomId
    }
  },
  methods: {
    // query parameter를 가지고 있는 경우만 고려한다.
    enterSession: function () {
      axios({
        method: 'post',
        url: '/room/user',
        data: {
          'avatar': this.$store.state.avatar,
          'nickname': this.$store.state.nickname,
        },
        params: {
          roomId: this.$store.state.roomId
        }
      }).then((res) => {
        this.$router.push( {name : 'Game', query: {room: this.$store.state.roomId}})
        this.$store.dispatch('setUserList', res.data.data.userList)
        console.log('리스폰스 데이터', res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
    connect() {
      const serverURL = 'https://j5b106.p.ssafy.io:443/stomp/room'
      // const serverURL = 'http://localhost:8080/stomp/room'

      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.$store.state.stompClient = this.stompClient;
      //console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true
          console.log('방 정보 교환 소켓 연결 성공', frame)
          // 룸객체정보 왔다갔다할 토픽 구독
          this.stompClient.subscribe('/sub/room_info/room/' + this.roomId, info => {
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            let recvData = JSON.parse(info.body)
            console.log('이거 뜨면 제대로 받은거임 =======', recvData)
            // 받아온 룸정보 데이터 가지고 다시 룸 랜더링 해주는 로직 필요 GameSetting, Init, Play각 필요한 시점별로 달라짐
            this.roomInfo = recvData.message // 수정필요함
            this.$store.dispatch('setMessage', this.roomInfo)
          })
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error)
          this.connected = false
        }
      )
    },
    roomUpdate : function () {
      axios({
        method: 'patch',
        url: '/room',
        data: {
          maxRound: this.$store.state.message.maxRound,
          roundTime: this.$store.state.message.roundTime,
        },
        params: {
          roomId: this.$store.state.roomId
        }
      }).then((res) => {
        console.log('방 정보 업데이트 성공')
        console.log(res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
    viewChange : function (viewName) {
      this.currentView = viewName
    }
  },
  created: function() {
    this.connect()
  },
  mounted: function () {
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
