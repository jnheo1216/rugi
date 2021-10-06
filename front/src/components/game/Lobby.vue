<template>
  <div class="lobby-body">
    <div>
      <!-- <UserList v-if="isRoomExist" /> -->
      <div class="lobby-components">
        <GameSetting v-if="this.$store.state.super" />
        <!-- 방장이 아닌 사람한테는 다른걸 보여줘도 좋을 듯 -->
        <LobbyButton />
      </div>
    </div>
  </div>
</template>

<script>

import LobbyButton from '@/components/buttons/LobbyButton.vue'
import GameSetting from '@/components/setting/GameSetting.vue'

export default {
  emits: ['viewChange'],
  components: {
    GameSetting,
    LobbyButton,
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
/* .lobby-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.lobby-components {
  display: flex;
  flex-direction: column;
} */
</style>
