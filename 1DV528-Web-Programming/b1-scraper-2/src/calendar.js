import { getDocFromUrl } from './htmlElements.js'

/**
 * Gets a list of suitable days
 * @param {string} calendarUrl calendar URL
 * @returns {Array} a list of suitable days using booleans
 */
export async function getSuitableDays (calendarUrl) {
  const suitableDays = [true, true, true]
  const calendarDoc = await getDocFromUrl(calendarUrl)
  const calendarsList = calendarDoc.querySelectorAll(
    'body > div > div:nth-child(3) > ul > li > a'
  )

  for (let i = 0; i < calendarsList.length; i++) {
    const personCalendarUrl = calendarsList[i]
    const personCalendarDoc = await getDocFromUrl(
      calendarUrl + personCalendarUrl
    )
    const personCalendarDayStatusList = personCalendarDoc.querySelectorAll(
      'body > table > tbody > tr > td'
    )

    for (let j = 0; j < personCalendarDayStatusList.length; j++) {
      const status = personCalendarDayStatusList[j]
      if (status.textContent.toLowerCase() !== 'ok') {
        suitableDays[j] = false
      }
    }
  }

  return suitableDays
}
