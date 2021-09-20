<template>
  <div class="avatarBody">
    <div>
      <div>아바타설정</div>
      <input
        v-model="nickname"
        type="text"
        placeholder="닉네임"
      >
    </div>
  </div>
</template>

<script>
export default {
  name: 'Avatar',
  data: function() {
    return {
      nickname : '',
    }
  }
}
</script>

<style>
.avatarBody {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}
</style>
