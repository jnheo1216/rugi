<template>
  <div class="mainButtonBody">
    <button id="craeteSession" @click="clickCreate">방만들기</button>
    <button id="enterSession">입장하기</button>
  </div>
</template>

<script>
// import axios from 'axios'

export default {
  name: 'MainButton',

  methods: {
    clickCreate: function () {
    //   axios({
    //     method: 'post',
    //     url: 'api/hi',
    //   }).then((res) => {
    //     console.log(res)
    //   })
      this.$router.push({ name : 'Lobby' , params: { room_id : 1}})
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
