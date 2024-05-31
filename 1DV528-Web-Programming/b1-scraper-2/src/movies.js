import { getDocFromUrl, getJsonFromUrl } from './htmlElements.js'

/**
 * Gets a list of suitable movies in JSON format
 * @param {string} cinemaUrl cinema URL
 * @param {Array} suitableDays a list of suitable days
 * @returns {Array} a list of suitable movies
 */
export async function getSuitableMovies (cinemaUrl, suitableDays) {
  const suitableMovies = []

  const cinemaDoc = await getDocFromUrl(cinemaUrl)

  const dayOptions = cinemaDoc.querySelectorAll('#day > option')
  const movieOptions = cinemaDoc.querySelectorAll('#movie > option')

  for (let i = 1; i < dayOptions.length; i++) {
    if (!suitableDays[i - 1]) {
      continue
    }
    const dayOption = dayOptions[i]
    const dayName = dayOption.textContent
    const dayValue = dayOption.getAttribute('value')
    for (let j = 1; j < movieOptions.length; j++) {
      const movieOption = movieOptions[j]
      const movieName = movieOption.textContent
      const movieValue = movieOption.getAttribute('value')
      const movieUrl = cinemaUrl + `/check?day=${dayValue}&movie=${movieValue}`
      const movieJson = await getJsonFromUrl(movieUrl)
      for (let k = 0; k < movieJson.length; k++) {
        const movieInfo = movieJson[k]
        if (movieInfo.status === 1) {
          suitableMovies.push({ day: dayName, name: movieName, time: movieInfo.time })
        }
      }
    }
  }
  return suitableMovies
}
