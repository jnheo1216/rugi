<template>
  <div class="mainButtonBody">
    <Button
      id="createSession"
      @click="clickCreate"
      class="p-button-raised p-button-text p-button-secondary"
      icon="pi pi-apple"
      label="방만들기"
    >
    </Button>
    <Button id="enterSession">입장하기</Button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'MainButton',

  methods: {
    clickCreate: function () {
      axios({
        method: 'post',
        url: '/room',
        data: {
          'avatar': `${this.$store.state.avatar}`,
          'nickname': `${this.$store.state.nickname}`
        }
      }).then(() => {
        this.$router.push({ name: "Lobby", params: {room_id: this.$store.state.id} })
      }).catch((err) => {
        console.log(err)
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
</style>
