import { JSDOM } from 'jsdom'

/**
 * Gets document from URL
 * @param {string} url URL
 * @returns {HTMLElement} HTML document
 */
export async function getDocFromUrl (url) {
  const fetchRes = await fetch(url)
  const htmlString = await fetchRes.text()

  const dom = new JSDOM(htmlString)
  const doc = dom.window.document
  return doc
}
/**
 * Gets document from URL with a cookie in the request
 * @param {string} url URL
 * @param {string} cookie Cookie
 * @returns {HTMLElement} document
 */
export async function getDocFromUrlWithCookie (url, cookie) {
  const fetchRes = await fetch(url, {
    headers: {
      cookie
    }
  })
  const htmlString = await fetchRes.text()

  const dom = new JSDOM(htmlString)
  const doc = dom.window.document
  return doc
}

/**
 * Gets JSON from URL
 * @param {string} url URL
 * @returns {JSON} JSON
 */
export async function getJsonFromUrl (url) {
  const fetchRes = await fetch(url)
  const json = await fetchRes.json()
  return json
}

/**
 * Sends a POST request
 * @param {string} url URL
 * @param {URLSearchParams} body Request body
 * @returns {Response} Request response
 */
export async function postToUrl (url, body) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: body.toString(),
    redirect: 'manual'
  })
  return response
}
