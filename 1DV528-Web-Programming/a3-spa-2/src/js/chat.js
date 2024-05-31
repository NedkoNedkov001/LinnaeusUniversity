import { isFocused } from './main.js'

const localStorage = window.localStorage
let username = localStorage.getItem('username')

/**
 * Initiates a new chat window
 * @param {Element} applicationWindow window to contain the chat
 */
export function initChatWindow (applicationWindow) {
  if (!username) {
    createUsernameWindow(applicationWindow)
  } else {
    createChatWindow(applicationWindow)
  }
}

/**
 * Creates the window where user enters username
 * @param {Element} applicationWindow window to contain the createUsernameWindow
 */
function createUsernameWindow (applicationWindow) {
  const chatWindow = document.createElement('div')
  chatWindow.classList.add('application-content')
  chatWindow.classList.add('application-content-chat')
  applicationWindow.appendChild(chatWindow)

  const usernameInput = document.createElement('input')
  usernameInput.classList.add('application-chat-username-input')
  usernameInput.setAttribute('placeholder', 'username')
  usernameInput.setAttribute('maxlength', 15)
  chatWindow.appendChild(usernameInput)

  const usernameSubmit = document.createElement('button')
  usernameSubmit.addEventListener('click', submitUsername)
  usernameSubmit.textContent = 'Submit'
  chatWindow.appendChild(usernameSubmit)

  /**
   *
   */
  function submitUsername () {
    username = usernameInput.value
    localStorage.setItem('username', username)
    chatWindow.removeChild(usernameInput)
    chatWindow.removeChild(usernameSubmit)

    createChatWindow(applicationWindow)
  }
}

/**
 * Creates the actual chat window
 * @param {Element} applicationWindow window to contain the chat window
 */
function createChatWindow (applicationWindow) {
  const chatWindow = document.createElement('div')
  chatWindow.classList.add('application-content')
  chatWindow.classList.add('application-content-chat')
  applicationWindow.appendChild(chatWindow)

  document.addEventListener('keydown', handleKeyPress)

  const messages = document.createElement('div')
  messages.classList.add('application-chat-messages')
  chatWindow.appendChild(messages)

  const userArea = document.createElement('div')
  userArea.classList.add('application-chat-user-area')
  chatWindow.appendChild(userArea)

  const textArea = document.createElement('textarea')
  textArea.classList.add('application-chat-text-area')

  userArea.appendChild(textArea)

  const buttonsArea = document.createElement('div')
  buttonsArea.classList.add('application-chat-buttons-area')
  userArea.appendChild(buttonsArea)

  const emojiButton = document.createElement('button')
  emojiButton.textContent = 'Emojis'
  emojiButton.addEventListener('click', openEmojiMenu)
  buttonsArea.appendChild(emojiButton)

  const submitButton = document.createElement('button')
  submitButton.textContent = 'Submit'
  submitButton.addEventListener('click', sendMessage)
  buttonsArea.appendChild(submitButton)

  const websocket = new WebSocket('wss://courselab.lnu.se/message-app/socket')

  websocket.onmessage = (event) => {
    const messageJson = JSON.parse(event.data)

    if (messageJson.type === 'message') {
      const sender = messageJson.username
      const message = messageJson.data
      receiveMessage(sender, message)
    }
  }

  let emojisMenuOpen = false
  const emojiMenu = getEmojiMenu()

  /**
   * Sends the message in the current focused window when user presses Enter
   * @param {Event} event a key press by the user
   */
  function handleKeyPress (event) {
    if (event.key === 'Enter') {
      if (!isFocused(applicationWindow)) {
        return
      }

      event.preventDefault()

      sendMessage()
    }
  }

  /**
   *
   */
  function sendMessage () {
    if (textArea.value.trim() === '') {
      return
    }
    const messageText = textArea.value
    textArea.value = ''
    const message = {
      type: 'message',
      data: messageText,
      username,
      channel: 'unknown',
      key: 'eDBE76deU7L0H9mEBgxUKVR0VCnq0XBd'
    }
    websocket.send(JSON.stringify(message))
  }

  /**
   * Visualizes a received message
   * @param {string} sender username of the sender
   * @param {string} message message data
   */
  function receiveMessage (sender, message) {
    const currTime = new Date()
    const hours = currTime.getHours().toString().padStart(2, '0')
    const minutes = currTime.getMinutes().toString().padStart(2, '0')
    const seconds = currTime.getSeconds().toString().padStart(2, '0')
    const formattedTime = `${hours}:${minutes}:${seconds}`

    const newMessage = document.createElement('p')
    newMessage.classList.add('application-chat-message')
    newMessage.textContent = `[${formattedTime}] ${sender}: ${message}`
    messages.appendChild(newMessage)
  }

  /**
   *
   */
  function openEmojiMenu () {
    if (emojisMenuOpen) {
      userArea.removeChild(emojiMenu)
      emojisMenuOpen = false
    } else {
      emojisMenuOpen = true
      userArea.appendChild(emojiMenu)
    }
  }

  /**
   * @returns {Element} emoji menu
   */
  function getEmojiMenu () {
    const emojiMenu = document.createElement('div')
    emojiMenu.classList.add('application-chat-emoji-menu')

    const emojiList = [
      '\u{1F642}',
      '\u{1F600}',
      '\u{1F601}',
      '\u{1F923}',
      '\u{1F61B}',
      '\u{1F610}',
      '\u{1F614}',
      '\u{1F635}',
      '\u{1F641}',
      '\u{1F62E}',
      '\u{1F627}',
      '\u{1F62D}',
      '\u{1F620}',
      '\u{1F621}'
    ]

    emojiList.forEach((emoji) => {
      addEmoji(emoji)
    })

    /**
     * Adds an emoji to emoji menu
     * @param {string} emojiCode UTF-8 code of emoji to add
     */
    function addEmoji (emojiCode) {
      const emoji = document.createElement('div')
      emoji.addEventListener('click', () => {
        addEmojiToText(emojiCode)
      })
      emoji.classList.add('application-chat-emoji')
      emoji.textContent = emojiCode
      emojiMenu.appendChild(emoji)
    }

    /**
     * Adds clicked emoji to chat
     * @param {string} emojiCode UTF-8 code of emoji to add
     */
    function addEmojiToText (emojiCode) {
      textArea.value = `${textArea.value}${emojiCode}`
    }
    return emojiMenu
  }
}
