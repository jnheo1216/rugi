<!-- 방법 1: data.timer에서 this.timerStart() 호출 -->
<!-- 방법 2: mounted에서 this.timerStart() 호출 -->
<template>
  <section>
    <div>타이머 째깍</div>
    {{ this.time }}
    <!-- <button @click="timerStart">타이머 시작</button> -->
    <button @click="timerStop">타이머 종료</button>
  </section>
</template>
<script>
export default {
  name: 'Timer',
  data: function () {
    return {
      timer: this.timerStart(),
      // roundTime을 가지고 있지 않는 경우가 있을까?
      time: (this.$store.state.room ? this.$store.state.room.roundTime : 60)
    }
  },
  methods: {
    // 타이머 시작. 타이머 객체를 리턴함
    timerStart: function () {
      return setInterval(()=>{
        this.time -= 1
        if (this.time === 0) { // 종료 조건 걸어줄 수 있음
          this.timerStop()
        }
      }, 1000)
    },
    timerStop: function () {
      clearInterval(this.timer)
    }
  },
  mounted: function () {
    // this.timerStart()
  }
}
</script>

