import { startTimer, stopTimer, getTime, restartTimer } from '/js/timer.js'
import { updateScores, addScore } from '/js/score.js'

let nextUrl = 'https://courselab.lnu.se/quiz/question/1'
let username
let isOver = false

const divElements = {
  welcomeDiv: document.getElementById('welcome-div'),
  quizDiv: document.getElementById('quiz-div'),
  questionDiv: document.getElementById('question-div'),
  inputQuestionDiv: document.getElementById('input-question-div'),
  listQuestionDiv: document.getElementById('list-question-div'),
  resultDiv: document.getElementById('result-div'),
  progressDiv: document.getElementById('progress-div')
}

const inputElements = {
  usernameInput: document.getElementById('username-input'),
  answerInput: document.getElementById('answer-input')
}

const btnElements = document.querySelectorAll('.answer-button')

const textElements = {
  resultText: document.getElementById('result-text'),
  resultStatus: document.getElementById('result-status')
}

const progressIndicator = document.getElementById('progress-indicator')

/**
 * Attach events.
 */
function attachEvents () {
  document.addEventListener('keydown', function (event) {
    if (isOver && event.key === 'Enter') {
      restartQuiz()
    }
  })

  inputElements.usernameInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
      startQuiz()
    }
  })

  inputElements.answerInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
      submitAnswer(inputElements.answerInput.value)
      inputElements.answerInput.value = ''
    }
  })

  btnElements.forEach(function (button) {
    const value = button.value
    button.addEventListener('click', function () {
      submitAnswer(value)
    })
  })
}

/**
 * Starts the quiz by showing the quiz div and hiding the welcome div
 */
async function startQuiz () {
  username = inputElements.usernameInput.value

  divElements.welcomeDiv.style.display = 'none'

  await nextQuestion(null)
  divElements.quizDiv.style.display = 'block'
}

/**
 * Fetches the next question and builds the quiz div to show either a list of alternatives or an input field. Resets the progress indicator to 100% and starts a new timer.
 */
async function nextQuestion () {
  const fetchRes = await fetch(nextUrl)
  const questionJson = await fetchRes.json()

  divElements.questionDiv.innerText = questionJson.question
  nextUrl = questionJson.nextURL

  if (questionJson.alternatives == null) {
    divElements.listQuestionDiv.style.display = 'none'
    divElements.inputQuestionDiv.style.display = 'block'
  } else {
    divElements.listQuestionDiv.style.display = 'block'
    divElements.inputQuestionDiv.style.display = 'none'

    for (let i = 0; i < 4; i++) {
      btnElements[i].textContent = questionJson.alternatives['alt' + (i + 1)]
    }
  }

  divElements.quizDiv.style.display = 'block'

  divElements.progressDiv.style.display = 'block'

  progressIndicator.style.width = '100%'
  if (questionJson.limit != null) {
    startTimer(questionJson.limit)
  } else {
    startTimer(10)
  }
}

/**
 * Submits the selected answer to the server. Checks for the response to see if the game has finished.
 * @param {answer} answer the answer for the question
 */
async function submitAnswer (answer) {
  stopTimer()

  const fetchRes = await fetch(nextUrl, {
    method: 'POST',
    body: JSON.stringify({ answer }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  const answerJson = await fetchRes.json()

  if (fetchRes.status === 400) {
    textElements.resultText.textContent = answerJson.message
    finishQuiz(false)
  } else {
    if (answerJson.nextURL != null) {
      nextUrl = answerJson.nextURL
      nextQuestion()
    } else {
      textElements.resultText.textContent = `${username} - ${getTime()}ms`
      finishQuiz(true)
    }
  }
}

/**
 * Hides the result div and shows the welcome div.
 */
function restartQuiz () {
  nextUrl = 'https://courselab.lnu.se/quiz/question/1'
  isOver = false
  restartTimer()

  divElements.welcomeDiv.style.display = 'block'
  divElements.resultDiv.style.display = 'none'
}

/**
 *
 * @param {boolean} win wether to show the winning or losing texts.
 */
export function finishQuiz (win) {
  divElements.quizDiv.style.display = 'none'
  divElements.progressDiv.style.display = 'none'

  isOver = true

  if (win) {
    textElements.resultStatus.textContent = 'You win! Press Enter to continue'
    divElements.resultDiv.style.display = 'block'

    const currTime = getTime()
    addScore(username, currTime)
  } else {
    textElements.resultStatus.textContent = 'You lose! Press Enter to continue'
    divElements.resultDiv.style.display = 'block'
  }
  updateScores()
}

attachEvents()
updateScores()
