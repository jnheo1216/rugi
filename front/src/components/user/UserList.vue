<template>
  <div>
    userlist
    <div v-for="user in users" :key="user.userNumber">
      <div>{{ user.userNickName }}</div>
      <div>{{ user.userNumber }}</div>
      <div>{{ user.isSuperUser }}</div>
    </div>
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

</style>
