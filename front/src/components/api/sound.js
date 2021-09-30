function soundEffect(soundURL) {
  if (soundURL) {
    var audio = new Audio(soundURL)
    audio.play()
  }
}

export {soundEffect};
