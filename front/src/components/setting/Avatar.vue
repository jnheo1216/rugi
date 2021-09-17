<template>
  <body class="avatarBody">
    <div>
      <div>아바타설정</div>
      <input
        v-model="nickname"
        type="text"
        placeholder="닉네임"
      >
    </div>
    <MainButton :nickname="nickname" />
  </body>
</template>

<script>
import MainButton from '../buttons/MainButton.vue'

export default {
  name: 'Avatar',
  components: {
    MainButton
  },
  data: function() {
    return {
      nickname : '',
    }
  }
}
</script>

<style>
@import "../../reset.css";
.avatarBody {
  background-color: #f6f9fc;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
</style>
