<template>
  <div>게임 결과 출력</div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'GameResult',
  data: function () {
    return
  },
  mounted: function () {
    let form = new FormData()
    form.append('file', this.$store.state.file)
    axios({
      method: 'post',
      url: '/question',
      data: form,
      header: {
        'Content-Type': 'multipart/form-data'
      },
    }).then((res) => {
      console.log('이얏호 성공이지롱', res)
    }).catch((err) => {
      console.log('ㅠㅠ 실패', err.response)
    })
  }
}
</script>

<style>

</style>
