<template>
  <div class="lobby-body">
    <div>
      <!-- <UserList v-if="isRoomExist" /> -->
      <div class="lobby-components">
        <GameSetting v-if="isRoomExist" />
        <!-- 방장이 아닌 사람한테는 다른걸 보여줘도 좋을 듯 -->
        <div class="offset-1 col-10">
          <LobbyButton />
        </div>
      </div>
    </div>
    <audio id="lobbybgm"><source src="@/assets/sounds/home.wav"></audio>
    <div class="sound-button">
      <button v-if="mute" @click="soundOn"><img src="@/assets/buttons/soundon.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
      <button v-else @click="soundOn"><img src="@/assets/buttons/soundoff.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
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
        audio.volume = 1
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
  }
}

</script>

<style>
/* .lobby-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.lobby-components {
  display: flex;
  flex-direction: column;
} */
.sound-button {
  position: absolute;
  bottom: 2rem;
  left: 2rem;
}
.sound-button button {
  background-color: white;
  box-shadow: 3px 3px 3px gray, 3px 3px 3px gray;
  border-radius: 50%;
  border: none;
  width: 3rem;
  height: 3rem;
}
.sound-button button:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
}
</style>
