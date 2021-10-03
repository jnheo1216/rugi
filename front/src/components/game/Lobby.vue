<template>
  <div class="lobbyBody">
    <div>
      <UserList v-if="isUserListExist" />
      <div class="lobbyComponents">
        <GameSetting v-if="$store.getters.isRoomExist" />
        <LobbyButton @viewChange="$emit('viewChange', $event)" />
      </div>
    </div>
  </div>
</template>

<script>

import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'
import UserList from '@/components/user/UserList.vue'

export default {
  emits: ['viewChange'],
  components: {
    GameSetting,
    LobbyButton,
    UserList,
  },
  name: 'Lobby',
  computed: {
    isUserListExist: function () {
      return Object.keys(this.$store.state.room).length
    }
  }
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
