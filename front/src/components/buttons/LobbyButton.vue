<template>
  <div>
    <Button
      class="p-button-raised p-button-text p-button-secondary"
      @click="startGame()"
    >
      게임시작
    </Button>
    <Button
      class="p-button-raised p-button-text p-button-secondary"
      @click="generateLink()"
    >
      초대링크
    </Button>
    <div>{{ inviteLink }}</div>
  </div>
</template>

<script>
export default {
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
    },
  },
  mounted: function() {
    console.log('nickname', this.$store.state.nickname)
    console.log('아거아ㅣ겅미나ㅓ리ㅏㅁㄴ어기ㅏ',document.location)
  }
}
</script>

<style>
  .p-button-raised {
    margin-right: 5px;
  }
</style>
