import * as dotenv from 'dotenv'
import mongoose from 'mongoose'
import bcrypt from 'bcrypt'

dotenv.config()
mongoose.Promise = global.Promise

export const model = {}

mongoose.set('strictQuery', false)
mongoose.connect(process.env.MONGO_URI, {
  serverSelectionTimeoutMS: 3000
})

const Schema = mongoose.Schema
const userSchema = new Schema({
  username: String,
  password: String
})

const User = mongoose.model('User', userSchema)

/**
 * Signs up a user with given username and password
 * @param {string} username username
 * @param {string} password password
 */
model.signUp = async (username, password) => {
  const saltRounds = 10
  password = await bcrypt.hash(password, saltRounds)

  const user = new User({
    username,
    password
  })

  await user.save()
}

/**
 * Tries to sign in with given username and password
 * @param {string} username username
 * @param {string} password password
 * @returns {boolean} login successful or not
 */
model.signIn = async (username, password) => {
  const user = await model.getByUsername(username)

  if (user) {
    const userPassword = user.password
    return await bcrypt.compare(password, userPassword)
  } else {
    return false
  }
}

/**
 * Gets a user with username
 * @param {string} username username
 * @returns {User} user
 */
model.getByUsername = async (username) => {
  return await User.findOne({
    username
  })
}

model.method = async () => {
  // Method logic
}
