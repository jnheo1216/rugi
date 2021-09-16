<template>
  <div>
    <div>로비에서 게임시작이랑 링크생성</div>
    <button @click="startGame()">게임시작</button>
    <button @click="generateLink()">초대링크</button>
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
      this.inviteLink = document.location.href
    },
    startGame: function () {
    },
  }
}
</script>

<style>

</style>
