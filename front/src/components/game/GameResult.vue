<template>
  <div>
    게임 결과 출력
    <!-- 퀴즈리스트에 무엇인가가 들어 있어야 출력을 해줘야 할 듯 해 -->
    <div v-if="Object.keys(this.quizList).length">
      <!-- 현재 라운드에 맞는 정보들을 뿌려줘야 할 것 -->
      <!-- 그렇다면 라운드 정보를 가지고 있어야 한다. -->
      <!-- alt 사진을 못 찾았을때 사진을 하나 그리면 좋을 것 같다. -->
      <div class="test">
        <p> 현재 문재 캡셔닝 </p>
        <div>
          {{ this.quizList[currentRound].caption }}
        </div>
        <!-- 현재 라운드의 퀴즈리스트, 그 안에 있는 imageList의 image들을 반복문 처리 -->
        <div
          class="captionResult"
          v-for="image in this.quizList[currentRound].imageList"
          :key="image.usernageme"
        >
          <span>
            {{ image.username }}
          </span>
          <span>
            <!-- TTS 할 수 있도록 기능 넣어주면 될 듯 -->
            {{ image.caption }}
          </span>
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
      alert: ""
    }
  },
  computed: {
    quizList: function () {
      return this.$store.getters.quizList
    },
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
    }).catch((err) => {
      console.log('ㅠㅠ 실패', err.response)
    })
  },
  unmounted: function () {
    console.log('게임리절트가 언마운트 될 때 받아온 정보', this.$store.room.quizList)
  }
}
</script>

<style>
.captionResult {
  display: flex;
  flex-direction: column !important;
  align-items: center;
  border-style: solid;
  border-color: black;
  border-width: 1px;
}
.userDrawings {
  height: 250px;
  width: 250px;
}
.test {
  display: flex;
  flex-direction: row;
}
</style>
