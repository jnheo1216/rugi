<template>
  <div class="init-component">
    <div id=quiz>
      <img id="balloon" src="@/assets/balloon1.png" alt="">
      <p> {{ quiz }} </p>
      <img id="rugi" src="@/assets/RUGI.png" alt="">
    </div>
    <div class="sound-button-init">
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
  /* display: flex;
  flex-direction: column;
  align-items: center; */
}

.initBody p {
  font-size: 35px;
}

.sound-button-init {
  position: absolute;
  top: 20%;
  left: 5%;
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
