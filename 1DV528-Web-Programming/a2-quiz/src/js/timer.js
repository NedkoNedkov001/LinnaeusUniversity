import { finishQuiz } from '/js/quiz.js'

const progressIndicator = document.getElementById('progress-indicator')
let timerInterval
let time = 0
const myScoreDiv = document.getElementById('my-score-div')
const resultText = document.getElementById('result-text')

/**
 *
 */
export function restartTimer () {
  time = 0
}

/**
 * @returns {number} current time
 */
export function getTime () {
  return time
}

/**
 *
 */
export function stopTimer () {
  clearInterval(timerInterval)
}

/**
 * Starts a timer. The game is lost if the timer runs out.
 * @param {number} seconds how many seconds to wait before timeout
 */
export function startTimer (seconds) {
  const totalDuration = seconds * 1000

  /**
   *
   * @param {number} millisecondsElapsed how many milliseconds have passed
   */
  function updateProgressBar (millisecondsElapsed) {
    const newWidth = (millisecondsElapsed / totalDuration) * 100
    progressIndicator.style.width = `${100 - newWidth}%`
  }

  let millisecondsElapsed = 0
  timerInterval = setInterval(function () {
    time += 10
    myScoreDiv.innerHTML = time
    millisecondsElapsed += 10

    updateProgressBar(millisecondsElapsed)

    if (millisecondsElapsed > totalDuration) {
      clearInterval(timerInterval)
      resultText.textContent = 'Time out!'
      finishQuiz(false)
      progressIndicator.style.width = '100%'
    }
  }, 10)
}
