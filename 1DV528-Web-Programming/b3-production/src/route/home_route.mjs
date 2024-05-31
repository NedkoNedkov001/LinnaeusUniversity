import express from 'express'
import { controller } from '../controller/home_controller.mjs'

const router = express.Router()
export default router

router.get('/', controller.home)
