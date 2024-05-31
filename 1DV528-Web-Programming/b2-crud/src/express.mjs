import express from 'express'
import logger from 'morgan'
import session from 'express-session'

import userRoute from './route/user_route.mjs'
import snippetRoute from './route/snippet_route.mjs'

const app = express()

app.set('view engine', 'ejs')

app.use(
  session({
    cookie: {
      maxAge: 60000
    },
    resave: false,
    saveUninitialized: true,
    secret: 'nn222mx'
  })
)

app.use((req, res, next) => {
  res.locals.flashMessage = null
  if (req.session && req.session.flashMessage) {
    res.locals.flashMessage = req.session.flashMessage
    console.log(res.locals.flashMessage)
    req.session.flashMessage = null
  }
  next()
})

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
app.use(express.static('public'))

// app.get('/', (req, res) => {
//   res.send('Hello World!\n')
// })
app.use('/', snippetRoute)
app.use('/user', userRoute)

app.use((req, res) => {
  console.log('Test')
  const data = { page: '404' }
  res.status(404).render('layout', data)
})

export default (port = 3000) => {
  app.listen(port, () => {
    console.log(`Listening at port ${port}`)
  })
}
