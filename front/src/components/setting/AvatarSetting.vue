<template>
  <div class="avatarBody">
    <div>
      <div class="hi">
        아바타설정
      </div>
      <Avatar class="avatar" image="@/assets/logo.png" size="xlarge" shape="circle" />
      <div class="p-float-label why">
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
// import MainButton from '@/components/MainButton'
export default {
  components: {
  },
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
  padding: 25px 0px;
}
.why {
  margin-top: 25px;

}

</style>
