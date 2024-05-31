import express from 'express'
import { controller } from '../controller/snippet_controller.mjs'

const router = express.Router()
export default router

// router.get('/', controller.method)
router.get('/', controller.all)
router.get('/add', controller.add)
router.post('/add', controller.addProcess)
router.get('/delete/:id', controller.delete)
router.get('/edit/:id', controller.edit)
router.post('/edit', controller.editProcess)
