<template>
  <div>
    <div>로비</div>
    ----------------------------------
    <UserList/>
    ----------------------------------
    <GameSetting/>
    ----------------------------------
    <LobbyButton/>
  </div>
</template>

<script>
import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'
import UserList from '@/components/user/UserList.vue'

export default {
  mounted: {
    // 입장하는순간 소켓 걸어줘야돼
  },
  components: {
    GameSetting,
    LobbyButton,
    UserList
  },
  name: 'Lobby',
}
</script>

<style>

</style>
