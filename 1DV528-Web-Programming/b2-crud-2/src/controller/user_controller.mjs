import { model } from './../model/user.mjs'

export const controller = {}

controller.method = async (req, res) => {
  // Method logic
}

/**
 * Shows the page to sign up
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.signUp = async (req, res) => {
  return res.render('layout', { page: 'signup' })
}

/**
 * Signs up a user if unique username
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.signUpProcess = async (req, res) => {
  const username = req.body.username
  const password = req.body.password

  if (!(await model.getByUsername(username))) {
    await model.signUp(username, password)
    req.session.user = {
      username
    }
    req.session.flashMessage = `Welcome, ${username}`
    return res.redirect('/snippet/')
  } else {
    req.session.flashMessage = 'Username is already taken'
    return res.redirect('/user/signup')
  }
}

/**
 * Shows the page to sign in
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.signIn = async (req, res) => {
  return res.render('layout', { page: 'signin' })
}

/**
 * Signs in a user if credentials match, otherwise shows a message
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.signInProcess = async (req, res) => {
  const username = req.body.username
  const password = req.body.password

  if (await model.signIn(username, password)) {
    req.session.user = {
      username
    }
    req.session.flashMessage = `Welcome, ${username}`
    return res.redirect('/snippet/')
  }
  req.session.flashMessage = 'Wrong credentials'
  return res.redirect('/user/signIn')
}

/**
 * Signs out a user if logged in, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.signOut = async (req, res) => {
  if (req.session.user) {
    req.session.flashMessage = `Goodbye, ${req.session.user.username}!`
    req.session.user = null
    return res.redirect('/snippet/')
  } else {
    return res.status(403).render('layout', { page: '403' })
  }
}
