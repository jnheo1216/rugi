<template>
  <div class="homeBody">
    <header>
      <img alt="Vue logo" src="../assets/ccc.png" height="200" width="200">
    </header>
    <h1>서 비 스 명</h1>
    <div class="mainService">
      <Avatar />
      <Main-button />
    </div>
  </div>
</template>

<script>
import Avatar from '../components/setting/Avatar.vue'
import MainButton from '../components/buttons/MainButton.vue'
import axios from 'axios'
axios.defaults.baseURL = process.env.VUE_APP_API_URL
export default {
  name: 'Home',
  components: {
    Avatar,
    MainButton
  },
  // mounted: function () 와 동일하게 동작
  mounted() {
    axios({
      method: 'post',
      url: '/user',
      data: {
        //기본 아바타, 닉네임 설정 할 수 있게 해줘야 한다.
        "avatar": "/avatar/1",
        "nickname": "nickname"
      }
    }).then((res) => {
      console.log('됐다 이 말 이야')
      axios.defaults.headers.common['User-id'] = res.data.data.id
      this.$store.dispatch('setUserData', res.data.data)
    }).catch((err) => {
      console.log(err.response)
    })
  }
}
</script>
<style>
.homeBody {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.mainService {
  background-color: #f6f9fc;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  display: flex;
  flex-direction: row;
  align-items: center;
  /* width: 100%; */
  padding-top: 50px;
  padding: 50px 0px;
}
</style>
