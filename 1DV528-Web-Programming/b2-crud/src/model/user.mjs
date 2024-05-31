import * as dotenv from 'dotenv'
import mongoose from 'mongoose'
import bcrypt from 'bcrypt'

dotenv.config()
mongoose.Promise = global.Promise

export const model = {}

//
// Connect to the MongoDB database
//
mongoose.set('strictQuery', false) // fix to prepare for 7.0 and avoid warning
mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
  serverSelectionTimeoutMS: 3000
})

//
// Create a database schema
//
const Schema = mongoose.Schema
const userSchema = new Schema({
  username: String,
  password: String,
  email: String
})

// Compile a model from the schema
const User = mongoose.model('User', userSchema)

/**
 * Add new user.
 * @param {string} username username of the user.
 * @param {string} password password of the user.
 * @param {string} email email of the user.
 */
model.add = async (username, password, email) => {
  const saltRounds = 10
  const hashedPassword = await bcrypt.hash(password, saltRounds)

  const user = new User({
    username,
    password: hashedPassword,
    email
  })

  await user.save()
}

model.isCorrectLogin = async (username, password) => {
  const user = await model.getByUsername(username)

  if (user) {
    const hashedPassword = user.password
    return await bcrypt.compare(password, hashedPassword)
  } else {
    return false
  }
}

/**
 * Get a user by ID.
 * @param {string} id an ID to search for.
 * @returns {User} user.
 */
model.getById = async (id) => {
  return await User.findById({
    _id: id
  })
}

/**
 * Get a user by username.
 * @param {string} username a username to search for.
 * @returns {User} user.
 */
model.getByUsername = async (username) => {
  return await User.findOne({
    username
  })
}

// model.deleteAll = async () => {
//   console.log("Deleting users");
//   await User.deleteMany({});
//   console.log(User.length);
// };
