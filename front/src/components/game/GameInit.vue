<template>
  <div class="init-component">
    <div id=quiz>
      <img id="balloon" src="@/assets/balloon1.png" alt="">
      <p> {{ quiz }} </p>
      <img id="rugi" src="@/assets/RUGI.png" alt="">
    </div>
    <div class="sound-button-init">
      <div class="sound-button">
        <button v-if="mute" @click="soundOn"><img src="@/assets/buttons/soundon.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
        <button v-else @click="soundOn"><img src="@/assets/buttons/soundoff.png" style="width: 2.5rem; height:2.5rem;" alt=""></button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GameInit',
  data: function() {
    return {
      mute: true
    }
  },
  methods: {
    soundOn() {
      // if(this.mute) {
      console.log(this.quizaudio)
      var audio = new Audio(this.quizaudio)
      audio.play()
      this.mute = false
      // }
    }
  },
  computed: {
    quiz: function () {
      return this.$store.state.room.quizList[this.$store.state.room.currentRound - 1].caption
    },
    quizaudio: function () {
      return this.$store.state.room.quizList[this.$store.state.room.currentRound - 1].audioUrl
    }
  },
  mounted() {
    this.soundOn()
  }
}
</script>

<style>
/* .init-component {
  display: flex;
  flex-direction: column;
  align-items: center;
} */

.initBody p {
  font-size: 35px;
}

.sound-button-init {
  position: absolute;
  top: 20%;
  left: 5%;
}

.sound-button-init * {
  color: #6A82FB;
  border: 0 !important;
}
.sound-button {
  border-radius: 50%;
}

#balloon {
  position: absolute;
  top: 0%;
}
#quiz {
  display: flex;
  flex-direction: column;
  /* 사진 크기만큼 height 줬음 */
  height: 306px;
  position: relative;
  align-items: center;
}
#quiz p {
  position: absolute;
  width: 450px;
  text-align: center;
  top: 40%;
}
#rugi {
  position: absolute;
  top: 80%;
}
</style>
