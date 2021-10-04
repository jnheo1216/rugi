<template>
  <div class="userListBody">
    <span>User</span>
    <AvatarGroup class="p-mb-3">
      <div class="avatarImg" v-for="user in userlist" :key="user.nickname">
        <Avatar
          :image="require(`@/assets/avatar/${user.avatar}.png`)"
          size="xlarge"
          shape="circle"
        />
        <div>
          <span class="nickname">{{ user.nickname }}</span>
        </div>
      </div>
      <Avatar :label="`${ usernum }`" shape="circle" size="large" style="background-color:#9c27b0; color: #ffffff" />
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
.userListBody {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 10px;
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
.nickname{
  display: inline-block;
  text-align: center;
  opacity: 0;
  width:60px;
}
.avatarImg:hover .nickname{
  opacity: 0.7;
}
</style>
