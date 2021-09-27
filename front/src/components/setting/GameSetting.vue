<template>
  <div class="gameSettingBody">
    <header>game setting</header>
    <div class="set">
      라운드
      <Button
        icon="pi pi-arrow-left"
        style="fontSize: 0.1rem"
        class="p-button-rounded p-button-text"
        @click="rounds_idx -= 1"
      >
      </Button>
      {{ rounds[rounds_idx%3] }}
      <Button
        icon="pi pi-arrow-right"
        class="p-button-rounded p-button-text"
        @click="rounds_idx += 1"
      >
      </Button>
    </div>
    <div class="set">
      인원
      <Button
        icon="pi pi-arrow-left"
        class="p-button-rounded p-button-text"
        @click="counts_idx -= 1"
      >
      </Button>
      {{ counts[counts_idx%5] }}
      <Button
        icon="pi pi-arrow-right"
        class="p-button-rounded p-button-text"
        @click="counts_idx += 1"
      >
      </Button>
    </div>
    <div class="set">
      타이머
      <Button
        icon="pi pi-arrow-left"
        class="p-button-rounded p-button-text"
        @click="timer_idx -= 1"
      >
      </Button>
      {{ timer[timer_idx%3] }}
      <Button
        icon="pi pi-arrow-right"
        class="p-button-rounded p-button-text"
        @click="timer_idx += 1"
      >
      </Button>
    </div>
    <div class="set">
      커스텀 사용
      <Button
        icon="pi pi-arrow-left"
        class="p-button-rounded p-button-text"
        @click="is_custom_idx -= 1"
      >
      </Button>
      {{ is_custom[is_custom_idx%2] }}
      <Button
        icon="pi pi-arrow-right"
        class="p-button-rounded p-button-text"
        @click="is_custom_idx += 1"
      >
      </Button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GameSetting',
  data: function() {
    return {
      rounds: {0:1, 1:2, 2:3},
      rounds_idx: 999,
      counts: {0:4, 1:5, 2:6, 3:7, 4:8},
      counts_idx: 999,
      timer: {0:60, 1:80, 2:100},
      timer_idx: 999,
      is_custom: {0:true, 1:false},
      is_custom_idx: 999,
    }
  },

}
</script>

<style>
.gameSettingBody {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-top: 50px;
  padding: 50px 0px;
}
.set {
  font-size: 30px;
}
</style>
