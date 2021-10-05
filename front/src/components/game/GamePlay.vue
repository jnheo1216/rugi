<template>
  <div class="playBody">
    <p>{{ quiz }}</p>
    <Canvas />
  </div>
</template>

<script>
import Canvas from '@/components/game/Canvas.vue'
export default {
  name: 'GamePlay',
  components: {
    Canvas,
  },
  computed: {
    quiz: function () {
      return this.$store.state.quiz
    }
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
