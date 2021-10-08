<template>
  <div id="app-body">
    <router-view />
  </div>
</template>


<script>
export default {
  name: 'App',
  mounted: async function() {
    if(1 > this.$store.state.nickname || this.$store.state.nickname > 8) {
      await this.$store.dispatch("setNickName", "RUGI")
    }
    console.log("AppStart");
    this.$store.dispatch("createUser")
    this.$store.dispatch("setRoom", {})
    this.$store.dispatch("setStompClient", "");
    console.log("AppEnd : ");
    console.log(this.$store.state);
  }
}
</script>

<style>
/* @import "/reset.css"; */

#app-body {
  background-color: white;
  /* display: flex;
  flex-direction: column; */
  align-items: center;
  align-self: center;
  justify-content: center;
  min-width: 96vw;
  max-width: 96vw;
  min-height: 92vh;
  max-height: 92vh;
  border-radius: 1rem;
  box-shadow: 0.5rem 0.5rem 1.5rem rgb(38, 38, 38);
  font-family: "Elice Digital Baeum",sans-serif !important;
  position:relative;
}

div {
  box-sizing: border-box;
}

</style>
