<template>
  <div class="playBody">
    <p>{{ quiz }}</p>
    <Timer />
    <Canvas />
    <audio id="playbgm"><source src="@/assets/sounds/ticking-clock.wav"></audio>
    <div v-if="mute" class="sound-button-play">
      <a @click="soundOn">
        <img src="@/assets/buttons/soundon.png" class="img-fluid">
      </a>
    </div>
    <div v-else class="sound-button-play">
      <a @click="soundOn">
        <img src="@/assets/buttons/soundoff.png" class="img-fluid">
      </a>
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
    console.log('GamePlay', this.$store.state.room.id)
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
.sound-button-play {
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
.sound-button-play:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
}
</style>
