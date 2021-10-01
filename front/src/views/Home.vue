<template>
  <div class="homeBody">
    <Timer />
    <header>
      <img alt="logo" src="../assets/logo.png" height="300" width="450">
    </header>
    <div class="mainService">
      <AvatarSetting />
      <HomeButton />
    </div>
  </div>
</template>

<script>
import AvatarSetting from '../components/setting/AvatarSetting.vue'
import HomeButton from '../components/buttons/HomeButton.vue'
import Timer from '../components/game/Timer.vue'
import axios from 'axios'
axios.defaults.baseURL = process.env.VUE_APP_API_URL
export default {
  name: 'Home',
  components: {
    AvatarSetting,
    HomeButton,
    Timer,
  },
  methods: {
    createUser: function () {
      this.$store.dispatch('createUser')
    },
    setRoomId: function () {
      if (this.$route.query.room) {
        this.$store.dispatch('setRoomId', this.$route.query.room)
      }
    }
  },
  // mounted: function () 와 동일하게 동작
  mounted: function() {
    this.createUser()
    this.setRoomId()
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
  padding: 50px 0px;
}
</style>
