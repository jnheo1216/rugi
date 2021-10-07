<template>
  <div class="game-setting-body offset-2 col-8" :class="[$store.state.super ? '':'hidden']">
    <!-- <header>Game Settings</header> -->
    <div class="set container">
      <div class="row">
        <div class="col-6">
          <span>라운드</span>
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changeRound('left')">
              <img src="@/assets/buttons/arrowL.png" class="img-fluid">
            </a>
          </div>
        </div>
        <div class="col-2">
          {{ $store.state.super ? maxRound : $store.state.room["maxRound"] }}
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changeRound('right')">
              <img src="@/assets/buttons/arrowR.png" class="img-fluid">
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col-6">
          <span>라운드 당 시간</span>
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changeRoundTime('left')">
              <img src="@/assets/buttons/arrowL.png" class="img-fluid">
            </a>
          </div>
        </div>
        <div class="col-2">
          {{ $store.state.super ? roundTime : $store.state.room["roundTime"] }}
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changeRoundTime('right')">
              <img src="@/assets/buttons/arrowR.png" class="img-fluid">
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col-6">
          <span>최대 인원</span>
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changePersonnel('left')">
              <img src="@/assets/buttons/arrowL.png" class="img-fluid">
            </a>
          </div>
        </div>
        <div class="col-2">
          {{ $store.state.super ? personnel : $store.state.room["personnel"] }}
        </div>
        <div class="col-2 arrow-box">
          <div class="arrow-game-setting">
            <a @click="changePersonnel('right')">
              <img src="@/assets/buttons/arrowR.png" class="img-fluid">
            </a>
          </div>
        </div>
      </div>
    </div>
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
      this.roomUpdate()
    },
    changeRoundTime: function (direction) {
      let newRoundTime = direction==="left" ? this.roundTime-10 : this.roundTime+10
      this.roundTime = (40 <= newRoundTime && newRoundTime <= 120) ? newRoundTime : this.roundTime
      this.roomUpdate()

    },
    changePersonnel: function (direction) {
      let newPersonnel = direction==="left" ? this.personnel-1 : this.personnel+1
      if (this.$store.state.room['userList'].length > newPersonnel) {
        alert("현재 인원보다 작을 수 없습니다.")
        return
      }
      this.personnel = (2 <= newPersonnel && newPersonnel <= 8) ? newPersonnel : this.personnel
      this.roomUpdate()
    },
    roomUpdate : function () {
      if (!this.isSettingChanged) {
        alert("최대 혹은 최소 값 입니다.");
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
        // alert('방 정보 업데이트 성공')
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
  /* margin-top: 6rem; */
}
.set {
  padding: 1rem;
  text-align: center;
  font-size: 2rem;
}
.hidden Button {
  visibility: hidden;
}
#setting-btn {
  display: flex;
  justify-content: flex-end;
  /* padding: 4rem; */
  padding: 2rem 4rem 2rem 4rem;
}
#setting-btn Button{
  border-radius: 100%;
  width: 42px;
  height: 42px;
  color: #fc5c7d;
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.2)!important;
}
.arrow-game-setting {
  /* margin-top: 1rem !important; */
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
}
.arrow-game-setting:hover {
  cursor: pointer;
}
.arrow-box {
  display: flex;
  justify-content: center;
}

.pi-arrow-right {
  font-size:1.5rem !important;
}
.pi-arrow-left {
  font-size:1.5rem !important;
}
</style>
