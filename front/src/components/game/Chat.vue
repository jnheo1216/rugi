<template>
  <div id="app">
    <div class="chat-box">
      <ScrollPanel
        style="width: 100%; height: 400px"
        class="custom"
      >
        <div
          v-for="(chat, idx) in chatList"
          :key="idx"
        >
          <div class="message-box">
            <div v-if="chat.writer===this.nickname" align="right">
              <div>{{ chat.writer }}</div>
              <div class="message-box-my">{{ chat.message }}</div>
            </div>
            <div v-else align="left">
              <div>{{ chat.writer }}</div>
              <div class="message-box-other">{{ chat.message }}</div>
            </div>
          </div>
          <ScrollTop />
        </div>
      </ScrollPanel>
      <InputText
        type="text"
        v-model="message"
        @keyup="sendMessage"
        placeholder="Chattt!"
      />
    </div>
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
      nickname : this.$store.state.nickname,
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

.chat-box {
  padding: 5px;
  box-shadow: 10px 5px 5px gray;
  border-radius: 10px;
}

.message-box {
  padding: 5px;
}

.message-box-other {
  padding: 5px;
  margin: 5px;
  border-radius: 5px 5px 5px 0px;
  background-color: #1976d2;
  width: auto;
  max-width: 70%;
  display: inline-block;
}

.message-box-my {
  padding: 5px;
  margin: 5px;
  border-radius: 5px 5px 0px 5px;
  background-color: antiquewhite;
  width: auto;
  max-width: 70%;
  display: inline-block;
}

</style>
