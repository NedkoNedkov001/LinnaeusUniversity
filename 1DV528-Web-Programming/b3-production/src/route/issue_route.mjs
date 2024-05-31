import express from 'express'
import { controller } from '../controller/issue_controller.mjs'

const router = express.Router()
export default router

router.get('/browse', controller.browseIssues)
router.get('/view/:id', controller.viewIssue)
router.get('/json/:id', controller.getIssueJson)
router.get('/json/', controller.getIssuesJson)
router.post('/webhook', controller.webhook)
