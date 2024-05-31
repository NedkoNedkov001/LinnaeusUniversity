const flashElement = document.querySelector('.flash-message')

if (flashElement) {
  flashElement.style.display = 'none'
  setTimeout(() => {
    fadeIn()
  }, 200)

  setTimeout(() => {
    fadeOut()
  }, 4000)
}

/**
 *
 */
function fadeIn () {
  let opacity = 0
  flashElement.style.opacity = opacity
  flashElement.style.display = 'block'
  const fadeInterval = setInterval(() => {
    if (opacity < 1) {
      opacity += 0.1
      flashElement.style.opacity = opacity
    } else {
      clearInterval(fadeInterval)
    }
  }, 25)
}

/**
 *
 */
function fadeOut () {
  let opacity = 1
  const fadeInterval = setInterval(() => {
    if (opacity > 0) {
      opacity -= 0.1
      flashElement.style.opacity = opacity
    } else {
      clearInterval(fadeInterval)
      flashElement.style.display = 'none'
    }
  }, 25)
}
