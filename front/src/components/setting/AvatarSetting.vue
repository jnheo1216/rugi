<template>
  <div class="avatarBody">
    <div>
      <!-- 여기 좌측 화살표 -->
      <button @click="changeAvatar('left')">left</button>
      <button @click="changeAvatar('right')">right</button>
      <button @click="randomAvatar">random</button>
      <div class="hi">
        Avatar Setting
      </div>
      <Avatar
        class="avatar"
        :image="require(`@/assets/avatar/${avatar}.png`)"
        shape="circle"
      />
      <!-- 여기 우측 화살표 -->

      <div class="p-float-label why">
        <InputText
          id="username1"
          type="text"
          v-model="nickname"
          @input="setNickName"
          @keyup.enter="userSet"
        >
        </InputText>
        <label
          v-if="nickname.length"
          for="username"
        >
          <!-- 닉네임이 있는 경우 닉네임 -->
          {{ nickname }}
        </label>
        <label v-else>
          <!-- 없으면 default 값 노출 -->
          Nickname
        </label>
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
      console.log(this.$store.state.avatar)
    },
    randomAvatar: function () {
      let rand = this.getRanNum()
      while (rand===this.avatar) {
        rand = this.getRanNum()
      }
      this.avatar = rand
      this.$store.dispatch('setAvatar', this.avatar)
      console.log(this.$store.state.avatar)
    },
    getRanNum: function () {
      return Math.floor(Math.random()*this.avatarCount)
    },
    setNickName: function () {
      this.$store.dispatch('setNickName', this.nickname)
    },
  },
  created: function () {
    // 접속시 아바타 랜덤 설정
    this.randomAvatar()
  }
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
</style>
