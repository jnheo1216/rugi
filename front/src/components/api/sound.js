function soundEffect() {
  var audio = new Audio(require("@/assets/sounds/enter.mp3"))
  audio.play()
}
function soundEffectChat() {
  var audio = new Audio(require("@/assets/sounds/chat.wav"))
  audio.play()
}
function soundEffectGamestart() {
  var audio = new Audio(require("@/assets/sounds/pingpong.mp3"))
  audio.play()
}

export {soundEffect, soundEffectChat, soundEffectGamestart};
