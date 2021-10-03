<template>
  <div class="userListBody">
    <span>User</span>
    <AvatarGroup class="p-mb-3">
      <Avatar
        v-for="user in userlist"
        :key="user.nickname"
        :image="require(`@/assets/avatar/${user.avatar}.png`)"
        size="xlarge"
        shape="circle"
      />
      <Avatar :label="`${ usernum }`" shape="circle" size="large" style="background-color:#9c27b0; color: #ffffff" />
    </AvatarGroup>
    <!-- <div class="userList" v-for="user in userlist" :key="user.nickname">
      <div>{{ user.nickname }}</div>
      <div>{{ user.avatar }}</div>
      <div>{{ user.super }}</div>
    </div> -->
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
.userListBody {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-top: 50px;
  padding: 50px 10px;
}
.userList {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>
