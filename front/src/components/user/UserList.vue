<template>
  <div class="userListBody">
    <!-- <div class="userList" v-for="user in users" :key="user.userNumber">
      <div>{{ user.userNickName }}</div>
      <div>{{ user.userNumber }}</div>
      <div>{{ user.isSuperUser }}</div>
    </div> -->
  </div>
</template>

<script>
export default {
  name: 'UserList',
  data: function() {
    return {
      users: [
        {
          userNumber: 0,
          userNickName: 'name',
          userAvartarSrc: '',
          isSuperUser: true
        },
        {
          userNumber: 1,
          userNickName: 'namename',
          userAvartarSrc: '',
          isSuperUser: false
        }
      ],
    }
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
