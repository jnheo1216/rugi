<template>
    <div class="container gameResult">
    <!-- 퀴즈리스트에 무엇인가가 들어 있어야 출력을 해줘야 할 듯 해 -->
    <div v-if="Object.keys(this.quizList).length">
      <!-- 현재 라운드에 맞는 정보들을 뿌려줘야 할 것 -->
      <!-- 그렇다면 라운드 정보를 가지고 있어야 한다. -->
      <!-- alt 사진을 못 찾았을때 사진을 하나 그리면 좋을 것 같다. -->
      <div class="row">
        <!-- 현재 라운드의 퀴즈리스트, 그 안에 있는 imageList의 image들을 반복문 처리 -->
        <div class="col-8">
          <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <div class="carousel-item active">
                <p>정답</p>
                <img
                  id="question-img"
                  class="d-block w-100 card"
                  :src="`${ this.quizList[currentRound].imgUrl }`"
                  :alt="`${ this.quizList[currentRound].caption }`"
                >
                <p>정답은 "{{this.quizList[currentRound].caption}}" 입니다.</p>
                <div class="sound-button-result">
                  <audio id="res-sound-btn"><source :src="`${this.quizList[currentRound].audioUrl}`"></audio>
                  <button v-if="mute" @click="soundOn"><img src="@/assets/buttons/soundon.png" style="width: 2rem; height:2rem;" alt=""></button>
                  <button v-else @click="soundOn"><img src="@/assets/buttons/soundoff.png" style="width: 2rem; height:2rem;" alt=""></button>
                </div>
              </div>
              <div
                v-for="(image) in this.quizList[currentRound].imageList"
                :key="image.username"
                class="carousel-item"
              >
                <!-- :class="index ? '':'active'" -->
                <p>{{ image.username }} 님의 그림</p>
                <img
                  id="carousel-img"
                  class="d-block w-100 card"
                  :src="`${ image.imgUrl }`"
                  :alt="`${ image.username }`"
                >
                <span style="width:200px">"{{ image.caption }}"</span>
                <span style="font-size:12px">. . . 맞나요 ?</span>
                <div v-if="mute" class="sound-button-result">
                  <a id="res-sound-btn" @click="soundOnElement(image.audioUrl)">
                    <img src="@/assets/buttons/soundon.png" class="img-fluid">
                  </a>
                </div>
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
              <i id="carousel-arrow" class="pi pi-angle-left" style="color:blue"></i>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
              <i id="carousel-arrow" class="pi pi-angle-right"></i>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <div class="col-4">
          <p>정답 공개</p>
          <img class="balloon" src="@/assets/balloon1.png" alt="">
          <!-- <div
            v-for="(image) in this.quizList[currentRound].imageList"
            :key="image.username"
            class="carousel-item"
          > -->
            <!-- <span style="width:200px">{{ image.caption }}</span> -->
            <!-- <span style="font-size:12px">. . . 맞나요 ?</span> -->
          <!-- </div> -->
            <img src="@/assets/RUGI.png" alt="">
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
      alert: "",
      mute: true,
      audio: null,
    }
  },
  methods: {
    soundOn() {
      if(this.mute) {
        const audio = document.getElementById('quizsound')
        audio.volume = 1
        audio.play()
        this.mute = false
      }
      else {
        const audio = document.getElementById('quizsound')
        audio.volume = 0
        this.mute = true
      }
    },
    soundOnElement(audioUrl) {
      if (this.audio) {
        this.audio.pause()
      }
      this.audio = new Audio(audioUrl)
      this.audio.loop = false
      this.audio.play()
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
    console.log('게임리절트가 언마운트 될 때 받아온 정보', this.$store.state.room.quizList)
  }
}
</script>

<style>
.gameResult {
  text-align: center;
}
.carousel-item {
  /* max-height: 700px; */
  height: 70vh;
  /* width: 620px; */
}
#carousel-arrow{
  color: #6A82FB;
}
.sound-button-result {
  width: 2rem !important;
  height: 2rem !important;
  margin: auto;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
}
.sound-button-result:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
}
.balloon {
  position: absolute;
  width: 21vw;
  text-align: center;
  top: 40%;
  left: 20%;
}
</style>
