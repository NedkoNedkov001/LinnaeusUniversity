import express from 'express'
import { controller } from '../controller/user_controller.mjs'

const router = express.Router()
export default router

router.get('/', controller.method)
router.get('/signup', controller.signUp)
router.post('/signup', controller.signUpProcess)
router.get('/signin', controller.signIn)
router.post('/signin', controller.signInProcess)
router.get('/signout', controller.signOut)
