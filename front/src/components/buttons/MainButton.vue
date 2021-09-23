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

const API_URL = 'localhost'
export default {
  name: 'MainButton',

  methods: {
    clickCreate: function () {
      axios({
        method: 'post',
        url: `${API_URL}:8080/api/room/`,
        data: {
          "avatar": "/avatar/1",
          "nickname": "건건"
        }
      }).then((res) => {
        console.log(res)
      })
      // this.$router.push({ name : 'Lobby' , params: { room_id : 1}})
    }
  }
}
</script>

<style>
.mainButtonBody {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
