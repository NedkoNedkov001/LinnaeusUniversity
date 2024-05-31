/**
 *
 */
function makeWeatherAppLauncherMove () {
  const applicationWeather = document.querySelector(
    '.application-icon-weather'
  )
  applicationWeather.addEventListener('mouseover', moveRandomly)

  /**
   *
   */
  function moveRandomly () {
    const [randX, randY] = createRandomCoordinates()
    this.style.position = 'absolute'
    this.style.left = randX + 'px'
    this.style.bottom = randY + 'px'

    console.log('test')
  }
}

/**
 *
 */
function makeClockRandom () {
  let intervalSpeed = 100
  const clockTimeDiv = document.querySelector('.desktop-clock-div')
  const clockTime = document.querySelector('.desktop-clock-text')
  let interval = setInterval(updateClock, intervalSpeed)
  clockTimeDiv.addEventListener('mouseover', () => {
    clearInterval(interval)
  })

  clockTimeDiv.addEventListener('mouseout', () => {
    intervalSpeed = 100
    clearInterval(interval)
    interval = setInterval(updateClock, intervalSpeed)
  })

  /**
   *
   */
  function updateClock () {
    const hours = Math.floor(Math.random() * 24)
      .toString()
      .padStart(2, '0')
    const minutes = Math.floor(Math.random() * 60)
      .toString()
      .padStart(2, '0')
    const formattedTime = `${hours}:${minutes}`
    clockTime.textContent = formattedTime

    let [R, G, B] = createRandomRGB()
    clockTime.style.color = `rgb(${R}, ${G}, ${B})`;

    [R, G, B] = createRandomRGB()
    clockTimeDiv.style.backgroundColor = `rgb(${R}, ${G}, ${B})`
  }
}

/**
 * @returns {Array} RGB values
 */
function createRandomRGB () {
  const R = Math.floor(Math.random() * 255)
  const G = Math.floor(Math.random() * 255)
  const B = Math.floor(Math.random() * 255)
  return [R, G, B]
}

/**
 * @returns {Array} X, Y values
 */
function createRandomCoordinates () {
  const randX = Math.random() * 500
  const randY = Math.random() * 1000

  return [randX, randY]
}

/**
 *
 */
function makeClockSpin () {
  const clockTimeDiv = document.querySelector('.desktop-clock-div')
  setInterval(spinClock, 100)
  let isIncreasingSpeed = false
  let speed = 10.0
  const speedIncrement = 0.1

  /**
   *
   */
  function spinClock () {
    if (isIncreasingSpeed) {
      speed = parseFloat(speed) + speedIncrement
    } else {
      speed = parseFloat(speed) - speedIncrement
    }
    clockTimeDiv.style.animation = `spin ${speed}s linear infinite`

    if (speed < 0.1) {
      isIncreasingSpeed = true
    } else if (speed > 10) {
      isIncreasingSpeed = false
    }
  }
}

/**
 *
 */
function makeGameImpossible () {}

makeWeatherAppLauncherMove()
makeClockRandom()
makeClockSpin()
makeGameImpossible()
