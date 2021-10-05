<template>
  <div id="chat-component">
    <div class="chat-box p-flex">

      <div id="chat_list"
        :class="[$store.getters.currentView === 'Lobby' ? 'game-before' : 'game-ing', 'p-col-12']">
        <div
          v-for="(chat, idx) in chatList"
          :key="idx"
        >
          <div class="message-box">
            <div v-if="chat.writer===this.nickname" align="right">
              <div class="message-box-my">{{ chat.message }}</div>
            </div>
            <div v-else align="left">
              <div>{{ chat.writer }}</div>
              <div class="message-box-other">{{ chat.message }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="p-inputgroup p-mt-3">
        <InputText
          class="p-col-10"
          type="text"
          v-model="message"
          @keyup="sendMessage"
          placeholder="Chattt!"
        />
        <!-- 버튼 클릭 안되는데 왠지 모르겠음 -->
        <Button id=chat-btn icon="pi pi-comment" @click="sendMessage" />
      </div>
    </div>

  </div>
</template>

<script>
import { soundEffectChat } from '../api/sound.js'

export default {
  name: 'Chat',
  props: {
    chatList : {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      message: '',
      nickname : this.$store.state.nickname,
    }
  },
  methods: {
    sendMessage (e) {
      soundEffectChat()
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        //console.log('recieve list', this.chatList)
        this.message = ''
      }
      let chat_list = document.getElementById("chat_list")
      chat_list.scrollTo({ top: chat_list.scrollHeight, behavior: 'smooth' })
      chat_list.scrollTop = chat_list.scrollHeight
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
}

</script>

<style>
@font-face {
    font-family: 'IBMPlexSansKR-ExtraLight';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-ExtraLight.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}


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
  width: 30vw;
  bottom: 0em !important;
  right: 0em !important;
  position: absolute;
  padding: 3rem;
  border-radius: 10px;
  min-width: 200px;
  max-width: 400px;
}

.message-box {
  padding: 10px;
}

.message-box-other {
  padding: 0.6rem;
  border-radius: 1.2em;
  border-bottom-left-radius: 0em;
  width: auto;
  max-width: 70%;
  display: inline-block;
  word-break:break-all;
  box-shadow: 2px 2px 5px #cacaca;
}

.message-box-my {
  padding: 0.6rem;
  border-radius: 1.2em;
  border-bottom-right-radius: 0em;
  /* background-color: rgb(246, 229, 206); */
  background-color:  #6a82fb;
  color: white;
  width: auto;
  max-width: 70%;
  display: inline-block;
  word-break:break-all;
  box-shadow: 2px 2px 5px #cacaca;
}

.game-before {
  height: 80vh !important;
}
.game-ing {
  height: 50vh !important;
}

#chat_list {
  font-family: 'IBMPlexSansKR-ExtraLight' !important;
  font-size: 0.8rem;
  height: 80vh;
  overflow-y: scroll;
  overflow-x: hidden;
}
#chat_list::-webkit-scrollbar {
  width: 10px;
}
#chat_list::-webkit-scrollbar-thumb {
  background-color: #6a82fb;
  border-radius: 5px;
}
#chat_list::-webkit-scrollbar-track {
  background-color: #fc5c7d;
  border-radius: 5px;
  /* box-shadow: inset 0px 0px 5px white; */
}

#chat-btn {
  background-color: white !important;
  border: 0px !important;
  box-shadow: 2px 2px 5px #cacaca;
  color: #fc5c7d !important;
}
.p-inputtext{
  width:100%;
  border: 0px !important;
  box-shadow: 2px 2px 5px #cacaca;
}

.p-inputgroup {
  display: flex;
}


</style>
