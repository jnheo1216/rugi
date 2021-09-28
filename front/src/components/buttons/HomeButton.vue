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

export default {
  name: 'MainButton',

  methods: {
    clickCreateSession: function () {
      axios({
        method: 'post',
        url: '/room',
        data: {
          'avatar': `${this.$store.state.avatar}`,
          'nickname': `${this.$store.state.nickname}`
        }
      }).then((res) => {
        this.$router.push({ name: "Game", query: {room: res.data.data.id} })
        console.log(res)
      }).catch((err) => {
        console.log(err)
      })
    },
    clickEnterSession: function () {
      axios({
        method: 'post',
        url: '/room/user',
        params: {
          roomId: this.$store.state.roomId
        }
      }).then(() => {
          this.$router.push( {name : 'Game', query: {room: this.$store.state.roomId}})
      }).catch((err) => {
        console.log(err.response)
      })
    }
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
