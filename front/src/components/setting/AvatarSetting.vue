<template>
  <div class="avatarBody">
    <div>
      <section class="avatar-select-header">
        <span>Avatar</span>
      </section>
      <section class="avatar-select">
        <i class="fas fa-chevron-left" @click="changeAvatar('left')"></i>
        <Avatar class="avatar" :image="require(`@/assets/avatar/${avatar}.png`)" shape="circle" />
        <i class="fas fa-chevron-right" @click="changeAvatar('right')"></i>
      </section>
      <section class="avatar-select">
        <i class="fas fa-random" @click="randomAvatar"></i>
      </section>
      <p>Nickname</p>
      <div class="p-float-label why">
        <InputText
          id="username"
          type="text"
          v-model="nickname"
          @input="setNickName"
          place-holder="Hello"
        >
        </InputText>
      </div>
    </div>
  </div>
</template>

<script>
// import MainButton from '@/components/MainButton'

export default {
  components: {
  },
  name: 'AvatarSetting',
  data: function() {
    return {
      // 로컬 스토리지에 저장된 닉네임 불러오기
      nickname : this.$store.state.nickname,
      avatar: 0,
      avatarCount: 28 // 이미지 개수만큼 여기만 수정하면 됨
    }
  },
  methods: {
    changeAvatar: function (direction) {
      // 여기로직은 건드리지 않아도 됨
      this.avatar = (direction ==='left' ? this.avatar+this.avatarCount-1 : this.avatar+1) % this.avatarCount
      this.$store.dispatch('setAvatar', this.avatar)
    },
    randomAvatar: function () {
      let rand = this.getRanNum()
      while (rand===this.avatar) {
        rand = this.getRanNum()
      }
      this.avatar = rand
      this.$store.dispatch('setAvatar', this.avatar)
    },
    getRanNum: function () {
      return Math.floor(Math.random()*this.avatarCount)
    },
    setNickName: function () {
      this.$store.dispatch('setNickName', this.nickname)
    },
  },
  mounted: function () {
    // 접속시 아바타 랜덤 설정
    // 최초 접속시 (localstorage가 비어있음)
    if (this.$store.state.avatar === 0) {
      this.randomAvatar()
    } else {
      // 새로고침시 (localstorage가에 정보가 저장되어 있음)
      this.avatar = this.$store.state.avatar
    }
  },
}
</script>

<style>
.avatarBody {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
.hi {
  padding: 25px 0px;
}
.why {
  /* avatar랑 nickname 띄어놓기 위함 */
  margin-top: 25px;
}
.avatar {
  width: 150px !important;
  height: 150px !important;
}

.avatar-select-header {
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-select-header > span {
  font-size: 1.3rem;
  font-weight: 600;
}

.avatar-select {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  margin: 5px 0;
}
.fa-chevron-left, .fa-chevron-right {
  margin: 20px;
  cursor: pointer;
  font-size: 2rem;
}
.fa-random {
  cursor: pointer;
  font-size: 1.8rem;
}
</style>
