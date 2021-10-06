<template>
  <div class="init-component">
    <div class="init-body"></div>
    <p>
      {{ quiz }}
    </p>
    <img src="@/assets/RUGI.png" alt="">
  </div>
</template>

<script>
export default {
  name: 'GameInit',
  data: function() {
    return {
    }
  },
  methods: {
  },
  computed: {
    quiz: function () {
      return this.$store.state.room.quizList[this.$store.state.room.currentRound - 1].caption
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
</style>
