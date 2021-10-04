<template>
  <div>
    <div>
      <img src="@/assets/RUGI.png" alt="">
    </div>
    <div>
      {{ quiz }}
    </div>
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

</style>
