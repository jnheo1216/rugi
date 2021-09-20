<template>
  <div class="gameSettingBody">
    <header>game set</header>
    <div>라운드  :  <button @click="rounds_idx -= 1">좌</button> {{ rounds[rounds_idx%3] }} <button @click="rounds_idx += 1">우</button></div>
    <div>인원  :  <button @click="counts_idx -= 1">좌</button> {{ counts[counts_idx%5] }} <button @click="counts_idx += 1">우</button></div>
    <div>타이머  :  <button @click="timer_idx -= 1">좌</button> {{ timer[timer_idx%3] }} <button @click="timer_idx += 1">우</button></div>
    <div>커스텀 사용  :  <button @click="is_custom_idx -= 1">좌</button> {{ is_custom[is_custom_idx%2] }} <button @click="is_custom_idx += 1">우</button></div>
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
</style>
