import { model } from './../model/snippet.mjs'

export const controller = {}

controller.method = async (req, res) => {
  // Method logic
}

/**
 * Shows all snippets
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.all = async (req, res) => {
  const snippets = await model.getAll()
  return res.render('layout', { page: 'snippets', snippets })
}

/**
 * Shows the page to add a snippet if logged in, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.add = async (req, res) => {
  if (req.session.user) {
    return res.render('layout', { page: 'add' })
  } else {
    return res.status(403).render('layout', { page: '403' })
  }
}

/**
 * Adds a snippet if logged in, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.addProcess = async (req, res) => {
  if (req.session.user) {
    const progLang = req.body.progLang
    const codeStr = req.body.codeStr
    const author = req.session.user.username
    await model.add(progLang, codeStr, author)
    req.session.flashMessage = 'Added snippet!'
    return res.redirect('/snippet/')
  } else {
    return res.status(403).render('layout', { page: '403' })
  }
}

/**
 * Deletes a snippet if logged in and author, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.delete = async (req, res) => {
  const snippet = await model.getById(req.params.id)
  if (!snippet) {
    return res.status(404).render('layout', { page: '403' })
  }
  if (req.session.user && snippet.author === req.session.user.username) {
    await model.delete(snippet)
    req.session.flashMessage = 'Deleted snippet!'
    return res.redirect('/snippet')
  } else {
    return res.status(403).render('layout', { page: '403' })
  }
}

/**
 * Shows the page to edit a snippet if logged in and author, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.edit = async (req, res) => {
  if (req.session.user) {
    const snippet = await model.getById(req.params.id)
    if (snippet && snippet.author === req.session.user.username) {
      return res.render('layout', { page: 'edit', snippet })
    }
  }
  return res.status(403).render('layout', { page: '403' })
}

/**
 * Edits a snippet if logged in and author, otherwise 403
 * @param {Request} req request
 * @param {Response} res response
 * @returns {string} page
 */
controller.editProcess = async (req, res) => {
  const id = req.body.id
  const snippet = await model.getById(id)
  if (
    snippet &&
    req.session.user &&
    snippet.author === req.session.user.username
  ) {
    const progLang = req.body.progLang
    const codeStr = req.body.codeStr

    await model.edit(id, progLang, codeStr)
    req.session.flashMessage = 'Edited snippet!'
    return res.redirect('/snippet')
  }
  return res.status(403).render('layout', { page: '403' })
}
