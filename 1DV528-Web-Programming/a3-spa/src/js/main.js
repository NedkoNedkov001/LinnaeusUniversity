import { createWindow } from './window.js'
import { createMemoryGame } from './game.js'
import { setupChatApp } from './chat.js'
import { setupQuizApp } from './quiz.js'

const memoryGameIcon = document.getElementById('memory-game-launcher')
memoryGameIcon.addEventListener('click', openMemoryGame)

const chatAppIcon = document.getElementById('chat-app-launcher')
chatAppIcon.addEventListener('click', openChatApp)

const quizAppIcon = document.getElementById('quiz-app-launcher')
quizAppIcon.addEventListener('click', openQuizApp)

/**
 *
 */
function openMemoryGame () {
  const newWindow = createWindow(
    'Memory Game'
  )

  createMemoryGame(newWindow)

  document.querySelector('body').appendChild(newWindow)
}

/**
 *
 */
function openChatApp () {
  const newWindow = createWindow(
    'Chat App'
  )

  setupChatApp(newWindow)

  document.querySelector('body').appendChild(newWindow)
}

/**
 *
 */
function openQuizApp () {
  const newWindow = createWindow(
    'Quiz App'
  )

  setupQuizApp(newWindow)

  document.querySelector('body').appendChild(newWindow)
}
