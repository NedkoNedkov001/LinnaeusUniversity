let currTop

const dropZone = document.querySelector('body')

// dropZone.addEventListener("dragover", (event) => {
//   event.preventDefault();
// });

/**
 *
 * @param {string} name name for the window.
 * @returns {Element} the new window.
 */
export function createWindow (name) {
  const newWindow = document.createElement('div')
  newWindow.setAttribute('draggable', true)
  newWindow.classList.add('window')

  const header = document.createElement('header')
  header.classList.add('window-header')

  const headerInfo = document.createElement('div')
  headerInfo.classList.add('window-header-info')

  const headerInfoImage = document.createElement('div')
  headerInfoImage.classList.add('window-thumbnail')
  switch (name) {
    case 'Memory Game':
      headerInfoImage.classList.add('window-header-info-game')
      break
    case 'Chat App':
      headerInfoImage.classList.add('window-header-info-chat')
      break
    case 'Quiz App':
      headerInfoImage.classList.add('window-header-info-quiz')
      break

    default:
      break
  }

  const headerInfoName = document.createElement('p')
  headerInfoName.innerText = name
  headerInfoName.classList.add = 'window-name'

  headerInfo.appendChild(headerInfoImage)
  headerInfo.appendChild(headerInfoName)

  const closeBtn = document.createElement('i')
  closeBtn.classList.add('fa-solid')
  closeBtn.classList.add('fa-xmark')
  closeBtn.classList.add('window-close-btn')

  closeBtn.addEventListener('click', () => {
    dropZone.removeChild(newWindow)
  })

  header.appendChild(headerInfo)
  header.appendChild(closeBtn)

  newWindow.appendChild(header)

  const windowMain = document.createElement('main')
  windowMain.classList.add('window-main')

  newWindow.appendChild(windowMain)

  makeDraggable(newWindow)
  focusWindow(newWindow)

  newWindow.addEventListener('click', () => {
    focusWindow(newWindow)
  })
  return newWindow
}

/**
 * Make a window draggable.
 * @param {unknown} newWindow window to make draggable.
 */
function makeDraggable (newWindow) {
  let isBeingDragged = false

  newWindow.addEventListener('mousedown', (event) => {
    isBeingDragged = true
    const offsetX = event.clientX - newWindow.getBoundingClientRect().left
    const offsetY = event.clientY - newWindow.getBoundingClientRect().top

    /**
     * Move window when dragging it.
     * @param {event} moveEvent dragging event.
     */
    function handleDragMove (moveEvent) {
      if (isBeingDragged) {
        newWindow.style.left = moveEvent.clientX - offsetX + 'px'
        newWindow.style.top = moveEvent.clientY - offsetY + 'px'
        focusWindow(newWindow)
      }
    }

    /**
     *
     */
    function handleDragEnd () {
      isBeingDragged = false
      document.removeEventListener('mousemove', handleDragMove)
      document.removeEventListener('mouseup', handleDragEnd)
    }

    document.addEventListener('mousemove', handleDragMove)
    document.addEventListener('mouseup', handleDragEnd)
  })

  // newWindow.addEventListener("dragstart", (event) => {
  //   currDrag = newWindow
  //   event.dataTransfer.dropEffect = "move";

  //   const style = window.getComputedStyle(event.target, null);
  //   const startX = parseInt(style.getPropertyValue("left"), 10) - event.clientX;
  //   const startY = parseInt(style.getPropertyValue("top"), 10) - event.clientY;
  //   const start = {
  //     posX: startX,
  //     posY: startY,
  //   };

  //   event.dataTransfer.setData("application/json", JSON.stringify(start));
  // });

  // dropZone.addEventListener("drop", (event) => {
  //   const start = JSON.parse(event.dataTransfer.getData("application/json"));

  //   const dropX = event.clientX;
  //   const dropY = event.clientY;

  //   currDrag.style.left = dropX + start.posX + "px";
  //   currDrag.style.top = dropY + start.posY + "px";
  //   focusWindow(currDrag)
  // });
}

/**
 *
 * @param {Element} window the window to focus
 */
function focusWindow (window) {
  if (currTop !== undefined) {
    currTop.style.zIndex = 1
  }

  currTop = window
  currTop.style.zIndex = 2
}

/**
 * Get the top window.
 * @returns {Element} the current top(focused) window.
 */
export function getTopWindow () {
  return currTop
}
