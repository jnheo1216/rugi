<template>
  <div class="avatarBody">
    <div>
      <div class="hi">아바타설정</div>
      <span class="p-float-label">
        <InputText
          id="username1"
          type="text"
          v-model="nickname"
        >
        </InputText>
        <label for="username">Nickname</label>
      </span>
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
.hi {
  padding: 50px 0px;
}
</style>
