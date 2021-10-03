<template>
  <div class="gameSettingBody">
    <header>Game Settings</header>
    <div class="set">
      <span>라운드</span>
      <Button @click="changeRound('left')" icon="pi pi-arrow-left" style="fontSize: 0.1rem" class="p-button-rounded p-button-text"></Button>
      {{ maxRound }}
      <Button @click="changeRound('right')" icon="pi pi-arrow-right" class="p-button-rounded p-button-text"></Button>
    </div>
    <div class="set">
      <span>라운드 당 시간</span>

      <Button @click="changeRoundTime('left')" icon="pi pi-arrow-left" style="fontSize: 0.1rem" class="p-button-rounded p-button-text"></Button>
      {{ roundTime }}
      <Button @click="changeRoundTime('right')" icon="pi pi-arrow-right" class="p-button-rounded p-button-text"></Button>
    </div>
    <section>
      <Button label="Primary" class="p-button-raised p-button-text p-button-plain" @click="roomUpdate">
        방 설정 적용
      </Button>
    </section>
  </div>
</template>

<script>
import axios from'axios'

export default {
  name: 'GameSetting',
  data: function() {
    return {
      maxRound: this.$store.state.room["maxRound"],
      roundTime: this.$store.state.room["roundTime"],
    }
  },
  methods: {
    changeRound: function (direction) {
      let newRound = direction==="left" ? this.maxRound-1 : this.maxRound+1
      this.maxRound = (1 <= newRound && newRound <= 5) ? newRound : this.maxRound
    },
    changeRoundTime: function (direction) {
      let newRoundTime = direction==="left" ? this.roundTime-10 : this.roundTime+10
      this.roundTime = (40 <= newRoundTime && newRoundTime <= 120) ? newRoundTime : this.roundTime
    },
    roomUpdate : function () {
      if (!this.isSettingChanged) {
        alert("변경된 설정이 없습니다.");
        return
      }
      axios({
        method: 'patch',
        url: '/room',
        data: {
          maxRound: this.maxRound,
          roundTime: this.roundTime,
        },
        params: {
          roomId: this.$route.query["room"]
        }
      }).then((res) => {
        alert('방 정보 업데이트 성공')
        console.log(res.data)
      }).catch((err) => {
        alert('방 정보 업데이트 실패')
        console.log(err.response)
      })
    },
  },
  computed: {
    isSettingChanged: function () {
      return this.$store.state.room["maxRound"] !== this.maxRound || this.$store.state.room["roundTime"] !== this.roundTime
    }
  }
}
</script>

<style>
.gameSettingBody {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-top: 50px;
  padding: 50px 0px;
}
.set {
  font-size: 30px;
}
</style>
