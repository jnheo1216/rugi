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
  
    <div class="special-btn">

    <!-- BGM on/off -->
      <div>
        <audio id="homebgm"><source src="@/assets/sounds/home.wav"></audio>
        <div v-if="mute" class="sound-button">
          <a @click="soundOn">
            <img src="@/assets/buttons/soundoff.png" class="img-fluid">
          </a>
        </div>
        <div v-else class="sound-button">
          <a @click="soundOn">
            <img src="@/assets/buttons/soundon.png" class="img-fluid">
          </a>
        </div>
      </div>
      <Modal />
      <!-- 도움말 modal button -->
      <!-- <div>
        <div class="q-btn">
          <a>
            <img src="@/assets/buttons/q_mark.png" class="img-fluid">
          </a>
        </div>
      </div> -->
    </div>

  </div>
</template>

<script>
import AvatarSetting from '../components/setting/AvatarSetting.vue'
import HomeButton from '../components/buttons/HomeButton.vue'
import Modal from '../components/buttons/Modal.vue'
import axios from 'axios'
axios.defaults.baseURL = process.env.VUE_APP_API_URL
export default {
  name: 'Home',
  components: {
    AvatarSetting,
    HomeButton,
    Modal,
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
        audio.volume = 0.3
        audio.play()
        this.mute = false
      }
      else {
        const audio = document.getElementById('homebgm')
        audio.volume = 0
        this.mute = true
      }
    }
  },
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
  display: flex;
  position: absolute;
  width: 3rem !important;
  height: 3rem !important;
  bottom: 2rem;
  left: 2rem;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
  padding: 0.5rem;
  align-items: flex-end;
}
.sound-button:hover {
  background-color: #6a82fb45 !important;
  border-radius: 50%;
  border: none;
  cursor: pointer;
}
.q-btn {
  display: flex;
  position: absolute;
  width: 3rem !important;
  height: 3rem !important;
  bottom: 2rem;
  left: 6rem;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
  padding: 1rem;
  align-items: center;
}

.q-btn:hover{
  background-color: #6a82fb45;
  
  border-radius: 50%;
  border: none;
  cursor: pointer;
}
</style>
