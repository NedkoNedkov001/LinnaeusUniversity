import { getTopWindow } from './window.js'

const localStorage = window.localStorage
let username = localStorage.getItem('username')

/**
 * Setup the chat application. User will have to enter a username if one is not cached.
 * @param {Element} window window to attach the chat application to.
 */
export function setupChatApp (window) {
  if (username) {
    createChatApp(window)
    return
  }
  window.classList.add('selectable')

  const mainElement = window.querySelector('.window-main')

  document.addEventListener('keydown', handleKeyPress)

  const setupDiv = document.createElement('div')
  setupDiv.classList.add('chat-setup-div')

  const usernameInput = document.createElement('input')
  usernameInput.classList.add('chat-username-input')
  usernameInput.setAttribute('placeholder', 'Username')
  usernameInput.setAttribute('maxlength', 20)
  setupDiv.appendChild(usernameInput)
  mainElement.appendChild(setupDiv)

  /**
   * Handles submitting the username using enter.
   * @param {Event} event keyboard press event.
   */
  function handleKeyPress (event) {
    if (getTopWindow().querySelector('.window-main') !== mainElement) {
      return
    }
    if (event.key === 'Enter') {
      event.preventDefault()

      if (usernameInput.value.trim() !== '') {
        username = usernameInput.value
        localStorage.setItem('username', username)
        mainElement.removeChild(setupDiv)
        createChatApp(window)
      }
    }
  }
}

/**
 * Creates the actual chat app with message input and messages list.
 * @param {Element} window window to attach the chat to.
 */
function createChatApp (window) {
  let currChannel = 'unknown'

  const mainElement = window.querySelector('.window-main')

  document.addEventListener('keydown', handleKeyPress)

  const channelDiv = document.createElement('div')
  channelDiv.classList.add('chat-channel-div')
  mainElement.appendChild(channelDiv)

  const channelInput = document.createElement('input')
  channelInput.classList.add('chat-channel-input')
  channelInput.setAttribute('placeholder', 'channel')
  channelInput.setAttribute('maxlength', 20)
  channelDiv.appendChild(channelInput)

  const channelApplyButton = document.createElement('button')
  channelApplyButton.classList.add('chat-channel-button')
  channelApplyButton.textContent = 'Apply'
  channelApplyButton.addEventListener('click', changeChannel)
  channelDiv.appendChild(channelApplyButton)

  const clearChatButton = document.createElement('button')
  clearChatButton.addEventListener('click', clearChat)
  channelDiv.appendChild(clearChatButton)
  const clearChatIcon = document.createElement('i')
  clearChatIcon.classList.add('fa-regular')
  clearChatIcon.classList.add('fa-trash-can')
  clearChatButton.appendChild(clearChatIcon)

  const messagesListDiv = document.createElement('div')
  messagesListDiv.classList.add('messages-list-div')

  mainElement.appendChild(messagesListDiv)
  const messageList = document.createElement('ul')
  messageList.classList.add('messages-list')

  messagesListDiv.appendChild(messageList)

  const messageTextArea = document.createElement('textarea')
  messageTextArea.classList.add('message-text-area')

  mainElement.appendChild(messageTextArea)

  const websocket = new WebSocket('wss://courselab.lnu.se/message-app/socket')
  websocket.onopen = () => {
    addMessage('Server', 'Welcome to the Chat App!')
  }

  websocket.onmessage = (event) => {
    const messageJson = JSON.parse(event.data)
    if (messageJson.type === 'message' && messageJson.channel === currChannel) {
      addMessage(messageJson.username, messageJson.data)
    }

    messageList.scrollTop = messageList.scrollHeight
  }

  websocket.onclose = () => {
    addMessage('Server', 'The websocket is now closed.')
  }

  /**
   * Handles sending a message using enter.
   * @param {Event} event keyboard key press event.
   */
  function handleKeyPress (event) {
    if (getTopWindow().querySelector('.window-main') !== mainElement) {
      return
    }
    if (event.key === 'Enter') {
      event.preventDefault()
      if (messageTextArea.value.trim() !== '') {
        const message = {
          type: 'message',
          data: messageTextArea.value,
          username,
          channel: currChannel,
          key: 'eDBE76deU7L0H9mEBgxUKVR0VCnq0XBd'
        }
        websocket.send(JSON.stringify(message))
      }

      messageTextArea.value = ''
    }
  }

  /**
   * Adds a message to the message list.
   * @param {string} sender username of the sender.
   * @param {string} message message text.
   */
  function addMessage (sender, message) {
    const messageItem = document.createElement('li')
    messageItem.classList.add('chat-message')

    messageItem.textContent = `${sender}: ${message}`

    messageList.appendChild(messageItem)
  }

  /**
   *
   */
  function changeChannel () {
    clearChat()
    if (channelInput.value.trim() === '') {
      currChannel = 'unknown'
    } else {
      currChannel = channelInput.value.toLowerCase()
    }
    addMessage('Server', `Welcome to channel "${currChannel}"!`)
  }

  /**
   *
   */
  function clearChat () {
    messageList.innerHTML = ''
  }
}
