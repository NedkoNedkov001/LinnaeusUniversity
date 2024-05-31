import express from 'express'
import { controller } from '../controller/snippet_controller.mjs'

const router = express.Router()
export default router

router.get('/', controller.listGet)
router.get('/add', controller.addGet)
router.post('/add', controller.addPost)
router.get('/view/:id', controller.viewGet)
router.get('/delete/:id', controller.deleteGet)
router.get('/edit/:id', controller.editGet)
router.post('/edit', controller.editPost)
// router.get('/deleteAll', controller.deleteAll)
