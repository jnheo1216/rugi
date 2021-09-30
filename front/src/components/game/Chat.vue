<template>
  <div id="app">
    내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    >
    <ScrollPanel
      style="width: 100%; height: 200px"
      class="custom"
    >
      <div
        v-for="(chat, idx) in chatList"
        :key="idx"
      >
        <p>
          {{ chat.writer }} {{ chat.message }}
        </p>
        <ScrollTop />
      </div>
    </ScrollPanel>
  </div>
</template>

<script>
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client'

export default {
  components: {
  },
  name: 'Chat',
  data() {
    return {
      roomId: this.$route.query.room,
      nickname: this.$store.state.nickname,
      message: '',
      chatList: [],
      roomInfo: {},
    }
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        //console.log('recieve list', this.chatList)
        this.message = ''
      }
    },
    send() {
      //console.log("Send message:" + this.message)
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          roomId: this.roomId,
          writer: this.nickname,
          message: this.message
        }
        JSON.stringify(msg)
        //console.log(msg)
        this.stompClient.send('/pub/chat/message', JSON.stringify(msg))
      }
    },
    connect() {
      // const serverURL = 'https://j5b106.p.ssafy.io:443/stomp/chat'
      // const serverURL = 'http://localhost:8080/api/stomp/chat'

      // let socket = new SockJS(serverURL);
      // this.stompClient = Stomp.over(socket);
      this.stompClient = this.$store.state.stompClient
      //console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true
          console.log('채팅 소켓 연결 성공', frame)
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          //console.log('roomID:' + this.roomId)
          this.stompClient.subscribe('/sub/chat/room/' + this.roomId, chat => {
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            let recvData = JSON.parse(chat.body)
            this.chatList.push(recvData)
          })
          if (this.stompClient && this.stompClient.connected) {
            const greeting = {
              roomId: this.roomId,
              writer: this.nickname,
              message: this.$store.state.message || this.message
            }
            console.log(greeting)
            this.stompClient.send('/pub/chat/enter', JSON.stringify(greeting))
          }
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error)
          this.connected = false
        }
      )
    }
  },
  created() {
    this.connect()
  },
  updated() {
  }
}

</script>

<style>
.custom .p-scrollpanel-wrapper {
    border-right: 9px solid #f4f4f4;
}

.custom .p-scrollpanel-bar {
    background-color: #1976d2;
    opacity: 1;
    transition: background-color .3s;
}

.custom .p-scrollpanel-bar:hover {
    background-color: #135ba1;
}
</style>
