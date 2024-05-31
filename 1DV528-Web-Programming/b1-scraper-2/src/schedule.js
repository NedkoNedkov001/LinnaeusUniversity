import { getSuitableReservations } from './bar.js'
import { getSuitableDays } from './calendar.js'
import { getDocFromUrl } from './htmlElements.js'
import { getSuitableMovies } from './movies.js'

/**
 * Makes and prints schedule from a given start URL
 * @param {string} startUrl URL of start page
 */
async function makeSchedule (startUrl) {
  const startDoc = await getDocFromUrl(startUrl)

  const calendarUrl = startDoc
    .querySelector('body > ol > li:nth-child(1) > a')
    .getAttribute('href')
  const cinemaUrl = startDoc
    .querySelector('body > ol > li:nth-child(2) > a')
    .getAttribute('href')
  const barUrl = startDoc
    .querySelector('body > ol > li:nth-child(3) > a')
    .getAttribute('href')

  const suitableDays = await getSuitableDays(calendarUrl)
  if (!suitableDays.includes(true)) {
    console.log('No suitable days.')
    return
  }

  const suitableMovies = await getSuitableMovies(cinemaUrl, suitableDays)

  const suitableReservations = await getSuitableReservations(barUrl, suitableMovies)
  suitableReservations.forEach(reservation => {
    console.log(`On ${reservation.day} the movie "${reservation.movie}" starts at ${reservation.movieTime} and there is a free table between ${reservation.reservationStart}-${reservation.reservationEnd}.`)
  })
}

const startUrl = process.argv[2]
makeSchedule(startUrl)
