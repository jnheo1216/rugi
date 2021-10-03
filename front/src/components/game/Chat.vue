<template>
  <div id="app">
    <ScrollPanel
      style="width: 100%; height: 400px"
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
    <InputText
      type="text"
      v-model="message"
      @keyup="sendMessage"
      placeholder="Chattt!"
    />
    <!-- <button @click="unSub">언섭 테스트</button> -->
  </div>
</template>

<script>
export default {
  name: 'Chat',
  props: {
    chatList : {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      message: '',
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
      console.log("Send message:" + this.message)
      this.$store.state.stompClient.send(
        '/pub/chat/message',
        JSON.stringify({
          roomId: this.$route.query["room"],
          writer: this.$store.state.nickname,
          message: this.message
        })
      )
    },
  },
  created() {
    // this.connect()
  },
  mounted() {
    // window.addEventListener('unload', this.$store.dispatch('unSub'))
  },
  unmounted() {
    // this.stompClient.disconnect('/pub/chat/enter', {}, {})
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
