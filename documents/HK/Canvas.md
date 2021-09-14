# Canvas

> Vanilla JavaScript로 진행.
>
> SETUP + STYLES 작업 진행



### index.html

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>PaintJS</title>
  </head>
  <body>
    <canvas id="jsCanvas" class="canvas"></canvas>
    <div class="controls">
      <div class="controls_range">
        <input 
          type="range" 
          id="jsRange" 
          min="0.1"
          max="5.0" 
          value="2.5" 
          step="0.1"
        />
      </div>
      <div class="controls_btns">
        <button id="jsMode">Fill</button>
        <button id="jsSave">Save</button>
      </div>
      <div id="jsColors" class="controls_colors">
        <div class="controls_color jsColor" style="background-color: #2c2c2c  ;"></div>
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
    <script src="app.js"></script>
  </body>
</html>

```



### styles.css

```css
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
```



### reset.css

```css
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
```



### app.js

```javascript
const canvas = document.getElementById("jsCanvas");
const ctx = canvas.getContext("2d");
const colors = document.getElementsByClassName("jsColor")
const range = document.getElementById("jsRange")
const mode = document.getElementById("jsMode")
const saveBtn = document.getElementById("jsSave")

const INITIAL_COLOR = "#2c2c2c"
const CANVAS_SIZE = 700

// pixel modifier 실제 픽셀 설정
canvas.width = CANVAS_SIZE;
canvas.height = CANVAS_SIZE;
//context의 default 설정
ctx.fillStyle = "white" //13, 14 line으로 캔버스의 기본 바탕색을 지정해주지 않으면
ctx.fillRect(0, 0, canvas.width, canvas.height) // 이미지를 저장했을때 투명 배경이 된다.
ctx.strokeStyle = INITIAL_COLOR;
ctx.fillstyle = INITIAL_COLOR;
ctx.lineWidth = 2.5;

let painting = false;
let filling = false;

// painting을 시작했을 때
function startPainting() {
  painting = true;
}

// painting을 멈췄을 때
function stopPainting() {
  painting = false;
}
// clinetX는 browser에서의 마우스 좌표, offsetX는 canvas위에서 마우스의 좌표
function onMouseMove(event) {
  // console.log(event)
  const x = event.offsetX
  const y = event.offsetY
  if (!painting) {
    ctx.beginPath();
    ctx.moveTo(x, y); 
  } else {
    ctx.lineTo(x, y)
    ctx.stroke()
  }
  // console.log(x, y)
}

// 마우스 클릭이 끝났을때
function onMouseUp(event) {
  // console.log(event)
  // console.log(ctx)
  stopPainting()
}

function handleColorClick(event) {
  // console.log(event.target.style)
  const color = event.target.style.backgroundColor;
  // console.log(color)
  ctx.strokeStyle = color;
  ctx.fillStyle = color;
}

function handleRangeChange(event) {
  console.log(event.target.value);
  const size = event.target.value;
  ctx.lineWidth = size
}

function handleModeEClick() {
   if (filling === true) {
     filling = false;
     mode.innerText = "Fill"
   } else {
     filling = true;
     mode.innerText = "Paint"
   }
}

function handleCanvasClick() {
  if (filling) {
    ctx.fillRect(0, 0, canvas.width, canvas.height)
  }
}

// 마우스 우클릭 방지 SAVE 버튼을 통해서 이미지 가져갈 수 있게
function handleCM(event) {
  event.preventDefault()
}

// a html mdn download googling
function handleSaveClick() {
  // canvas to dataurl googling
  const image = canvas.toDataURL() //"image/jpeg"를 괄호에 넣으면 저장형식 지정가능 디폴트는 png
  // console.log(image)
  // a 태그를 만들어주고 download 속성에 image url을 넣음
  const link = document.createElement("a")
  link.href = image
  link.download = "PaintJS[EXPORT]"
  // fake click
  link.click()
}
if (canvas) {
  canvas.addEventListener("mousemove", onMouseMove)
  canvas.addEventListener("mousedown", startPainting)
  canvas.addEventListener("mouseup", onMouseUp)
  canvas.addEventListener("mouseleave", stopPainting)
  canvas.addEventListener("click", handleCanvasClick)
  canvas.addEventListener("contextmenu", handleCM)
}
// colors를 배열의 형태로 만들어 줌
console.log(colors)
// console.log(Array.from(colors))
if (colors) {
  Array.from(colors).forEach(color => color.addEventListener("click", handleColorClick))
}

if (range) {
  range.addEventListener("input", handleRangeChange)
}

if (mode) {
  mode.addEventListener("click", handleModeEClick)
}

if (saveBtn) {
  saveBtn.addEventListener("click", handleSaveClick)
}

```
