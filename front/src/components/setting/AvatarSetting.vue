<template>
  <div class="avatar-body">
    <div>
      <section class="avatar-select">
        <a class="" @click="randomAvatar">
          <img class="img-fluid sf-btn" src="@/assets/buttons/shufflebtn.png" alt="">
        </a>
      </section>
      <section class="avatar-select">
        <div class="arrow-avatar-setting">
          <a @click="changeAvatar('left')">
            <img src="@/assets/buttons/arrowL.png" class="img-fluid">
          </a>
        </div>
        <Avatar class="avatar" :image="require(`@/assets/avatar/${avatar}.png`)" shape="circle" />
        <div class="arrow-avatar-setting">
          <a @click="changeAvatar('right')">
            <img src="@/assets/buttons/arrowR.png" class="img-fluid">
          </a>
        </div>
      </section>
      <div class="p-d-grid">
        <label class="p-col-8 p-offset-2 mini" for="nickname">
          nickname
        </label>
        <InputText
          id="username"
          class="p-col-8 p-offset-2"
          type="text"
          v-model="nickname"
          @input="setNickName"
          place-holder="RUGI"
        >
        </InputText>
      </div>
    </div>
  </div>
</template>

<script>

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
    if (this.$store.state.avatar !== null) {
      this.avatar = this.$store.state.avatar
    }
  },
}
</script>

<style>
.p-button, .p-inputtext {
  font-family: "Elice Digital Baeum",sans-serif !important;
  font-size: 0.7rem !important;
}
.avatar-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.avatar {
  width: 10vh !important;
  height: 100% !important;
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
  justify-content: center;
  margin: 5px 0;
}
.fa-chevron-left, .fa-chevron-right {
  margin: 20px;
  cursor: pointer;
  font-size: 2rem;
  color: #6A82FB !important;
}
.mini {
  font-size: 0.2rem;
}
.sf-btn {
  height: 3vh;
}
.sf-btn:hover {
  cursor: pointer;
}

.arrow-avatar-setting {
  /* margin-top: 1rem !important; */
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
}
.arrow-avatar-setting:hover {
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
}
</style>
