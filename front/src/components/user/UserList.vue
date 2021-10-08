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
        style="background-color:#fc5c7d; color: #ffffff; box-shadow: none;"
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
  },
}
</script>

<style>
.user-list-body {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  
  padding: 4rem 1rem 0rem 2rem;
  /* box-shadow: 2px 2px 5px #cacaca; */
  border-radius: 1.5rem;
  /* background-color: rgb(247, 247, 247) !important; */
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
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  box-sizing: content-box;
  padding:0.4rem;
}
.p-avatar-image {
  width: 80px !important;
  height: 80px !important;
}
</style>
