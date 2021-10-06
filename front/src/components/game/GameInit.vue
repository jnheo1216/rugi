<template>
  <div class="init-component">
    <div class="init-body"></div>
    <div id=quiz>
    <img src="@/assets/balloon1.png" alt="">
      <p>
      {{ quiz }}
      </p>
    </div>
    <img src="@/assets/RUGI.png" alt="">
    <div class="sound-button-init">
      <Button v-if="mute" class="p-button-help p-button-raised p-button-rounded p-button-outlined" icon="pi pi-volume-off" iconPos="right" @click="soundOn" />
      <Button v-else class="p-button-help p-button-raised p-button-rounded p-button-outlined" icon="pi pi-volume-up" iconPos="right" @click="soundOn" />
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
  }
}
</script>

<style>
.init-component {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.init-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.initBody p {
  font-size: 35px;
}
#quiz p {
  position: absolute;
  top: 20rem;
  left: 5rem;
  /* background: url("@/assets/balloon1.png") no-repeat; */
}

.sound-button-init {
  position: absolute;
  top: 2rem;
}
</style>
