<template>
  <div id="body">
    <div class="canvas-box">
      <canvas
        @mousemove="onMouseMove"
        @mousedown="startPainting"
        @mouseup="stopPainting"
        @mouseleave="stopPainting"
        @click="handleCanvasClick"
        @contextmenu="handleCM"
        id="jsCanvas"
        class="canvas"
      ></canvas>
      <div class="controls_range">
        <input
          @input="handleRangeChange"
          type="range"
          id="jsRange"
          min="0.1"
          max="15.0"
          value="7.5"
          step="0.1"
        />
      </div>
    </div>
    <div class="controls">
      <div class="controls_btns">
        <!-- <button
          @click="handleModeClick"
          id="jsMode"
        >
          Fill
        </button> -->
        <button @click="handlePaintClick" :class="{ picked : mode_painting }">
          Paint
        </button>
        <button @click="handleFillClick" :class="{ picked : mode_filling }">
          Fill
        </button>
        <button @click="handleEraseClick" :class="{ picked : mode_erasing }" id="jsEraser">
          Eraser
        </button>
        <button @click="handleClearClick" id="jsClear">
          Clear
        </button>
        <Button @click="handleSaveClick" id="jsSave" icon="pi pi-save" label="Save"></Button>
      </div>
      <div id="jsColors" class="controls_colors">
        <div class="controls_color jsColor" style="background-color: #2c2c2c;"></div>
        <div class="controls_color jsColor" style="background-color: white;"></div>
        <div class="controls_color jsColor" style="background-color: #ff3b30;"></div>
        <div class="controls_color jsColor" style="background-color: #ff9500;"></div>
        <div class="controls_color jsColor" style="background-color: #fc0;"></div>
        <div class="controls_color jsColor" style="background-color: #4cd963;"></div>
        <div class="controls_color jsColor" style="background-color: #5ac8fa;"></div>
        <div class="controls_color jsColor" style="background-color: #0579ff;"></div>
        <div class="controls_color jsColor" style="background-color: #5856d6;"></div>
      </div>
      <ColorPicker v-model="ctxcolor" />
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import 'primevue/resources/primevue.min.css'
const INITIAL_COLOR = "#2c2c2c"
const CANVAS_SIZE = 700


export default {
  name: 'Canvas',
  data: function() {
    return {
      canvas : undefined,
      ctx : undefined,
      colors : undefined,
      range : undefined,
      saveBtn : undefined,

      // 기본  ctx컬러
      ctxcolor : "#2c2c2c",

      painting : false,
      mode_painting : true,
      mode_filling : false,
      mode_erasing : false,
    }
  },
  methods: {
    // painting을 시작했을 때
    startPainting: function () {
      this.painting = true
    },
    // painting을 멈췄을 때
    stopPainting: function () {
      this.painting = false
    },
    onMouseMove: function (event) {
      // mode_filling 모드인 상태에서 클릭말고 드래그 방지
      if (this.mode_filling === false) {
        const x = event.offsetX
        const y = event.offsetY
        if (!this.painting) {
          this.ctx.beginPath()
          this.ctx.moveTo(x, y)
        } else {
          this.ctx.lineTo(x, y)
          this.ctx.stroke()
        }
      }
    },
    // Color 클릭
    handleColorClick: function (event) {
      // console.log(event.target)
      const color = event.target.style.backgroundColor
      this.ctx.strokeStyle = color
      this.ctx.fillStyle = color
    },
    // ColorPicker 클릭
    handleColorPickerClick: function () {
      this.ctx.strokeStyle = '#' + this.ctxcolor
      this.ctx.fillStyle = this.ctxcolor
    },
    handleRangeChange: function (event) {
      const size = event.target.value
      this.ctx.lineWidth = size
    },
    handlePaintClick: function () {
      //mode_filling, mode_erasing이 true이면 둘 다 false로 바꿔줘야함
      if (this.mode_filling === true || this.mode_erasing === true) {
        this.mode_filling = false
        this.mode_erasing = false
      }
      this.ctx.globalCompositeOperation='source-over'
      this.mode_painting = true
    },
    handleFillClick: function () {
      if (this.mode_painting === true || this.mode_erasing === true) {
        this.mode_painting = false
        this.mode_erasing = false
      }
      this.ctx.globalCompositeOperation='source-over'
      this.mode_filling = true
    },
    handleEraseClick: function () {
      // fill은 true인 상태로 들어온다면
      if (this.mode_painting === true || this.mode_filling === true) {
        this.mode_painting = false
        this.mode_filling = false
      }
      this.ctx.globalCompositeOperation='destination-out'
      this.mode_erasing = true
    },
    handleClearClick: function () {
      this.ctx.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE)
    },
    handleCanvasClick: function () {
      if (this.mode_filling) {
        this.ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE)
      }
    },
    // 마우스 우클릭 방지
    handleCM: function (event) {
      event.preventDefault()
    },
    handleSaveClick: function () {
      const image = this.canvas.toDataURL('image/png')
      const link = document.createElement("a")
      link.href = image

      // 일단 save 버튼 누를시에 spring으로 전달하는 것
      // console.log(this.canvas)
      // console.log(image)
      // console.log(link)
      let bstr = atob(image.split(",")[1])
      let n = bstr.length
      let u8arr = new Uint8Array(n)

      while(n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }

      let today = new Date()

      let year = today.getFullYear()
      let month = today.getMonth() + 1
      let date = today.getDate()
      let hours = today.getHours(); // 시
      let minutes = today.getMinutes();  // 분
      let seconds = today.getSeconds();  // 초

      let imgname = year + '-' + month + '-' + date + '-' + hours + '-' + minutes + '-' + seconds

      var file = new File([u8arr], imgname, {type:"mine"})
      console.log(file)

      let form = new FormData()
      form.append('file', file)
      console.log(form)
      axios.post('/question', form, {
        header: { 'Content-Type': 'multipart/form-data' }
      })
        .then((res) => {console.log(res)})
        .catch((err) => {console.log(err)})

      link.download = "kons example"
      // fake click
      link.click()
    },
  },
  watch: {
    ctxcolor() {
      this.handleColorPickerClick()
    }
  },
  mounted: function() {
    this.canvas = document.getElementById("jsCanvas")
    this.ctx = this.canvas.getContext("2d")
    this.colors = document.getElementsByClassName("jsColor")
    // color 선택 이벤트
    if (this.colors) {
      Array.from(this.colors).forEach(color => color.addEventListener("click", this.handleColorClick))
    }
    // pixel modifier
    this.canvas.width = CANVAS_SIZE
    this.canvas.height = CANVAS_SIZE
    // default 설정
    this.ctx.fillStyle = "white"
    this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height)
    this.ctx.strokeStyle = INITIAL_COLOR
    this.ctx.fillStyle = INITIAL_COLOR
    this.ctx.lineWidth = 7.5
  }
}
</script>

<style>
/* @import "../../reset.css"; */
#body {
  /* background-color: #f6f9fc; */
  display: flex;
  align-items: center;
}

.canvas {
  width: 700px;
  height: 700px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 5px 10px rgb(50 50 93 / 11%), 0 5px 10px rgb(0 0 0 / 8%);
  display: flex;
  flex-direction: row;
}

.canvas-box {
  display: flex;
  flex-direction: column;
}

.controls {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.controls .controls_btns {
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.controls_btns button {
  all: unset;
  cursor: pointer;
  background-color: white;
  padding: 5px 0px;
  width: 80px;
  text-align: center;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgb(50 50 93 / 11%), 0 1px 3px rgb(0 0 0 / 8%);
  border: 2px solid rgba(0, 0, 0, 0.2);
  color: solid rgba(0, 0, 0, 0.8);
  text-transform: uppercase;
  font-weight: 600;
  font-size: 12px;
  margin: 5px;
}

.controls_btns button:active {
  transform: scale(0.98);
}

.controls .controls_colors {
  display: flex;
  flex-direction: column;
}

.controls_colors .controls_color {
  width: 50px !important;
  height: 50px !important;
  border-radius: 25px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgb(50 50 93 / 11%), 0 1px 3px rgb(0 0 0 / 8%);
}

.controls_range {
  margin-top: 30px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.controls .picked {
  all: unset;
  cursor: pointer;
  background-color: #2df7ed;
  padding: 5px 0px;
  width: 80px;
  text-align: center;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgb(50 50 93 / 11%), 0 1px 3px rgb(0 0 0 / 8%);
  border: 2px solid rgba(0, 0, 0, 0.2);
  color: solid rgba(0, 0, 0, 0.8);
  text-transform: uppercase;
  font-weight: 600;
  font-size: 12px;
}
</style>
