import express from 'express'
import { controller } from '../controller/user_controller.mjs'

const router = express.Router()
export default router

router.get('/register', controller.registerGet)
router.post('/register', controller.registerPost)
router.get('/login', controller.loginGet)
router.post('/login', controller.loginPost)
router.get('/logout', controller.logoutGet)
// router.get('/deleteAll', controller.deleteAllGet)
