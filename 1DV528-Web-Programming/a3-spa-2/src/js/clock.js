const clockTime = document.querySelector('.desktop-clock-text')

setInterval(() => {
  const currDate = new Date()
  const hours = currDate.getHours().toString().padStart(2, '0')
  const minutes = currDate.getMinutes().toString().padStart(2, '0')
  const formattedTime = `${hours}:${minutes}`
  clockTime.textContent = formattedTime
}, 1000)
