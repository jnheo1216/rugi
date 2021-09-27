<template>
  <div class="avatarBody">
    <div>
      <!-- 여기 좌측 화살표 -->
      <button @click="changeAvatar('left')">left</button>
      <button @click="changeAvatar('right')">right</button>
      <button @click="randomAvatar">random</button>
      <div class="hi">
        아바타설정
      </div>
      <Avatar class="avatar" :image="require(`@/assets/avatar/${avatar}.png`)" size="xlarge" shape="circle" />
      <!-- 여기 우측 화살표 -->

      <div class="p-float-label why">
        <InputText
          id="username1"
          type="text"
          v-model="nickname"
          @input="setNickName"
        >
        </InputText>
        <label for="username">Nickname</label>
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
      nickname : '',
      avatar: 0,
      avatarCount: 28 // 이미지 개수만큼 여기만 수정하면 됨
    }
  },
  methods: {
    changeAvatar: function (direction) {
      // 여기로직은 건드리지 않아도 됨
      this.avatar = (direction ==='left' ? this.avatar+this.avatarCount-1 : this.avatar+1) % this.avatarCount
    },
    randomAvatar: function () {
      let rand = this.getRanNum()
      while (rand===this.avatar) {
        rand = this.getRanNum()
      }
      this.avatar = rand
    },
    getRanNum: function () {
      return Math.floor(Math.random()*this.avatarCount)
    },
    setNickName: function () {
      this.$store.dispatch('setNickName', this.nickname)
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

</style>
