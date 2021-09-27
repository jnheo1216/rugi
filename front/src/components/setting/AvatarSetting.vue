<template>
  <div class="avatarBody">
    <div>
      <div class="hi">아바타설정</div>
      <div class="p-float-label why">
        <Avatar icon="pi pi-search" />
        <InputText
          id="username1"
          type="text"
          v-model="nickname"
        >
        </InputText>
        <label for="username">Nickname</label>
      </div>
    </div>
  </div>
</template>

<script>
import Avatar from 'primevue/avatar'
export default {
  components: { Avatar },
  name: 'AvatarSetting',
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
.why {
  display: block;
  flex-direction: column;
  align-items: center;
}
</style>
