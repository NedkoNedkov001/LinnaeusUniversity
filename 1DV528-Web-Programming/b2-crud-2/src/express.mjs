import express from 'express'
import logger from 'morgan'
import session from 'express-session'

import userRoute from './route/user_route.mjs'
import snippetRoute from './route/snippet_route.mjs'

const app = express()

app.set('view engine', 'ejs')

// Session to keep track of sign ins
app.use(
  session({
    cookie: {
      maxAge: 60000
    },
    resave: false,
    saveUninitialized: true,
    secret: 'b2Crud'
  })
)

// Shows flash messages when needed
app.use((req, res, next) => {
  res.locals.flashMessage = null
  if (req.session && req.session.flashMessage) {
    res.locals.flashMessage = req.session.flashMessage
    req.session.flashMessage = null
  }
  next()
})

// Passes the current session's user to page
app.use((req, res, next) => {
  res.locals.user = {
    username: null
  }

  if (req.session && req.session.user) {
    res.locals.user.username = req.session.user.username ?? null
  }
  next()
})

app.use(logger('dev', { immediate: true }))
app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(express.static('res'))

// Make /snippet the main page
app.get('/', (req, res) => {
  res.redirect('/snippet/')
})

app.use('/snippet', snippetRoute)
app.use('/user', userRoute)

// Renders page 404 when page is not found
app.use((req, res) => {
  const data = { page: '404' }
  res.status(404).render('layout', data)
})

export default (port = 3000) => {
  app.listen(port, () => {
    console.log(`Listening at port ${port}`)
  })
}
