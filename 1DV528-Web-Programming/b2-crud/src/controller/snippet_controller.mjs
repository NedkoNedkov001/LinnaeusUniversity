import { model as snippetModel } from './../model/snippet.mjs'

export const controller = {}

controller.listGet = async (req, res) => {
  const snippets = await snippetModel.listAll()
  const topLanguages = await snippetModel.getTopLanguages()
  const data = { page: 'home', snippets, topLanguages }
  return res.render('layout', data)
}

controller.addGet = async (req, res) => {
  if (req.session.user) {
    const data = { page: 'add' }
    res.render('layout', data)
  } else {
    res.redirect('/user/login')
  }
}

controller.addPost = async (req, res) => {
  if (req.session.user) {
    const name = req.body.snippetName
    const lang = req.body.snippetLanguage
    const desc = req.body.snippetDescription
    const snippet = req.body.snippet
    const author = req.session.user.username
    const currDate = new Date()
    const currTimeJson = {
      year: currDate.getFullYear(),
      month: currDate.getMonth() + 1,
      day: currDate.getDate(),
      hours: currDate.getHours(),
      minutes: currDate.getMinutes(),
      seconds: currDate.getSeconds()
    }

    await snippetModel.add(name, lang, desc, snippet, author, currTimeJson)
    req.session.flashMessage = 'Snippet added successfully!'
    return res.redirect('/')
  } else {
    const data = { page: '403' }
    return res.status('403').render('layout', data)
  }
}

controller.viewGet = async (req, res) => {
  const snippet = await snippetModel.getSnippet(req.params.id)
  if (!snippet) {
    const data = { page: '404' }
    return res.status('404').render('layout', data)
  }
  const data = { page: 'view', snippet }
  return res.render('layout', data)
}

controller.deleteGet = async (req, res) => {
  const snippet = await snippetModel.getSnippet(req.params.id)
  if (!snippet) {
    const data = { page: '404' }
    return res.status('404').render('layout', data)
  }

  if (req.session.user && snippet.author === req.session.user.username) {
    await snippetModel.delete(snippet)
    req.session.flashMessage = 'Deleted snippet successfully!'
    return res.redirect('/')
  } else {
    const data = { page: '403' }
    return res.status('403').render('layout', data)
  }
}

controller.editGet = async (req, res) => {
  const snippet = await snippetModel.getSnippet(req.params.id)
  if (!snippet) {
    const data = { page: '404' }
    return res.status('404').render('layout', data)
  }
  if (req.session.user && snippet.author === req.session.user.username) {
    const data = { page: 'edit', snippet }
    return res.render('layout', data)
  } else {
    const data = { page: '403' }
    return res.status('403').render('layout', data)
  }
}

controller.editPost = async (req, res) => {
  const id = req.body.id
  const snippet = await snippetModel.getSnippet(id)
  if (req.session.user && snippet.author === req.session.user.username) {
    const name = req.body.snippetName
    const lang = req.body.snippetLanguage
    const desc = req.body.snippetDescription
    const snippetStr = req.body.snippet

    await snippetModel.edit(id, name, lang, desc, snippetStr)
    req.session.flashMessage = 'Snippet edited successfully!'
    return res.redirect('/')
  } else {
    const data = { page: '403' }
    return res.status('403').render('layout', data)
  }
}

// controller.deleteAll = async (req, res) => {
//   snippetModel.deleteAll();
// }
