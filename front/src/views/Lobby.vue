<template>
  <div class="lobbyBody">
    <header>
      <img alt="Vue logo" src="@/assets/ccc.png" height="200" width="200">
    </header>
    <div class="lobbyComponents">
      <UserList />
      <div class="gameBody">
        <GameSetting />
        <LobbyButton />
      </div>
    </div>
  </div>
</template>

<script>
import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'
import UserList from '@/components/user/UserList.vue'

export default {
  components: {
    GameSetting,
    LobbyButton,
    UserList
  },
  name: 'Lobby',
}
</script>

<style>
.lobbyBody {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
.lobbyComponents {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
.gameBody {
  display: flex;
  flex-direction: column;
  padding: 0px 50px;
}
</style>
