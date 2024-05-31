import { isFocused } from './main.js'

/**
 * Initiates a new game window
 * @param {Element} applicationWindow window to contain the game
 */
export function initGameWindow (applicationWindow) {
  const gameWindow = document.createElement('div')
  gameWindow.classList.add('application-content')
  gameWindow.classList.add('application-content-game')
  applicationWindow.appendChild(gameWindow)

  const dimensions = document.createElement('div')
  dimensions.classList.add('application-game-dimensions')
  gameWindow.appendChild(dimensions)

  const xInput = document.createElement('input')
  xInput.classList.add('application-game-input-dimension')
  xInput.setAttribute('type', 'number')
  xInput.setAttribute('max', '5')
  xInput.setAttribute('min', '2')
  xInput.setAttribute('value', '2')
  dimensions.appendChild(xInput)

  const xText = document.createElement('p')
  xText.textContent = 'X'
  dimensions.appendChild(xText)

  const yInput = document.createElement('input')
  yInput.classList.add('application-game-input-dimension')
  yInput.setAttribute('type', 'number')
  yInput.setAttribute('max', '4')
  yInput.setAttribute('min', '2')
  yInput.setAttribute('value', '2')
  dimensions.appendChild(yInput)

  const submit = document.createElement('button')
  submit.addEventListener('click', startGame)
  submit.textContent = 'Submit'
  gameWindow.appendChild(submit)

  const errorMessage = document.createElement('p')
  errorMessage.classList.add('error-message')

  /**
   *
   */
  function startGame () {
    const numCards = parseInt(xInput.value) * parseInt(yInput.value)
    let numPairsFound = 0
    if (numCards % 2 !== 0) {
      if (gameWindow.contains(errorMessage)) {
        gameWindow.removeChild(errorMessage)
      }
      errorMessage.textContent = 'Rows X Columns should give an even number!'
      gameWindow.appendChild(errorMessage)

      return
    }

    if (numCards > 16) {
      if (gameWindow.contains(errorMessage)) {
        gameWindow.removeChild(errorMessage)
      }
      errorMessage.textContent = 'No more than 16 cards allowed!'
      gameWindow.appendChild(errorMessage)

      return
    }
    gameWindow.innerHTML = ''

    const cardsSpace = document.createElement('div')
    cardsSpace.classList.add('application-game-cards-space')
    gameWindow.appendChild(cardsSpace)
    cardsSpace.style.gridTemplateColumns = `repeat(${yInput.value}, 1fr)`

    const cardPictureIds = []

    for (let i = 0; i < numCards; i++) {
      const newCard = document.createElement('div')
      newCard.classList.add('application-game-card')
      newCard.classList.add('application-game-card-unknown')
      newCard.setAttribute('value', i)
      newCard.addEventListener('click', turnCard)
      cardsSpace.appendChild(newCard)

      cardPictureIds.push(i % (numCards / 2))
    }
    shuffleArray(cardPictureIds)

    const bottomMenu = document.createElement('div')
    bottomMenu.classList.add('application-game-bottom-menu')
    gameWindow.appendChild(bottomMenu)

    let timer = 0
    const timerText = document.createElement('p')
    timerText.textContent = 'Time: 00:00'
    bottomMenu.appendChild(timerText)

    const timerInterval = setInterval(() => {
      timer++
      const minutes = Math.floor(timer / 60)
        .toString()
        .padStart(2, '0')
      const seconds = (timer % 60).toString().padStart(2, '0')
      const formattedTime = `Time: ${minutes}:${seconds}`
      timerText.textContent = formattedTime
    }, 1000)
    let attemptsNum = 0
    const attemptsNumText = document.createElement('p')
    attemptsNumText.textContent = 'Attempts: 0'
    bottomMenu.appendChild(attemptsNumText)

    document.addEventListener('keydown', handleKeyboardEvent)
    let turnedCard1
    let turnedCard2

    let currCardId = 0
    let currCard

    updateCurrCard()

    /**
     * Handles keyboard event
     * @param {event} event keydown event
     */
    function handleKeyboardEvent (event) {
      if (!isFocused(applicationWindow)) {
        return
      }

      switch (event.key) {
        case 'ArrowLeft':
          moveCard(-1)
          break
        case 'ArrowRight':
          moveCard(1)
          break
        case 'ArrowUp':
          moveCard(-parseInt(xInput.value))
          break
        case 'ArrowDown':
          moveCard(parseInt(xInput.value))
          break
        case 'Enter':
          turnCard(currCard)
          break
      }
    }

    /**
     * Moves between cards
     * @param {number} direction a number to add to the current card ID. Can be negative to move backwards
     */
    function moveCard (direction) {
      if (
        parseInt(currCardId) + direction < 0 ||
        parseInt(currCardId) + direction >= numCards
      ) {
        return
      }

      currCardId = parseInt(currCardId) + direction

      updateCurrCard()
    }

    /**
     *
     */
    function updateCurrCard () {
      if (
        currCard &&
        currCard.classList.contains('application-game-card-selected')
      ) {
        currCard.classList.remove('application-game-card-selected')
      }
      currCard = gameWindow
        .querySelectorAll('.application-game-card')
        .item(currCardId)
      currCard.classList.add('application-game-card-selected')
    }

    /**
     * Ensures the correct turning of a game card
     * @param {Element} card the card to turn
     */
    function turnCard (card) {
      if (this !== undefined) {
        card = this
      }

      if (card.classList.contains('application-game-card-locked')) {
        return
      }

      if (turnedCard1 === card) {
        return
      }

      if (!turnedCard1) {
        increaseAttempts()
        turnedCard1 = card
        revealCard(turnedCard1)
        currCardId = card.getAttribute('value')
      } else if (!turnedCard2) {
        turnedCard2 = card
        revealCard(turnedCard2)
        currCardId = card.getAttribute('value')
        if (cardsMatch()) {
          turnedCard1.classList.add('application-game-card-locked')
          turnedCard2.classList.add('application-game-card-locked')
          turnedCard1 = undefined
          turnedCard2 = undefined
          numPairsFound++
          console.log(numPairsFound)
          if (numPairsFound === numCards / 2) {
            clearInterval(timerInterval)
            finishGame()
          }
        } else {
          setTimeout(() => {
            hideCard(turnedCard1)
            hideCard(turnedCard2)
            turnedCard1 = undefined
            turnedCard2 = undefined
          }, 500)
        }
      }

      updateCurrCard()
    }

    /**
     *
     */
    function finishGame () {
      bottomMenu.classList.add('application-game-bottom-menu-completed')

      const restartButton = document.createElement('button')
      restartButton.addEventListener('click', restartGame)
      restartButton.textContent = 'Start over'
      gameWindow.appendChild(restartButton)
    }

    /**
     *
     */
    function restartGame () {
      startGame()
    }

    /**
     * Reveals the image of a hidden card
     * @param {Element} card card to reveal
     */
    function revealCard (card) {
      card.classList.remove('application-game-card-unknown')
      const cardPictureId = cardPictureIds[card.getAttribute('value')]
      card.classList.add(`application-game-card-${cardPictureId}`)
    }

    /**
     * Hides the image of a revealed card
     * @param {Element} card card to hide
     */
    function hideCard (card) {
      const cardPictureId = cardPictureIds[card.getAttribute('value')]
      card.classList.remove(`application-game-card-${cardPictureId}`)
      card.classList.add('application-game-card-unknown')
    }

    /**
     * @returns {boolean} Returns true if turned cards match, otherwise false
     */
    function cardsMatch () {
      return (
        cardPictureIds[turnedCard1.getAttribute('value')] ===
        cardPictureIds[turnedCard2.getAttribute('value')]
      )
    }

    /**
     *
     */
    function increaseAttempts () {
      attemptsNumText.textContent = `Attempts: ${++attemptsNum}`
    }
  }
}

/**
 * Shuffles an array
 * @param {Array} array array to shuffle
 */
function shuffleArray (array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]]
  }
}
