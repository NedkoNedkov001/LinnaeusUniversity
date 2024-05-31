import { JSDOM } from 'jsdom'

/**
 * Gets the document element of a given page.
 * @param {string} url url of page document to get.
 * @param {string} cookie cookie to add to the request header.
 * @returns {Element} document element of given page.
 */
export async function getDocumentElement (url, cookie) {
  const htmlString = await getTextFromUrl(url, cookie)

  const dom = new JSDOM(htmlString)
  const document = dom.window.document

  return document
}

/**
 * Gets JSON from a URL.
 * @param {string} url url to fetch json from.
 * @returns {JSON} json.
 */
export async function getJsonFromUrl (url) {
  const fetchRes = await tryFetch(url)
  const json = await fetchRes.json()
  return json
}

/**
 * Sends a POST request.
 * @param {string} url url to send request to.
 * @param {string} data data to send in body.
 * @returns {Response} response.
 */
export async function postRequestUrlEncoded (url, data) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: data.toString(),
    redirect: 'manual'
  })
  return response
}

/**
 * Gets a list of option values from a given document and selector.
 * @param {Element} doc document holding the options list.
 * @param {string} selector selector of the options list.
 * @returns {Array} list of option values.
 */
export function getNameWithValueList (doc, selector) {
  const nameWithValueList = []
  const items = doc.querySelectorAll(selector)

  items.forEach((element) => {
    const value = element.getAttribute('value')
    if (value) {
      nameWithValueList.push([element.innerHTML, value])
    }
  })

  return nameWithValueList
}
/**
 * Gets the HTML from a URL as a string.
 * @param {string} url url to get HTML from.
 * @param {string} cookie cookie to add to the request header.
 * @returns {string} HTML contents as a string.
 */
async function getTextFromUrl (url, cookie) {
  const fetchRes = await tryFetch(url, cookie)

  const htmlString = await fetchRes.text()
  return htmlString
}

/**
 * Gets the result of a fetch with url and an optional header cookie.
 * @param {string} url url to fetch.
 * @param {string} cookie optional cookie to add to the request header.
 * @returns {Response} response.
 */
async function tryFetch (url, cookie) {
  const fetchRes = await fetch(url, {
    headers: {
      cookie
    }
  })

  if (!fetchRes.ok) {
    console.error('Something failed. Try another URL.')
  }
  return fetchRes
}
