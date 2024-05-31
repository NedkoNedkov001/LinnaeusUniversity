import * as dotenv from 'dotenv'

import mongoose from 'mongoose'
dotenv.config()
mongoose.Promise = global.Promise

export const model = {}

//
// Connect to the MongoDB database
//
mongoose.set('strictQuery', false)
mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
  serverSelectionTimeoutMS: 3000
})

//
// Create a database schema
//
const Schema = mongoose.Schema
const snippetSchema = new Schema({
  name: String,
  lang: String,
  desc: String,
  snippetStr: String,
  author: String,
  createdOn: Object
})

const Snippet = mongoose.model('Snippet', snippetSchema)

/**
 * List all snippets.
 * @returns {Array} array with snippets.
 */
model.listAll = async () => {
  const snippets = await Snippet.find()
  return snippets.reverse()
}

/**
 * Add a new snippet.
 * @param {string} name name of snippet.
 * @param {string} lang language of snippet.
 * @param {string} desc description of snippet.
 * @param {string} snippetStr snippet as string.
 * @param {string} author username of author.
 * @param {Array} createdOn time of creation.
 */
model.add = async (name, lang, desc, snippetStr, author, createdOn) => {
  const snippet = new Snippet({
    name,
    lang,
    desc,
    snippetStr,
    author,
    createdOn
  })

  await snippet.save()
}

/**
 * Get a snippet with ID.
 * @param {string} id an ID to search for.
 * @returns {Snippet} snippet.
 */
model.getSnippet = async (id) => {
  try {
    return await Snippet.findById(id)
  } catch (error) {
    return null
  }
}

/**
 * Delete a snippet with given ID.
 * @param {string} snippet snippet to delete.
 */
model.delete = async (snippet) => {
  await Snippet.deleteOne(snippet)
}

model.edit = async (id, name, lang, desc, snippetStr) => {
  await Snippet.findByIdAndUpdate(id, { name, lang, desc, snippetStr })
}

model.getTopLanguages = async () => {
  const snippets = await Snippet.find()
  const languages = {}

  for (let i = 0; i < snippets.length; i++) {
    const language = snippets[i].lang
    languages[language] = (languages[language] || 0) + 1
  }

  const languagesArray = Object.entries(languages)
  languagesArray.sort((a, b) => b[1] - a[1])
  const top10Languages = languagesArray.slice(0, 10)
  const top10LanguagesJson = Object.fromEntries(top10Languages)

  return top10LanguagesJson
}

// model.deleteAll = async () => {
//   await Snippet.deleteMany({});
// };
