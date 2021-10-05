<template>
  <div>
    게임 결과 출력
    <!-- 퀴즈리스트에 무엇인가가 들어 있어야 출력을 해줘야 할 듯 해 -->
    <div v-if="Object.keys(this.quizList).length">
      <!-- 현재 라운드에 맞는 정보들을 뿌려줘야 할 것 -->
      <!-- 그렇다면 라운드 정보를 가지고 있어야 한다. -->
      <!-- alt 사진을 못 찾았을때 사진을 하나 그리면 좋을 것 같다. -->
      <div>
        <p> 현재 문재 캡셔닝 </p>
        {{ this.quizList[currentRound].caption }}
        <div
          class="captionResult"
          v-for="image in this.quizList[currentRound].imageList"
          :key="image.usernageme"
        >
          {{ image.username }}
          {{ image.caption }}
          <img
            class="userDrawings"
            :src="`${ image.imgUrl }`"
            alt=""
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'GameResult',
  data: function () {
    return {
      quizList: {}
    }
  },
  computed: {
    currentRound: function () {
      return this.$store.state.room.currentRound - 1
    }
  },
  mounted: function () {
    let form = new FormData()
    form.append('file', this.$store.state.file)
    axios({
      method: 'post',
      url: '/question',
      data: form,
      params: {
        roomId: this.$store.state.currentRoomId,
        nickname: this.$store.state.nickname
      },
      header: {
        'Content-Type': 'multipart/form-data',
      },
    }).then((res) => {
      console.log('이얏호 성공이지롱', res)
      // 성공할 때 마다 quizList가 갱신 -> 1 라운드의 정보를 담고 -> 1, 2 라운드의 정보를 담고
      this.quizList = res.data.quizList
    }).catch((err) => {
      // 실패하면 quizList를 비워줘야 할까?
      console.log('ㅠㅠ 실패', err.response)
    })
  }
}
</script>

<style>
.captionResult {
  display: flex;
  flex-direction: column !important;
  align-items: center;
}

.userDrawings {
  height: 250px;
  width: 250px;
}
</style>
