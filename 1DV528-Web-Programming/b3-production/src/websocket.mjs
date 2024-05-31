// import { WebSocketServer } from 'ws'
import WebSocket, { WebSocketServer } from 'ws'

const wsServer = new WebSocketServer({
  noServer: true,
  clientTracking: true
})
export default wsServer

wsServer.on('connection', (ws) => {
  console.log('Connection received. Adding client.')
  wsServer.broadcastExceptSelf(ws, `New client connected (${wsServer.clients.size}).`)

  ws.on('message', (data) => {
    wsServer.broadcastExceptSelf(ws, data)
  })

  ws.on('close', () => console.info('Client closed connection'))
  ws.on('error', console.error)
})

/**
 * Broadcast a message to all clients.
 * @param {object} ws the current ws client
 * @param {string} data  the message to broadcast
 */
wsServer.broadcastExceptSelf = (ws, data) => {
  let clients = 0

  wsServer.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      clients++
      client.send(data)
    }
  })
  console.log(`Broadcasted data to ${clients} (${wsServer.clients.size}) clients.`)
}
