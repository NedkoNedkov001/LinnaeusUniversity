import { initChatWindow } from './chat.js'
import { initGameWindow } from './game.js'
import { initWeatherWindow } from './weather.js'

const desktop = document.querySelector('.desktop')

const applicationGame = document.querySelector('.application-icon-game')
applicationGame.addEventListener('click', openApplicationGame)

const applicationChat = document.querySelector('.application-icon-chat')
applicationChat.addEventListener('click', openApplicationChat)

const applicationWeather = document.querySelector('.application-icon-weather')
applicationWeather.addEventListener('click', openApplicationWeather)

let focusedWindow

/**
 *
 */
function openApplicationGame () {
  const gameWindow = createWindow('Memory Game')
  initGameWindow(gameWindow)
}

/**
 *
 */
function openApplicationChat () {
  const chatWindow = createWindow('Chat App')
  initChatWindow(chatWindow)
}

/**
 *
 */
function openApplicationWeather () {
  const weatherWindow = createWindow('Weather App')
  initWeatherWindow(weatherWindow)
}

/**
 * Creates a new window element with a given name
 * @param {string} name name of the window
 * @returns {Element} the new window
 */
function createWindow (name) {
  const newWindow = document.createElement('div')
  newWindow.classList.add('application-window')
  newWindow.setAttribute('draggable', 'true')
  desktop.appendChild(newWindow)

  const newWindowHeader = document.createElement('div')
  newWindowHeader.classList.add('application-window-header')
  newWindow.appendChild(newWindowHeader)

  const newWindowHeaderClose = document.createElement('div')
  newWindowHeaderClose.classList.add('application-window-header-close')
  newWindowHeaderClose.addEventListener('click', () => {
    closeWindow(newWindow)
  })
  newWindowHeader.appendChild(newWindowHeaderClose)

  const newWindowHeaderTitle = document.createElement('p')
  newWindowHeaderTitle.classList.add('application-window-header-title')
  newWindowHeaderTitle.textContent = name
  newWindowHeader.appendChild(newWindowHeaderTitle)

  newWindow.addEventListener('click', () => {
    focusWindow(newWindow)
  })
  newWindow.addEventListener('dragstart', (event) => {
    const style = window.getComputedStyle(event.target, null)
    const startX = parseInt(style.getPropertyValue('left'), 10) - event.clientX
    const startY = parseInt(style.getPropertyValue('top'), 10) - event.clientY
    const start = {
      posX: startX,
      posY: startY
    }

    event.dataTransfer.setData('application/json', JSON.stringify(start))
    focusWindow(newWindow)
  })

  desktop.addEventListener('drop', (event) => {
    if (focusedWindow === newWindow) {
      const start = JSON.parse(event.dataTransfer.getData('application/json'))
      const dropX = event.clientX
      const dropY = event.clientY

      newWindow.style.left = dropX + start.posX + 'px'
      newWindow.style.top = dropY + start.posY + 'px'
    }
  })

  desktop.addEventListener('dragover', (event) => {
    event.preventDefault()
  })

  focusWindow(newWindow)
  return newWindow
}

/**
 * Changes focused window
 * @param {Element} window the window to focus on
 */
function focusWindow (window) {
  if (focusedWindow) {
    focusedWindow.style.zIndex = 1
  }
  focusedWindow = window
  focusedWindow.style.zIndex = 2
}

/**
 * Closes a given window
 * @param {Element} window window to close
 */
function closeWindow (window) {
  desktop.removeChild(window)
}

/**
 * Checks whether a window is focused.
 * @param {Element} applicationWindow window to check
 * @returns {boolean} true if the window is focused, otherwise false
 */
export function isFocused (applicationWindow) {
  return applicationWindow === focusedWindow
}
