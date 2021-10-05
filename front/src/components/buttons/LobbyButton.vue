<template>
  <div>
    <div class="d-flex justify-content-center">
      <div class="huge-button shadow-sm rounded"
      :class="[$store.state.super ? 'yes-super':'no-super']">
        <Popper content="링크 복사 완료 🍿">
          <a class="huge-button" @click="copyLink()">
            초대링크 복사
          </a>
        </Popper>
      </div>
      <div v-if="$store.state.super" class="huge-button shadow-sm rounded"
      :class="[$store.state.super ? 'yes-super':'no-super']">
      <a class="huge-button" @click="startGame">
        게임 시작
      </a>
      </div>
      <!-- 클립보드 복사용 인풋 -->
    </div>
    <input type="text" id="copyText" style="opacity: 0;">
  </div>
</template>

<script>
import axios from 'axios'
import Popper from "vue3-popper";

import { soundEffectGamestart } from '../api/sound.js'
export default {
  emits: ['viewChange'],
  name: 'LobbyButton',
  components: {
    Popper,
  },
  data: function() {
    return {
      inviteLink: document.location.origin + '/' + document.location.search,
    }
  },
  methods :{
    copyLink: function() {
      soundEffectGamestart()
      const copyText = document.getElementById("copyText"); 
      copyText.value = this.inviteLink;
      copyText.focus()
      copyText.select()
      document.execCommand('copy')
    },
    startGame: function () {
      soundEffectGamestart()
      console.log("게임 시작");
      axios({
        method: 'post',
        url: '/game',
        params: {
          roomId: this.$store.state.currentRoomId
        }
      }).then((res) => {
        console.log('게임 시작 완료', res)
      }).catch((err) => {
        console.log(err.response)
      })
    },
  },
  mounted: function() {
  }
}
</script>

<style>
  .p-button-raised {
    margin-right: 5px;
  }

  :root {
  --popper-theme-text-color: #33333;
  --popper-theme-border-width: 0px;
  --popper-theme-border-style: solid;
  --popper-theme-border-radius: 3rem 3rem 3rem 3rem;
  --popper-theme-padding: 10px;
  --popper-theme-box-shadow: 0 6px 30px -6px rgba(0, 0, 0, 0.25);
  }
  .huge-button {
    font-size: 3rem;
    text-decoration: none;
    color: rgba(0, 0, 0, 0.5);
    padding: 2rem;
    margin: 1rem;
  }
  .huge-button a:hover {
    color:#fc5c7d;
  }
  .no-super {
    /* position:absolute;
    bottom: 40%; */
  }
  .yes-super {
    /* margin-top: 10rem; */
  }
</style>
