<template>
  <div id="app-body">
    <router-view />
  </div>
</template>


<script>
export default {
  name: 'App',
  mounted: function() {
    console.log("AppStart");
    this.$store.dispatch('createUser')
    this.$store.dispatch("setRoom", {})
    this.$store.dispatch("setStompClient", "");
    this.$store.dispatch("setCurrentRoomId", "");
    console.log("AppEnd : ");
    console.log(this.$store.state);
  }
}
</script>

<style>
@import "/reset.css";

#app-body {
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  align-self: center;
  justify-content: center;
  min-width: 96vw;
  max-width: 96vw;
  min-height: 92vh;
  max-height: 92vh;
  border-radius: 1rem;
  box-shadow: 0.5rem 0.5rem 1.5rem rgb(38, 38, 38);
}

div {
  box-sizing: border-box;
}

.controls {
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
