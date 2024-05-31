import { model as userModel } from './../model/user.mjs'

export const controller = {}

controller.registerGet = async (req, res) => {
  if (!req.session.user) {
    const data = { page: 'register' }
    return res.render('layout', data)
  } else {
    return res.redirect('/')
  }
}

controller.registerPost = async (req, res) => {
  const username = req.body.username
  const password = req.body.password
  const email = req.body.email // Not unique

  if (!(await userModel.getByUsername(username))) {
    await userModel.add(username, password, email)
    req.session.user = {
      username
    }
    req.session.flashMessage = `Welcome, '${username}'!`

    return res.redirect('/')
  } else {
    req.session.flashMessage = 'Username is taken. Try another one!'
    return res.redirect('/user/register')
  }
}

controller.loginGet = async (req, res) => {
  if (!req.session.user) {
    const data = { page: 'login' }
    return res.render('layout', data)
  } else {
    return res.redirect('/')
  }
}

controller.loginPost = async (req, res) => {
  const username = req.body.username
  const password = req.body.password

  const success = await userModel.isCorrectLogin(username, password)

  if (success) {
    req.session.user = {
      username
    }
    req.session.flashMessage = `Welcome, '${username}'!`
    return res.redirect('/')
  }

  req.session.user = null
  req.session.flashMessage = 'Wrong user or password!'
  return res.redirect('/user/login')
}

controller.logoutGet = async (req, res) => {
  if (req.session.user) {
    req.session.user = null
    req.session.flashMessage = 'Logged out!'
  }
  return res.redirect('/')
}

// controller.deleteAllGet = async (req, res) => {
//   userModel.deleteAll();
//   res.send("Deleted all users");
// };
