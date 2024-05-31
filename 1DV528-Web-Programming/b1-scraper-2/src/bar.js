import {
  getDocFromUrl,
  getDocFromUrlWithCookie,
  postToUrl
} from './htmlElements.js'

/**
 * Gets a list of suitable reservations
 * @param {string} barUrl bar URL
 * @param {Array} suitableMovies a list of suitable movies
 * @returns {Array} a list of suitable reservations
 */
export async function getSuitableReservations (barUrl, suitableMovies) {
  const reservations = []
  const barDoc = await getDocFromUrl(barUrl)

  const barLoginUrl =
    barUrl + barDoc.querySelector('body > div > form').getAttribute('action')

  const barLoginData = new URLSearchParams({
    username: 'zeke',
    password: 'coys'
  })

  const barLoginResponse = await postToUrl(barLoginUrl, barLoginData)

  const barReservationUrl = barUrl + barLoginResponse.headers.get('location')
  const barReservationCookie = barLoginResponse.headers.get('set-cookie')

  const reservationDoc = await getDocFromUrlWithCookie(
    barReservationUrl,
    barReservationCookie
  )

  const availableReservations = reservationDoc.querySelectorAll(
    'body > form > div > p > input[type=radio]'
  )

  for (let i = 0; i < availableReservations.length; i++) {
    const reservation = availableReservations[i]
    const reservationValue = reservation.getAttribute('value')
    const reservationDay = reservationValue.substring(0, 3)
    const reservationStart = reservationValue.substring(3, 5)
    const reservationEnd = reservationValue.substring(5, 7)
    for (let j = 0; j < suitableMovies.length; j++) {
      const movie = suitableMovies[j]
      if (
        movie.day.substring(0, 3).toLowerCase() === reservationDay &&
        parseInt(movie.time.substring(0, 2)) + 2 <=
          parseInt(reservationStart)
      ) {
        reservations.push({
          day: movie.day,
          movie: movie.name,
          movieTime: movie.time,
          reservationStart: reservationStart + ':00',
          reservationEnd: reservationEnd + ':00'
        })
      }
    }
  }
  return reservations
}
