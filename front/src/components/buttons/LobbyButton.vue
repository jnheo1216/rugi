<template>
  <div>
    <Button v-if="$store.state.super" class="p-button-raised p-button-text p-button-secondary" @click="$emit('viewChange', 'GameInit')">
      게임시작
    </Button>
    <Button class="p-button-raised p-button-text p-button-secondary" @click="generateLink()">
      초대링크
    </Button>
    <div>{{ inviteLink }}</div>
  </div>
</template>

<script>
export default {
  emits: ['viewChange'],
  name: 'LobbyButton',
  data: function() {
    return {
      inviteLink: '',
    }
  },
  methods :{
    generateLink: function () {
      this.inviteLink = document.location.origin + '/' + document.location.search
    },
    startGame: function () {
      console.log("게임 시작");
    },
  },
  mounted: function() {
  }
}
</script>

<style>
  .p-button-raised {
    margin-right: 5px;
  }
</style>
