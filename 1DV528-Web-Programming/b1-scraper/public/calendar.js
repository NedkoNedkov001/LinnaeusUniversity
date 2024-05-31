import { getDocumentElement } from './utils.js'

/**
 * Gets a list of available days for all people.
 * @param {string} calendarsUrl Url of the calendar page.
 * @returns {Array} List of booleans indicating whether a day is available for all people.
 */
export async function getListOfAvailableDays (calendarsUrl) {
  const availableDays = [true, true, true]
  const calendarsPageDoc = await getDocumentElement(calendarsUrl)

  const allCalendarLinks = calendarsPageDoc.querySelectorAll(
    'body > div > div > ul > li > a'
  )

  const peopleAvailableDays = []
  for (let i = 0; i < allCalendarLinks.length; i++) {
    const url =
      calendarsUrl + allCalendarLinks[i].getAttribute('href').replace('./', '')
    const personAvailableDays = await getAvailableDaysFromCalendar(url)
    peopleAvailableDays.push(personAvailableDays)
  }

  for (let i = 0; i < peopleAvailableDays.length; i++) {
    for (let j = 0; j < 3; j++) {
      if (peopleAvailableDays[i][j] === false) {
        availableDays[j] = false
      }
    }
  }

  return availableDays
}

/**
 * Gets a list of people and the days they are available on.
 * @param {string} calendarUrl url of calendar page.
 * @returns {Array} A nested list where the main list holds the people and the nested lists hold booleans indicating whether the person is available or not on Friday, Saturday, Sunday
 */
async function getAvailableDaysFromCalendar (calendarUrl) {
  const daysAvailable = []
  const calendarPageDoc = await getDocumentElement(calendarUrl)

  const dayStatuses = calendarPageDoc.querySelectorAll(
    'body > table > tbody > tr > td'
  )

  for (let i = 0; i < dayStatuses.length; i++) {
    const isAvailable = dayStatuses[i].innerHTML.toLowerCase() === 'ok'
    daysAvailable.push(isAvailable)
  }

  return daysAvailable
}
