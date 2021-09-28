<template>
  <div>
    <span>Game.vue</span>
    <Lobby />
    <GameInit />
    <GamePlay />
    <GameResult />
  </div>
</template>

<script>
import Lobby from '@/components/game/Lobby.vue'
import GameInit from '@/components/game/GameInit.vue'
import GamePlay from '@/components/game/GamePlay.vue'
import GameResult from '@/components/game/GameResult.vue'
export default {
  name: 'Game',
  components: {
    Lobby,
    GameInit,
    GamePlay,
    GameResult
  },
  data: function() {
    return {

    }
  }
}
</script>

<style>

</style>
