<template>
  <body>
    <header>
      <img alt="Vue logo" src="../assets/ccc.png" height="200" width="200">
    </header>
    <h1>서 비 스  명</h1>
    <div>
      <Avatar />
      <MainButton />
    </div>
  </body>
</template>

<script>
import Avatar from '../components/setting/Avatar.vue'
import MainButton from '../components/buttons/MainButton.vue'

export default {
  name: 'Home',
  components: {
    Avatar,
    MainButton,
  },
}
</script>
<style>
@import "../reset.css";
body {
  background-color: #f6f9fc;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
</style>
