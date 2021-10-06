<template>
  <div class="home-body p-d-flex p-ai-center">
    <header class="p-col-12">
      <img class="logo p-d-flex" alt="logo1" src="../assets/logo1.gif">
    </header>
    <div class="main-service p-grid p-col-10 p-jc-center">
      <div class="p-col-5 p-my-auto">
        <AvatarSetting />
      </div>
      <div class="p-col-5 p-my-auto">
        <HomeButton />
      </div>
    </div>
    <audio id="homebgm"><source src="@/assets/sounds/home.wav"></audio>
    <div class="sound-button">
      <Button
        v-if="mute"
        class="p-button-help p-button-raised p-button-rounded p-button-outlined"
        icon="pi pi-volume-off"
        iconPos="right"
        @click="soundOn"
      />
      <Button
        v-else
        class="p-button-help p-button-raised p-button-rounded p-button-outlined"
        icon="pi pi-volume-up"
        iconPos="right"
        @click="soundOn"
      />
    </div>
  </div>
</template>

<script>
import AvatarSetting from '../components/setting/AvatarSetting.vue'
import HomeButton from '../components/buttons/HomeButton.vue'
import axios from 'axios'
axios.defaults.baseURL = process.env.VUE_APP_API_URL
export default {
  name: 'Home',
  components: {
    AvatarSetting,
    HomeButton,
  },
  data() {
    return {
      mute: true,
    }
  },
  methods: {
    soundOn() {
      if(this.mute) {
        const audio = document.getElementById('homebgm')
        audio.loop = true
        audio.volume = 1
        audio.play()
        this.mute = false
      }
      else {
        const audio = document.getElementById('homebgm')
        audio.volume = 0
        this.mute = true
      }
    }
  }
  // methods: {
  //   soundsplay() {
  //     var promise = document.getElementById('audio')
  //     promise.loop = true
  //     promise.play()
  //   }
  // },
  // created() {
  //   var audi = new Audio('@/assets/sounds/home.wav')
  //   audi.loop = true
  //   console.log('오디오')
  //   audi.play()
  // }
}
</script>
<style>
.home-body {
  display: flex;
  flex-direction: column;
  align-items: between;
  justify-content: center;
  padding-block: 3rem;
}
.main-service {
  background-color: transparent;
}

.logo {
  aspect-ratio: initial;
  height: 40vh;
  margin: auto;
}

.sound-button {
  position: absolute;
  bottom: 2rem;
}

</style>
