import {
  getDocumentElement,
  getJsonFromUrl,
  getNameWithValueList
} from './utils.js'

/**
 * Gets a list of movie times.
 * @param {string} cinemaUrl Url to cinema page.
 * @param {Array} availableDays List of available days as booleans.
 * @returns {Array} Two JSON lists containing information about days and movies.
 */
export async function getListOfMoviesAndDays (cinemaUrl, availableDays) {
  const cinemaPageDoc = await getDocumentElement(cinemaUrl)
  const daysInfo = getNameWithValueList(cinemaPageDoc, '#day > option')
  const moviesInfo = getNameWithValueList(cinemaPageDoc, '#movie > option')
  const days = []
  const movies = []

  for (let i = 0; i < daysInfo.length; i++) {
    const day = daysInfo[i]
    days.push({ name: day[0], id: day[1] })
  }
  for (let i = 0; i < moviesInfo.length; i++) {
    const movie = moviesInfo[i]
    movies.push({ name: movie[0], id: movie[1] })
  }
  return [days, movies]
}

/**
 * Gets a list of suitable screenings.
 * @param {string} cinemaUrl Cinema url
 * @param {Array} days JSON list of days information.
 * @param {Array} movies JSON list of movies information.
 * @param {Array} availableDays Bool list of days availability statuses.
 * @returns {Array} JSON list of suitable screenings information.
 */
export async function getListOfSuitableScreenings (
  cinemaUrl,
  days,
  movies,
  availableDays
) {
  const availableScreenings = []
  for (let i = 0; i < availableDays.length; i++) {
    const day = availableDays[i]
    if (!day) {
      continue
    }
    for (let j = 0; j < movies.length; j++) {
      const url = cinemaUrl + `/check?day=${days[i].id}&movie=${movies[j].id}`

      const availableScreeningsDay = await getAvailableMoviesForDay(
        url,
        days[i],
        movies[j]
      )

      if (availableScreeningsDay.length === 0) {
        continue
      }
      availableScreeningsDay.forEach((screening) => {
        availableScreenings.push(screening)
      })
    }
  }

  return availableScreenings
}

/**
 * Gets available movies for a given day.
 * @param {string} url url containing movie and day IDs.
 * @param {string} daysInfo day as string and ID.
 * @param {string} moviesInfo movie name and ID.
 * @returns {Array} JSON list containing information about movies.
 */
async function getAvailableMoviesForDay (url, daysInfo, moviesInfo) {
  const availableScreenings = []
  const movieDayJson = await getJsonFromUrl(url)
  for (let i = 0; i < movieDayJson.length; i++) {
    const movieScheduleItem = movieDayJson[i]
    if (movieScheduleItem.status === 0) {
      continue
    }
    availableScreenings.push({
      day: daysInfo.name,
      movie: moviesInfo.name,
      time: movieScheduleItem.time
    })
  }
  return availableScreenings
}
