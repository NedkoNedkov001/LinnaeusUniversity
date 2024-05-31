import { getDocumentElement, postRequestUrlEncoded } from './utils.js'

/**
 * Gets a list of all available bar reservations.
 * @param {string} barUrl Url of bar page.
 * @returns {Array} JSON list of reservation information.
 */
export async function getListOfAvailableBarReservations (barUrl) {
  const barPageDoc = await getDocumentElement(barUrl)
  const loginUrl =
      barUrl +
      barPageDoc
        .querySelector('body > div > form')
        .getAttribute('action')
        .replace('./', '')

  const data = new URLSearchParams({
    username: 'zeke',
    password: 'coys'
  })

  const loginResponse = await postRequestUrlEncoded(loginUrl, data)

  const cookie = loginResponse.headers.get('set-cookie')
  const bookingUrl = barUrl + loginResponse.headers.get('location')
  const bookingDoc = await getDocumentElement(bookingUrl, cookie)

  const freeElements = bookingDoc.getElementsByName('group1')
  const freeBookings = []
  for (let i = 0; i < freeElements.length; i++) {
    const currValue = freeElements[i].getAttribute('value')
    freeBookings.push({
      day: currValue.substring(0, 3),
      start: parseInt(currValue.substring(3, 5)),
      end: parseInt(currValue.substring(5, 7))
    })
  }
  return freeBookings
}
