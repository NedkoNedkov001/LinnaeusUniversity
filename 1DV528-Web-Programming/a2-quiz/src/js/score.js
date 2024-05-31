const localStorage = window.localStorage

let storedScores = JSON.parse(localStorage.getItem('topScores'))
const scoreboardItems = document.querySelectorAll('.scoreboard-item')

/**
 *
 * @param {string} username name of user whose score is being added
 * @param {number} currTime current time being added
 */
export function addScore (username, currTime) {
  if (storedScores == null) {
    storedScores = [{ username, time: currTime }]
  } else {
    for (let i = 0; i < 5; i++) {
      if (storedScores[i] == null) {
        storedScores[i] = { username, time: currTime }
        break
      } else if (storedScores[i].time > currTime) {
        const temp = storedScores[i]
        storedScores[i] = { username, time: currTime }
        username = temp.username
        currTime = temp.time
      }
    }
  }
  localStorage.setItem('topScores', JSON.stringify(storedScores))
}
/**
 * Updates the scoreboard to show the top5 of the saved results.
 */
export function updateScores () {
  storedScores = JSON.parse(localStorage.getItem('topScores'))

  for (let i = 0; i < 5; i++) {
    if (storedScores[i] != null) {
      scoreboardItems[
        i
      ].textContent = `${storedScores[i].username} - ${storedScores[i].time}`
    }
  }
}
