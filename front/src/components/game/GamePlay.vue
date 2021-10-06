<template>
  <div class="playBody">
    <p>{{ quiz }}</p>
    <Timer />
    <Canvas />
    <audio id="playbgm"><source src="@/assets/sounds/home.wav"></audio>
    <div class="sound-button">
      <button v-if="mute" @click="soundOn"><img src="@/assets/buttons/soundon.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
      <button v-else @click="soundOn"><img src="@/assets/buttons/soundoff.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
    </div>
  </div>
</template>

<script>
import Canvas from '@/components/game/Canvas.vue'
import Timer from '@/components/game/Timer.vue'
export default {
  name: 'GamePlay',
  components: {
    Canvas,
    Timer
  },
  data() {
    return {
      mute: true
    }
  },
  methods: {
    soundOn() {
      if(this.mute) {
        const audio = document.getElementById('playbgm')
        audio.loop = true
        audio.volume = 1
        audio.play()
        this.mute = false
      }
      else {
        const audio = document.getElementById('playbgm')
        audio.volume = 0
        this.mute = true
      }
    }
  },
  computed: {
    quiz: function () {
      return this.$store.state.room.quizList[this.$store.state.room.currentRound - 1].caption
    }
  },
  mounted: function () {
    console.log('게임플레이 마운티드!', this.$store.state.room.roundTime)
  }
}
</script>

<style>
.playBody {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.playBody p {
  font-size: 20px;
}
.sound-button {
  position: absolute;
  bottom: 2rem;
  left: 2rem;
}
.sound-button button {
  padding: 10px;
  background-color: white;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
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
