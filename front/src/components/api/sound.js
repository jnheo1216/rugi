function soundEffect(soundID) {
  const enter = document.querySelector(soundID)
  enter.loop = false
  enter.play()
}

export {soundEffect};
