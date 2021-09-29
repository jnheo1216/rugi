<template>
  <div class="lobbyBody">
    <div>
      <UserList />
      <div class="lobbyComponents">
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
    UserList,
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
/* .lobbyComponents {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
} */
.lobbyComponents {
  display: flex;
  flex-direction: column !important;
  padding: 0px 50px;
}
</style>
