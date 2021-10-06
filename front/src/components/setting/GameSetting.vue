<template>
  <div class="game-setting-body offset-2 col-8" :class="[$store.state.super ? 'yes-super':'no-super']">
    <!-- <header>Game Settings</header> -->
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>라운드</span>
        </div>
        <div class="col">
          <Button 
            @click="changeRound('left')" 
            icon="pi pi-arrow-left" 
            style="fontSize: 5rem" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
          {{ maxRound }}
          <Button 
            @click="changeRound('right')" 
            icon="pi pi-arrow-right" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>라운드 당 시간</span>
        </div>
        <div class="col">
          <Button 
            @click="changeRoundTime('left')" 
            icon="pi pi-arrow-left" style="fontSize: 5rem" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
          {{ roundTime }}
          <Button 
            @click="changeRoundTime('right')" 
            icon="pi pi-arrow-right" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
        </div>
      </div>
    </div>
    <div class="set container">
      <div class="row">
        <div class="col">
          <span>최대 인원</span>
        </div>
        <div class="col">
          <Button 
            @click="changePersonnel('left')" 
            icon="pi pi-arrow-left" 
            style="fontSize: 5rem" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
          {{ personnel }}
          <Button 
            @click="changePersonnel('right')" 
            icon="pi pi-arrow-right" 
            class="p-button-rounded p-button-text arrow"
          >
          </Button>
        </div>
      </div>
    </div>
    <!-- <section id="setting-btn">
      <Button 
        @click="roomUpdate"
        icon="pi pi-check" 
        class="p-button-raised p-button-text p-button-plain" 
      >
      </Button>
    </section> -->
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
.arrow {
  /* margin-top: 1rem !important; */
  color: #6A82FB !important;
}
/* game setting 화살표 살짝 내려온거 */
.col .p-button-icon-only {
  height: 3.8rem !important;
}
.pi-arrow-right {
  font-size:2rem !important;
}
.pi-arrow-left {
  font-size:2rem !important;
}
</style>
