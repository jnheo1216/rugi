<template>
    <div class="container gameresult">
    <!-- 퀴즈리스트에 무엇인가가 들어 있어야 출력을 해줘야 할 듯 해 -->
    <div v-if="Object.keys(this.quizList).length">
      <!-- 현재 라운드에 맞는 정보들을 뿌려줘야 할 것 -->
      <!-- 그렇다면 라운드 정보를 가지고 있어야 한다. -->
      <!-- alt 사진을 못 찾았을때 사진을 하나 그리면 좋을 것 같다. -->
      <div class="row">
        <div class="col">
          <p> 현재 문제 캡셔닝 </p>
          <div>
            <p>
              {{ this.quizList[currentRound].caption }}
            </p>
            <!-- <p>
              {{this.quizList[currentRound].audioUrl}}
            </p> -->
          </div>
          <p> 이게 정답이에요 </p>
          <div>
            <img :src="`${this.quizList[currentRound].imgUrl}`" alt="answer">
          </div>
            <div class="sound-button">
              <audio id="quizsound"><source :src="`${this.quizList[currentRound].audioUrl}`"></audio>
              <button v-if="mute" @click="soundOn"><img src="@/assets/buttons/soundon.png" style="width: 2rem; height:2rem;" alt=""></button>
              <button v-else @click="soundOn"><img src="@/assets/buttons/soundoff.png" style="width: 2rem; height:2rem;" alt=""></button>
            </div>
        </div>
        <!-- 현재 라운드의 퀴즈리스트, 그 안에 있는 imageList의 image들을 반복문 처리 -->
        <div class="col">
          <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <!-- <div class="carousel-item active">
                <img src="https://source.unsplash.com/random" class="d-block w-100" alt="https://source.unsplash.com/random">
              </div> -->
              <div
                v-for="(image, index) in this.quizList[currentRound].imageList"
                :key="image.username"
                class="carousel-item"
                :class="index ? '':'active'"
              >
                <!-- <div class="carousel-item"> -->
                  <!-- <div class="col card"> -->
                    <p>
                      {{ image.username }} 님의 그림
                    </p>
                    <img
                      class="d-block w-100"
                      :src="`${ image.imgUrl }`"
                      alt=""
                    >
                    <span style="width:200px">
                      "{{ image.caption }}" !!
                    </span>
                    <div v-if="mute" class="sound-button">
                      <a @click="soundOnElement(image.audioUrl)">
                        <img src="@/assets/buttons/soundon.png" class="img-fluid">
                      </a>
                    </div>
                  <!-- </div> -->
                <!-- </div> -->
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
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
      const audio = new Audio(audioUrl)
      audio.loop = false
      audio.play()
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
  /* border-style: solid;
  border-color: black; */
  /* border-width: 1px; */
}
.col .card {
  padding: 10px;
}
.userDrawings {
  height: 200px;
  width: 200px;
}
.sound-button {
  width: 2rem !important;
  height: 2rem !important;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
  align-items: end;
}
.sound-button:hover {
  background-color: #6a82fb;
  border-radius: 50%;
  border: none;
}
</style>
