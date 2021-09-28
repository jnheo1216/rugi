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
    <div>
      <Chat />
    </div>
  </div>
</template>

<script>

import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'
import UserList from '@/components/user/UserList.vue'
import Chat from '@/components/game/Chat.vue'

export default {
  components: {
    GameSetting,
    LobbyButton,
    UserList,
    Chat
  },
  name: 'Lobby',
  data() {
    return {
    }
  },
  methods: {
  },
  created() {
    // console.log('query test', this.$route.query.room)
  },
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
