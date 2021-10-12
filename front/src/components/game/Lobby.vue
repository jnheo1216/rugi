<template>
  <div class="lobby-body">
    <div class="d-flex">
      <!-- <UserList v-if="isRoomExist" /> -->
      <div class="lobby-components pb-0">
        <GameSetting v-if="isRoomExist" />
        <!-- 방장이 아닌 사람한테는 다른걸 보여줘도 좋을 듯 -->
        <div class="offset-1 col-10">
          <LobbyButton />
        </div>
      </div>
    </div>
    <audio id="lobbybgm"><source src="@/assets/sounds/home.wav"></audio>
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
</template>

<script>

import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'

export default {
  emits: ['viewChange'],
  components: {
    GameSetting,
    LobbyButton,
  },
  name: 'Lobby',
  data() {
    return {
      mute: true
    }
  },
  methods: {
    soundOn() {
      if(this.mute) {
        const audio = document.getElementById('lobbybgm')
        audio.loop = true
        audio.volume = 0.3
        audio.play()
        this.mute = false
      }
      else {
        const audio = document.getElementById('lobbybgm')
        audio.volume = 0
        this.mute = true
      }
    }
  },
  computed: {
    isRoomExist : function () {
      return this.$store.getters.isRoomExist
    },
  },
  mounted() {
    console.log("Lobby", this.$store.state.room.id);
  }
}

</script>

<style>
/* .lobby-body {
  display: flex;
  flex-direction: column;
  align-items: center;
} */
.lobby-components {
  padding:3rem;
  display: flex;
  flex-direction: column;
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
  padding-bottom: 1rem;
  align-items: center;
}
.sound-button:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
  cursor: pointer;
}
</style>
