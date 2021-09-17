<template>
  <body>
    <button id="craeteSession">방만들기</button>
    <button id="enterSession">입장하기</button>
  </body>
</template>

<script>
import axios from 'axios'

export default {
  name: 'MainButton',

  props: {
    nickname: String,
  },

  methods: {
    clickCreate: function () {
      axios({
        method: 'post',
        url: 'api/hi',
      }).then((res) => {
        console.log(res)
      })
    }
  }
}
</script>

<style>
@import '../../reset.css';
body {
  background-color: #f6f9fc;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
</style>
