<template>
  <div class="game-setting-body" :class="[$store.state.super ? 'yes-super':'no-super']">
    <!-- <header>Game Settings</header> -->
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>라운드</span>
        </div>
        <div class="col">
          <Button @click="changeRound('left')" icon="pi pi-arrow-left" style="fontSize: 5rem" class="p-button-rounded p-button-text"></Button>
          {{ maxRound }}
          <Button @click="changeRound('right')" icon="pi pi-arrow-right" class="p-button-rounded p-button-text"></Button>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>라운드 당 시간</span>
        </div>
        <div class="col">
          <Button @click="changeRoundTime('left')" icon="pi pi-arrow-left" style="fontSize: 5rem" class="p-button-rounded p-button-text"></Button>
          {{ roundTime }}
          <Button @click="changeRoundTime('right')" icon="pi pi-arrow-right" class="p-button-rounded p-button-text"></Button>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>최대 인원</span>
        </div>
        <div class="col">
          <Button @click="changePersonnel('left')" icon="pi pi-arrow-left" style="fontSize: 5rem" class="p-button-rounded p-button-text"></Button>
          {{ personnel }}
          <Button @click="changePersonnel('right')" icon="pi pi-arrow-right" class="p-button-rounded p-button-text"></Button>
        </div>
      </div>
    </div>
    <section id="setting-btn">
      <Button label="Primary" class="p-button-raised p-button-text p-button-plain" icon="pi pi-check" @click="roomUpdate"></Button>
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
      personnel: this.$store.state.room["personnel"],
    }
  },
  methods: {
    changeRound: function (direction) {
      let newRound = direction==="left" ? this.maxRound-1 : this.maxRound+1
      this.maxRound = (1 <= newRound && newRound <= 5) ? newRound : this.maxRound
      console.log('테스트용 ', this.maxRound)
      console.log('테스트용 ', this.roundTime)
      console.log('테스트용 ', this.personal)
    },
    changeRoundTime: function (direction) {
      let newRoundTime = direction==="left" ? this.roundTime-10 : this.roundTime+10
      this.roundTime = (40 <= newRoundTime && newRoundTime <= 120) ? newRoundTime : this.roundTime
      console.log('테스트용 ', this.maxRound)
      console.log('테스트용 ', this.roundTime)
      console.log('테스트용 ', this.personal)
    },
    changePersonnel: function (direction) {
      let newPersonnel = direction==="left" ? this.personnel-1 : this.personnel+1
      this.personnel = (2 <= newPersonnel && newPersonnel <= 8) ? newPersonnel : this.personnel
      console.log('테스트용 ', this.maxRound)
      console.log('테스트용 ', this.roundTime)
      console.log('테스트용 ', this.personal)
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
          personnel: this.personnel,
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
      return (
        this.$store.state.room["maxRound"] !== this.maxRound
        || this.$store.state.room["roundTime"] !== this.roundTime
        || this.$store.state.room["personnel"] !== this.personnel
      )
    }
  }
}
</script>

<style>
.game-setting-body {
  display: flex;
  flex-direction: column;
  font-size: 3rem !important;
  margin-top: 6rem;
}
.set {
  font-size: 30px;
  /* columns:12; */
}
.col {
  text-align: center;
  font-size: 3rem !important;
}
.no-super Button{
  opacity: 0;
}
#setting-btn {
  display: flex;
  justify-content: flex-end;
  padding: 4rem;
}
#setting-btn Button{
  border-radius: 100%;
  align-items: center;
  width: 42px;
  height: 42px;
}
</style>
