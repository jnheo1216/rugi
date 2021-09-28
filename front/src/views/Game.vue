<template>
  <div>
    <div>
      <component :is="currentView">
      </component>
    </div>

    <div>
      <Chat />
    </div>
  </div>
</template>

<script>
import Lobby from '@/components/game/Lobby.vue'
import GameInit from '@/components/game/GameInit.vue'
import GamePlay from '@/components/game/GamePlay.vue'
import GameResult from '@/components/game/GameResult.vue'
import Chat from '@/components/game/Chat.vue'

import axios from'axios'

export default {
  name: 'Game',
  components: {
    Lobby,
    GameInit,
    GamePlay,
    GameResult,
    Chat
  },
  data: function() {
    return {
      //currentView의 default값은 lobby로 해야 할 것인가?
      currentView: 'Lobby'
    }
  },
  methods: {
    // query parameter를 가지고 있는 경우만 고려한다.
    enterSession: function () {
      axios({
        method: 'post',
        url: '/room/user',
        params: {
          roomId: this.$store.state.roomId
        }
      }).then((res) => {
        this.$router.push( {name : 'Game', query: {room: this.$store.state.roomId}})
        console.log(res.data)
      }).catch((err) => {
        console.log(err.response)
      })
    }
  },
  mounted: function () {
    this.enterSession()
  }
}
</script>

<style>

</style>
