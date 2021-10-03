<template>
  <div class="lobbyBody">
    <div>
      <UserList v-if="isRoomExist" />
      <div class="lobbyComponents">
        <GameSetting v-if="isRoomExist && $store.state.super" />
        <!-- 방장이 아닌 사람한테는 다른걸 보여줘도 좋을 듯 -->
        {{ isRoomExist }}
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
    isRoomExist : function () {
      return this.$store.getters.isRoomExist
    },
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
