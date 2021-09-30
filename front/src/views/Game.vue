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
        console.log(res)
        console.log('리스폰스 데이터', res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    },
    roomUpdate: function() {
      // v-model같은걸로 서로...와따가따가능하게
      // 변경 필요
      const message = this.$store.state.message
      console.log('방정보 이걸로 바꿀거임', message)
      this.$store.dispatch('setMessage', message)
      // 업데이트한 방정보 퍼블리쉬
      console.log('방정보 변경')
      this.stompClient.send('/pub/room/info', JSON.stringify(message))
      this.roomInfo = JSON.stringify(message)
    },
    connect() {
      const serverURL = 'https://j5b106.p.ssafy.io:443/stomp/chat'
      // const serverURL = 'http://localhost:8080/api/stomp/chat'

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
          })
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error)
          this.connected = false
        }
      )
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
