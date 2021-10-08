<template>
  <div class="init-component">
    <div id=quiz>
      <img id="balloon" src="@/assets/balloon1.png" alt="">
      <p> {{ quiz }} </p>
      <img id="rugi" src="@/assets/RUGI.png" alt="">
    </div>
    <div class="sound-button-init">
      <div v-if="mute" class="sound-button">
        <a @click="soundOn">
          <img src="@/assets/buttons/soundon.png" class="img-fluid">
        </a>
      </div>
      <div v-else class="sound-button">
        <a @click="soundOn">
          <img src="@/assets/buttons/soundoff.png" class="img-fluid">
        </a>
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
      if(this.mute) {
        console.log(this.quizaudio)
        var audio = new Audio(this.quizaudio)
        audio.play()
        this.mute = false
      }
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
    console.log("GameInit", this.$store.state.room.id);
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
  top: 30%;
  left: 5%;
}

.sound-button-init * {
  color: #6A82FB;
  border: 0 !important;
}

.sound-button {
  display: flex;
  width: 3rem !important;
  height: 3rem !important;
  /* bottom: 2rem;
  left: 2rem; */
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
  padding: 0.5rem;
  align-items: flex-end;
}
.sound-button:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
  cursor: pointer;
}

#balloon {
  position: absolute;
  top: 0%;
  width: 30vw;
}
#quiz {
  display: flex;
  flex-direction: column;
  /* 사진 크기만큼 height 줬음 */
  height: 30vh;
  position: relative;
  align-items: center;
}
#quiz p {
  position: absolute;
  width: 21vw;
  text-align: center;
  top: 40%;
}
#rugi {
  margin-top: 1rem;
  position: absolute;
  top: 80%;
  height: 50vh;
}
</style>
