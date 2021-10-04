<template>
  <body>
    <router-view />
  </body>
</template>


<script>
export default {
  name: 'App',
  mounted: function() {
    this.$store.dispatch('createUser')
    this.$store.dispatch("setRoom", {})
    this.$store.dispatch("setStompClient", "");
    this.$store.dispatch("setCurrentRoomId", "");
  }
}
</script>

<style>
@import "/reset.css";
@font-face {
    font-family: 'EliceDigitalBaeum_Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/EliceDigitalBaeum_Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
body {
  padding: 10rem;
  background: #000;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  padding: 50px 0px;
  /* border: linear-gradient(#FC5C7D, 50%, #6A82FB) 5px solid !important; */
  border:magenta 5px solid !important;
}
div {
  /* background: maroon; */
  box-sizing: border-box;
  /* border: magenta 5px solid !important; */
}
html, input, body, div, p, button {
  font-family: 'EliceDigitalBaeum_Bold' !important;
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
