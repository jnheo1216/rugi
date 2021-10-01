<template>
  <div class="userListBody">
    <AvatarGroup class="p-mb-3">
      <Avatar
        v-for="user in userlist"
        :key="user.nickname"
        :image="require(`@/assets/avatar/${user.avatar}.png`)"
        size="large"
        shape="circle"
      />
      <Avatar :label="`${ usernum }`" shape="circle" size="large" style="background-color:#9c27b0; color: #ffffff" />
    </AvatarGroup>
    <div class="userList" v-for="user in userlist" :key="user.nickname">
      <div>{{ user.nickname }}</div>
      <div>{{ user.avatar }}</div>
      <div>{{ user.super }}</div>
    </div>
    <div>{{ userlist }}</div>
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
  },
  watch: {
    //computed로 store에 있는 room.userList가 변경된것이 감지 된다면 동작
    userlist: function() {
      //
    }
  }
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
