<template>
  <body>
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
    <div class="controls">
      <div class="controls_range">
        <input
          @input="handleRangeChange"
          type="range"
          id="jsRange"
          min="0.1"
          max="5.0"
          value="2.5"
          step="0.1"
        />
      </div>
      <div class="controls_btns">
        <button
          @click="handleModeClick"
          id="jsMode"
        >
          Fill
        </button>
        <button
          @click="handleClearClick"
          id="jsClear"
        >
          Clear
        </button>
        <button
          @click="handleSaveClick"
          id="jsSave"
        >
          Save
        </button>
      </div>
      <div
        id="jsColors"
        class="controls_colors"
      >
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
    </div>
  </body>
</template>

<script>
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
      mode : undefined,
      saveBtn : undefined,

      painting : false,
      filling : false,
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
      const x = event.offsetX
      const y = event.offsetY
      if (!this.painting) {
        this.ctx.beginPath()
        this.ctx.moveTo(x, y)
      } else {
        this.ctx.lineTo(x, y)
        this.ctx.stroke()
      }
    },
    handleColorClick: function (event) {
      const color = event.target.style.backgroundColor
      this.ctx.strokeStyle = color
      this.ctx.fillStyle = color
    },
    handleRangeChange: function (event) {
      const size = event.target.value
      this.ctx.lineWidth = size
    },
    handleModeClick: function () {
      if (this.filling === true) {
        this.filling = false
        this.mode.innerText = "Fill"
      } else {
        this.filling = true
        this.mode.innerText = "Paint"
      }
    },
    handleCanvasClick: function () {
      if (this.filling) {
        this.ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE)
      }
    },
    // 마우스 우클릭 방지
    handleCM: function (event) {
      event.preventDefault()
    },
    handleSaveClick: function () {
      const image = this.canvas.toDataURL()
      const link = document.createElement("a")
      link.href = image
      link.download = "kons example"
      // fake click
      link.click()
    },
    handleClearClick: function () {
      this.ctx.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE)
    }
  },
  mounted: function() {
    this.canvas = document.getElementById("jsCanvas")
    this.ctx = this.canvas.getContext("2d")
    this.colors = document.getElementsByClassName("jsColor")
    this.mode = document.getElementById("jsMode")
    console.log(this.mode)
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
    this.ctx.lineWidth = 2.5
  }
}
</script>

<style>
@import "reset.css";
body {
  background-color: #f6f9fc;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
}

.canvas {
  width: 700px;
  height: 700px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 4px 6px rgb(50 50 93 / 11%), 0 1px 3px rgb(0 0 0 / 8%);
}

.controls {
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.controls .controls_btns {
  margin-bottom: 30px;
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
}

.controls_btns button:active {
  transform: scale(0.98);
}

.controls .controls_colors {
  display: flex;
}

.controls_colors .controls_color {
  width: 50px;
  height: 50px;
  border-radius: 25px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgb(50 50 93 / 11%), 0 1px 3px rgb(0 0 0 / 8%);
}

.controls .controls_range {
  margin-bottom: 30px;
}
</style>
