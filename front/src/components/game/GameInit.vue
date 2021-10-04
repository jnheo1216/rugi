<template>
  <div class="initBody">
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
      return this.$store.state.quiz
    }
  }
}
</script>

<style>
.initBody {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.initBody p {
  font-size: 35px;
}
</style>
