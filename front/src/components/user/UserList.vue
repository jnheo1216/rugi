<template>
  <div class="user-list-body col-8">
    <AvatarGroup class="p-mb-5" style="max-height: 60px;">
      <div class="avatarImg" v-for="user in userlist" :key="user.nickname">
        <Avatar
          :image="require(`@/assets/avatar/${user.avatar}.png`)"
          size="large"
          shape="circle"
        />
        <div>
          <span id="nickname">{{ user.nickname }}</span>
        </div>
      </div>
      <Avatar
        :label="`${ usernum }`"
        shape="circle"
        size="small"
        style="background-color:#fc5c7d; color: #ffffff"
      />
    </AvatarGroup>
  </div>
</template>

<script>
export default {
  name: 'UserList',
  data: function() {
    return {
    }
  },
  methods: {
  },
  computed: {
    usernum: function () {
      return this.$store.state.room.userList.length
    },
    // store에 있는 userlist가 변할 때 마다 computed 됨
    userlist: function () {
      return this.$store.state.room.userList
    }

    // vuex의 store의 계산된 속성으로 생각할 수 있다.
    // store의 getter를 사용하는것과 현재 component에서 computed로 접근하는것의 차이는?
    // userlist: function () {
    //   console.log('유저리스트 변경됐지롱')
    //   return this.$store.getters.getUserList
    // }
  },
}
</script>

<style>
.user-list-body {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 2rem 1rem 1rem 2rem;
}
.userList {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
/* 호버시 텍스트 보이게 */
.avatarImg {
  cursor: default;
}

#nickname{
  display: inline-block;
  text-align: center;
  opacity: 0;
  width: 60px;
  height: 42px;
}

.avatarImg:hover #nickname{
  opacity: 0.7;
  font-family: "Elice Digital Baeum",sans-serif !important;
}
.p-avatar-circle {
  background-color: white ;
  font-family: "Elice Digital Baeum",sans-serif !important;
}
.p-avatar-image {
  width: 100px !important;
  height: 100px !important;
}
</style>
