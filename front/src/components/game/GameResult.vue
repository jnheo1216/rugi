<template>
    <div class="container gameResult">
    <!-- 퀴즈리스트에 무엇인가가 들어 있어야 출력을 해줘야 할 듯 해 -->
    <div v-if="Object.keys(this.quizList).length">
      <!-- 현재 라운드에 맞는 정보들을 뿌려줘야 할 것 -->
      <!-- 그렇다면 라운드 정보를 가지고 있어야 한다. -->
      <!-- alt 사진을 못 찾았을때 사진을 하나 그리면 좋을 것 같다. -->
      <div class="row">
        <!-- 현재 라운드의 퀴즈리스트, 그 안에 있는 imageList의 image들을 반복문 처리 -->
        <div id="carousel-container" class="col-8">
          <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <div class="empty-block"></div>
              <div class="carousel-item active">
                <p class="carousel-title">정답</p>
                  <img
                    id="question-img"
                    class="d-block h-95 py-auto"
                    :src="`${ this.quizList[currentRound].imgUrl }`"
                    :alt="`${ this.quizList[currentRound].caption }`"
                  >
                <p class="mt-3">정답은 "{{this.quizList[currentRound].caption}}" 입니다.</p>
                <div>
                  <audio id="quizsound"><source :src="`${this.quizList[currentRound].audioUrl}`"></audio>
                  <div class="sound-button-result mt-3">
                  <a id="res-sound-btn" @click="soundOn()">
                    <img src="@/assets/buttons/soundon.png" class="img-fluid">
                  </a>
                </div>
                </div>
              </div>
              <div
                v-for="(image) in this.quizList[currentRound].imageList"
                :key="image.username"
                class="carousel-item"
              >
                <!-- :class="index ? '':'active'" -->
                <p class="carousel-title">{{ image.username }} 님의 그림</p>
                <div>
                  <img
                    id="carousel-img"
                    class="d-block w-75 card"
                    :src="`${ image.imgUrl }`"
                    :alt="`${ image.username }`"
                  >

                </div>
                <div class="mt-3">
                  <span style="width:200px">"{{ image.caption }}"</span>
                  <span style="font-size:12px">. . . 맞나요 ?</span>
                </div>
                <div class="sound-button-result mt-3">
                  <a id="res-sound-btn" @click="soundOnElement(image.audioUrl)">
                    <img src="@/assets/buttons/soundon.png" class="img-fluid">
                  </a>
                </div>
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
              <img src="@/assets/buttons/arrowL.png" class="img-fluid">
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
              <img src="@/assets/buttons/arrowR.png" class="img-fluid">
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <div class="col-4">
          <!-- <img class="" src="@/assets/balloon1.png" alt=""> -->
          <div
            v-for="(image) in this.quizList[currentRound].imageList"
            :key="image.username"
            class="carousel-item"
          >
            <!-- <p id="tmp" style="width:100vw">{{ image.caption }}</p> -->
            <span style="font-size:12px; z-index: 11;">. . . 맞나요 ?</span>
          </div>
            <img id="result-rugi" src="@/assets/RUGI.png" alt="">
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
    console.log("GameResult", this.$store.state.room.id);
    let form = new FormData()
    form.append('file', this.$store.state.file)
    axios({
      method: 'post',
      url: '/question',
      data: form,
      params: {
        roomId: this.$store.state.room.id,
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
.carousel-inner {
  height: 70vh;
  align-items: center;
  vertical-align: middle;
  justify-content: center;
}
.carousel-item {
  /* height: 70vh; */
}
#carousel-img {
  margin: auto;
}
#question-img {
  margin: auto;
  max-height: 40vh;
  overflow: hidden;
}
.carousel-title {
  font-size: 1.7rem;
}
.sound-button-result {
  width: 2rem !important;
  height: 2rem !important;
  margin: auto;
  /* box-shadow: 0.2rem 0.2rem 0.5rem #cacaca; */
  border-radius: 50%;
  border: none;
  background-color: transparent;
}
.sound-button-result:hover {
  padding-top: 2px;
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
}
.balloon {
  /* position: absolute;
  width: 21vw;
  text-align: center;
  top: 40%;
  left: 20%; */
}
#result-rugi {
  position: absolute;
  right: 35%;
  bottom: 0%;
  width: 20vw;
  /* height: 10vh; */
}


.empty-block {
  height: 100px;
}
</style>
