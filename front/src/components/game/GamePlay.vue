<template>
  <div class="playBody">
    <p>{{ quiz }}</p>
    <Timer />
    <Canvas />
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
  computed: {
    quiz: function () {
      return this.$store.state.quiz
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
</style>
