<!-- 방법 1: data.timer에서 this.timerStart() 호출 -->
<!-- 방법 2: mounted에서 this.timerStart() 호출 -->
<template>
  <section class="timer-position d-flex flex-column ">
    <img class="timer" src="@/assets/buttons/timer.png" alt="">
    <p id=sec class="text-end">
      {{ this.time }}
    </p>
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
          // 일단 지금은 필요 없다.
        }
      }, 1000)
    },
  },
  mounted: function () {
    // this.timerStart()
  }
}
</script>

<style>
.timer-position {
  columns: 4;
  position: absolute;
  top: 0rem !important;
  right: 1rem !important;
  background-image: url(/src/assets/buttons/timer.png);
}
.timer {
  position: absolute;
  top: 1rem !important;
  right: 0rem !important;
  height: 18vh;
}
#sec {
  position: absolute;
  top: 93px !important;
  right: 48px !important;
  font-size: 2em;
  width: 10rem
}
</style>
