import { getListOfAvailableBarReservations } from './bar.js'
import { getListOfAvailableDays } from './calendar.js'
import {
  getListOfMoviesAndDays,
  getListOfSuitableScreenings
} from './cinema.js'

import { getDocumentElement } from './utils.js'

/**
 *
 */
async function getFriendsSchedule () {
  const homePageDoc = await getDocumentElement(homeUrl)

  const calendarsUrl = homePageDoc
    .querySelector('body > ol > li:nth-child(1) > a')
    .getAttribute('href')
  const availableDays = await getListOfAvailableDays(calendarsUrl)
  if (!availableDays.includes(true)) {
    console.log('The friends can\'t hang out this weekend.')
    return
  }

  const cinemaUrl = homePageDoc
    .querySelector('body > ol > li:nth-child(2) > a')
    .getAttribute('href')
  const [days, movies] = await getListOfMoviesAndDays(cinemaUrl)
  const suitableScreenings = await getListOfSuitableScreenings(
    cinemaUrl,
    days,
    movies,
    availableDays
  )

  const barUrl = homePageDoc
    .querySelector('body > ol > li:nth-child(3) > a')
    .getAttribute('href')
  const availableBarTimes = await getListOfAvailableBarReservations(barUrl)

  const suitableSchedules = getSuitableSchedules(
    availableBarTimes,
    suitableScreenings
  )

  suitableSchedules.forEach((schedule) => {
    console.log(`On ${schedule.day} the movie "${schedule.movie}" starts at ${schedule.movieStart} and there is a free table between ${schedule.reservationStart}:00-${schedule.reservationEnd}:00.
    `)
  })
}

/**
 * Gets a list of suitable schedules for the weekend.
 * @param {Array} availableBarTimes  a list of free reservation times.
 * @param {Array} suitableScreenings a list of suitable movie screenings.
 * @returns {Array} a list of possible schedule combinations.
 */
function getSuitableSchedules (availableBarTimes, suitableScreenings) {
  const schedules = []
  for (let i = 0; i < suitableScreenings.length; i++) {
    const screening = suitableScreenings[i]
    const screeningEndTime = parseInt(screening.time) + 2
    const screeningDayShort = screening.day.substring(0, 3).toLowerCase()
    for (let j = 0; j < availableBarTimes.length; j++) {
      const reservation = availableBarTimes[j]
      const reservationStartTime = reservation.start
      if (
        screeningDayShort === reservation.day &&
        reservationStartTime >= screeningEndTime
      ) {
        schedules.push({
          day: screening.day,
          movie: screening.movie,
          movieStart: screening.time,
          reservationStart: reservation.start,
          reservationEnd: reservation.end
        })
      }
    }
  }
  return schedules
}

const homeUrl = process.argv[2]
getFriendsSchedule(homeUrl)

// https://courselab.lnu.se/scraper-site-1
