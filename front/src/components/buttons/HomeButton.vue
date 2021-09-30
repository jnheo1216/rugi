<template>
  <div class="mainButtonBody">
    <Button
      id="createSession"
      @click="clickCreateSession"
      class="p-button-raised p-button-text p-button-secondary p-button-lg"
      label="방만들기"
    >
    </Button>
    <Button
      id="enterSession"
      @click="clickEnterSession"
      class="p-button-raised p-button-text p-button-secondary p-button-lg"
    >
      입장하기
    </Button>
  </div>
</template>

<script>
import axios from 'axios'
import {soundEffect} from '../api/sound.js'

export default {
  name: 'MainButton',

  methods: {
    clickCreateSession: function () {
      soundEffect('http://soundbible.com/mp3/sms-alert-1-daniel_simon.mp3')  // 소리는 쓰고싶은거 어디 올려놓고 써도 될거같음
      axios({
        method: 'post',
        url: '/room',
        data: {
          'avatar': `${this.$store.state.avatar}`,
          'nickname': `${this.$store.state.nickname}`
        }
      }).then((res) => {
        this.$store.dispatch('setRoomId', res.data.data.id)
        this.$router.push({ name: "Game", query: {room: res.data.data.id} })
        console.log(res)
      }).catch((err) => {
        console.log(err)
      })
    },
    clickEnterSession: function () {
      soundEffect('http://soundbible.com/mp3/sms-alert-1-daniel_simon.mp3')
      if (this.$store.state.roomId) {
        this.$router.push( {name : 'Game', query: {room: this.$store.state.roomId}})
      } else {
        // roomId가 없어요! Random 방 입장
      }
    },
  },
}
</script>

<style>
.mainButtonBody {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.p-button-raised {
  margin-top: 25px !important;
  margin-left: 25px !important;
}
</style>
