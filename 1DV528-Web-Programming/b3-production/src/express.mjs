import express from 'express'
import logger from 'morgan'

import homeRoute from './route/home_route.mjs'
import issueRoute from './route/issue_route.mjs'
import wsServer from './websocket.mjs'

const app = express()

app.set('view engine', 'ejs')

app.use(logger('dev', { immediate: true }))
app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(express.static('public'))

app.use('/', homeRoute)
app.use('/issues', issueRoute)

export default (port = 5050) => {
  const server = app.listen(port, () => {
    console.log(`Listening at port ${port}`)
  })

  server.on('upgrade', (request, socket, head) => {
    wsServer.handleUpgrade(request, socket, head, socket => {
      wsServer.emit('connection', socket, request)
    })
  })
}
