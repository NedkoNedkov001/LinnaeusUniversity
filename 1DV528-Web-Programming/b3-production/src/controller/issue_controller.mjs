import { service } from '../service/issue_service.mjs'
import wsServer from '../websocket.mjs'

export const controller = {}

controller.browseIssues = async (req, res) => {
  const data = { page: 'browse' }
  return res.render('layout', data)
}

controller.viewIssue = async (req, res) => {
  const issueId = req.params.id

  const data = { page: 'view', issueId }
  return res.render('layout', data)
}

controller.getIssuesJson = async (req, res) => {
  const issues = await service.getAllIssues()

  return res.json(issues)
}

controller.getIssueJson = async (req, res) => {
  const issueId = req.params.id

  const issue = await service.getIssue(issueId)

  return res.json(issue)
}

controller.webhook = async (req, res) => {
  if (req.headers['x-gitlab-token'] === process.env.ACCESS_TOKEN) {
    const message = { isWebHook: true }
    wsServer.broadcastExceptSelf(null, JSON.stringify(message))

    res.status(200).send('Webhook received successfully')
  }
}
