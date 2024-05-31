import { getTopWindow } from './window.js'

const colors = [
  'rgb(0, 0, 0)',
  'rgb(255, 0, 0)',
  'rgb(0, 255, 0)',
  'rgb(0, 0, 255)',
  'rgb(0, 255, 255)',
  'rgb(255, 0, 255)',
  'rgb(255, 255, 0)',
  'rgb(255, 255, 255)',
  'rgb(100, 0, 0)',
  'rgb(0, 100, 0)',
  'rgb(0, 0, 177)',
  'rgb(0, 177, 177)',
  'rgb(177, 0, 177)',
  'rgb(177, 177, 0)',
  'rgb(177, 177, 177)'
]

/**
 * Create the game in a div.
 * @param {Element} window window to attach the game to.
 */
export function createMemoryGame (window) {
  const mainElement = window.querySelector('.window-main')

  const memoryGameSetup = document.createElement('div')
  memoryGameSetup.classList.add('memory-game-setup')

  const rowsInput = document.createElement('input')
  rowsInput.classList.add('game-input')
  rowsInput.setAttribute('value', '2')
  rowsInput.setAttribute('min', '2')
  rowsInput.setAttribute('max', '5')
  rowsInput.setAttribute('type', 'number')

  memoryGameSetup.appendChild(rowsInput)

  const separator = document.createElement('p')
  separator.innerText = 'X'

  memoryGameSetup.appendChild(separator)

  const colsInput = document.createElement('input')
  colsInput.classList.add('game-input')
  colsInput.setAttribute('value', '2')
  colsInput.setAttribute('min', '2')
  colsInput.setAttribute('max', '5')
  colsInput.setAttribute('type', 'number')

  memoryGameSetup.appendChild(colsInput)

  const submitBtn = document.createElement('button')
  submitBtn.classList.add('game-button')
  submitBtn.innerText = 'Start'
  submitBtn.addEventListener('click', () => {
    if (colsInput.value > 5 || rowsInput.value > 5) {
      const warning = document.createElement('p')
      warning.classList.add('game-warning')
      warning.textContent = 'Maximum of 5 rows or columns allowed!'
      if (!mainElement.querySelector('.game-warning')) {
        mainElement.appendChild(warning)
      }
      return
    }
    if ((colsInput.value * rowsInput.value) % 2 === 1) {
      const warning = document.createElement('p')
      warning.classList.add('game-warning')
      warning.textContent = 'Either rows or columns has to be even!'
      if (mainElement.querySelector('.game-warning')) {
        mainElement.removeChild(mainElement.querySelector('.game-warning'))
      }
      mainElement.appendChild(warning)

      return
    }
    mainElement.innerHTML = ''
    startMemoryGame(mainElement, [rowsInput.value, colsInput.value])
  })

  memoryGameSetup.appendChild(submitBtn)

  mainElement.appendChild(memoryGameSetup)
}

/**
 *
 * @param {Element} mainElement main div to play the game in.
 * @param {Array} size an array[rows, cols]
 */
function startMemoryGame (mainElement, size) {
  const rows = parseInt(size[0])
  const cols = parseInt(size[1])

  const memoryGameContainer = document.createElement('div')
  memoryGameContainer.classList.add('memory-game')
  memoryGameContainer.style.gridTemplateColumns = `repeat(${cols}, 1fr)`

  for (let i = 0; i < rows * cols; i++) {
    const card = createCard()
    card.setAttribute('value', i)
    card.addEventListener('click', turnCard)
    memoryGameContainer.appendChild(card)
  }

  const memoryGameFooter = document.createElement('div')
  memoryGameFooter.classList.add('memory-game-footer')

  const timerElement = document.createElement('p')
  timerElement.classList.add('memory-game-timer')
  timerElement.textContent = '0:00'

  const attemptsElement = document.createElement('p')
  attemptsElement.classList.add('memory-game-attempts')
  attemptsElement.textContent = 'Attempts: 0'

  const restartButton = document.createElement('button')
  restartButton.classList.add('game-button')
  restartButton.textContent = 'Restart'

  restartButton.addEventListener('click', restartGame)

  memoryGameFooter.appendChild(timerElement)
  memoryGameFooter.appendChild(attemptsElement)
  memoryGameFooter.appendChild(restartButton)

  mainElement.appendChild(memoryGameContainer)
  mainElement.appendChild(memoryGameFooter)
  document.addEventListener('keydown', handleKeyPress)

  const cardColors = []
  for (let i = 0; i < (rows * cols) / 2; i++) {
    cardColors.push(i)
    cardColors.push(i)
  }

  shuffleArray(colors)
  shuffleArray(cardColors)

  let selectedCard1
  let selectedCard2
  let attempts = 0
  let seconds = 0
  let selectedCardNum = 0
  let selectedCard = mainElement.querySelector('.card')
  let foundPairs = 0

  updateUI()

  const timerInterval = setInterval(() => {
    seconds++
    const minutes = Math.floor(seconds / 60)
    const remainingSeconds = seconds % 60
    const formattedTime = `${minutes < 10 ? '0' : ''}${minutes}:${
      remainingSeconds < 10 ? '0' : ''
    }${remainingSeconds}`

    timerElement.textContent = formattedTime
  }, 1000)

  /**
   * Turns the card to reveal the color.
   * @param {Element} card card to turn.
   */
  function turnCard (card) {
    if (this !== undefined) {
      card = this
    }

    if (card.classList.contains('card-done')) {
      return
    }

    if (selectedCard1 === card) { // If second selected card is the same as the first selected, do nothing
      return
    }
    if (selectedCard1 === undefined) { // If no selected cards, select the first card
      attemptsElement.textContent = 'Attempts: ' + ++attempts
      selectedCard1 = card
      selectedCard1.style.backgroundColor =
        colors[cardColors[selectedCard1.getAttribute('value')]]
      selectedCard1.classList.remove('card-hidden')
    } else if (selectedCard2 === undefined) { // If only one selected card, select the second
      selectedCard2 = card
      selectedCard2.style.backgroundColor =
        colors[cardColors[selectedCard2.getAttribute('value')]]
      selectedCard2.classList.remove('card-hidden')
      if (
        colors[cardColors[selectedCard1.getAttribute('value')]] ===
        colors[cardColors[selectedCard2.getAttribute('value')]]
      ) {
        selectedCard1.removeEventListener('click', turnCard)
        selectedCard2.removeEventListener('click', turnCard)
        foundPairs++
        if (foundPairs === (rows * cols) / 2) {
          clearInterval(timerInterval)
        }
        selectedCard1.classList.add('card-done')
        selectedCard2.classList.add('card-done')
        selectedCard1 = undefined
        selectedCard2 = undefined
      } else {
        setTimeout(function () {
          // selectedCard1.style.backgroundColor = ''
          selectedCard1.classList.add('card-hidden')
          // selectedCard1.style.backgroundColor = ''
          selectedCard2.classList.add('card-hidden')
          selectedCard1 = undefined
          selectedCard2 = undefined
        }, 500)
      }
    }

    selectedCardNum = card.getAttribute('value')
    updateUI()
  }

  /**
   *
   */
  function restartGame () {
    mainElement.innerHTML = ''
    selectedCard.classList.remove('card-selected')
    selectedCard = undefined
    startMemoryGame(mainElement, size)
  }

  /**
   * Handles playing using the keyboard.
   * @param {Event} event keyboard press.
   */
  function handleKeyPress (event) {
    if (getTopWindow().querySelector('.window-main') !== mainElement) {
      return
    }
    switch (event.key) {
      case 'ArrowLeft':
        if (selectedCardNum % cols === 0) {
          selectedCardNum = parseInt(selectedCardNum) + cols - 1
        } else {
          selectedCardNum -= 1
        }
        break
      case 'ArrowRight':
        if ((parseInt(selectedCardNum) + 1) % cols === 0) {
          selectedCardNum -= cols - 1
        } else {
          selectedCardNum = parseInt(selectedCardNum) + 1
        }
        break
      case 'ArrowUp':
        if (selectedCardNum < cols) {
          selectedCardNum = parseInt(selectedCardNum) + rows * cols - cols
        } else {
          selectedCardNum -= cols
        }
        break
      case 'ArrowDown':
        selectedCardNum = (parseInt(selectedCardNum) + cols) % (rows * cols)

        break
      case 'Enter':
        turnCard(selectedCard, cardColors)
        break
    }
    updateUI()
  }

  /**
   *
   */
  function updateUI () {
    selectedCard.classList.remove('card-selected')
    selectedCard = mainElement.querySelectorAll('.card').item(selectedCardNum)
    selectedCard.classList.add('card-selected')
  }
}

/**
 * Creates a new card.
 * @returns {Element} new card element.
 */
function createCard () {
  const newCard = document.createElement('div')
  newCard.classList.add('card')
  newCard.classList.add('card-hidden')
  return newCard
}

/**
 * Shuffles a given array.
 * @param {Array} array array to shuffle.
 */
function shuffleArray (array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]]
  }
}
