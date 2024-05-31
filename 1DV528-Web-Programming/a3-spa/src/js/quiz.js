/**
 * Setup the quiz app, asking for NxN input.
 * @param {Element} window window to attach the setup to.
 */
export async function setupQuizApp (window) {
  const mainElement = window.querySelector('.window-main')

  const quizSetup = document.createElement('div')
  quizSetup.classList.add('quiz-setup')

  mainElement.appendChild(quizSetup)

  const startButton = document.createElement('button')
  startButton.classList.add('quiz-start-button')
  startButton.textContent = 'Start Quiz'
  startButton.addEventListener('click', startQuiz)
  quizSetup.appendChild(startButton)

  /**
   *
   */
  async function startQuiz () {
    let seconds = 0

    const timerInterval = setInterval(() => {
      seconds++
    }, 1000)
    mainElement.innerHTML = ''

    let nextUrl = 'https://courselab.lnu.se/quiz/question/1'

    const question = document.createElement('div')
    question.classList.add('quiz-question')

    nextQuestion()

    /**
     *
     */
    async function nextQuestion () {
      question.innerHTML = ''

      const fetchRes = await fetch(nextUrl)
      const questionJson = await fetchRes.json()

      const questionText = document.createElement('p')
      questionText.classList.add('quiz-question-text')
      questionText.textContent = questionJson.question

      question.appendChild(questionText)
      mainElement.appendChild(question)
      nextUrl = questionJson.nextURL

      if (questionJson.alternatives == null) {
        const answerInput = document.createElement('input')
        answerInput.classList.add('quiz-answer-input')
        question.appendChild(answerInput)
        answerInput.focus()

        const answerSubmitButton = document.createElement('button')
        answerSubmitButton.classList.add('quiz-answer-submit-button')
        answerSubmitButton.textContent = 'Submit'
        answerSubmitButton.addEventListener('click', () => {
          submitAnswer(answerInput.value)
        })
        question.appendChild(answerSubmitButton)
      } else {
        const alternativesList = document.createElement('div')
        alternativesList.classList.add('quiz-alternatives-list')

        question.appendChild(alternativesList)

        for (let i = 0; i < 4; i++) {
          const currAlternative = document.createElement('button')
          currAlternative.classList.add('quiz-alternative-button')
          currAlternative.value = `alt${i + 1}`
          currAlternative.textContent =
            questionJson.alternatives['alt' + (i + 1)]
          currAlternative.addEventListener('click', () => {
            submitAnswer(currAlternative.value)
          })
          alternativesList.appendChild(currAlternative)
        }
      }
    }

    /**
     * Submits the answer for the current question.
     * @param {string} answer user's answer.
     */
    async function submitAnswer (answer) {
      const fetchRes = await fetch(nextUrl, {
        method: 'POST',
        body: JSON.stringify({ answer }),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const answerJson = await fetchRes.json()

      if (fetchRes.status === 400) {
        question.innerHTML = ''

        const loseMessage = document.createElement('p')
        loseMessage.classList.add('quiz-message')
        loseMessage.textContent = answerJson.message
        const restartButton = document.createElement('button')
        restartButton.classList.add('quiz-start-button')
        restartButton.textContent = 'Restart'
        restartButton.addEventListener('click', startQuiz)
        question.appendChild(loseMessage)
        question.appendChild(restartButton)
      } else {
        if (answerJson.nextURL != null) {
          nextUrl = answerJson.nextURL
          nextQuestion()
        } else {
          clearInterval(timerInterval)
          question.innerHTML = ''
          const winMessage = document.createElement('P')
          winMessage.classList.add('quiz-message')
          winMessage.textContent = `You won in ${seconds}s!`
          const restartButton = document.createElement('button')
          restartButton.classList.add('quiz-start-button')
          restartButton.textContent = 'Restart'
          restartButton.addEventListener('click', startQuiz)
          question.appendChild(winMessage)
          question.appendChild(restartButton)
        }
      }
    }
  }
}
