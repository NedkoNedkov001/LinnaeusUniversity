import * as dotenv from 'dotenv'

import mongoose from 'mongoose'
dotenv.config()
mongoose.Promise = global.Promise

export const model = {}

mongoose.set('strictQuery', false)
mongoose.connect(process.env.MONGO_URI, {
  serverSelectionTimeoutMS: 3000
})

const Schema = mongoose.Schema
const snippetSchema = new Schema({
  progLang: String,
  codeStr: String,
  author: String
})

const Snippet = mongoose.model('Snippet', snippetSchema)

model.method = async () => {
  // Method logic
}

/**
 * Gets all snippets from database
 * @returns {Array} array of snippets
 */
model.getAll = async () => {
  const snippets = await Snippet.find()
  return snippets.reverse()
}

/**
 * Adds a snippet
 * @param {string} progLang programming language the snippet is written in
 * @param {string} codeStr code of the snippet
 * @param {string} author author of the snippet
 */
model.add = async (progLang, codeStr, author) => {
  const snippet = new Snippet({
    progLang,
    codeStr,
    author
  })

  await snippet.save()
}

/**
 * Gets a snippet by id
 * @param {string} id id of snippet
 * @returns {Snippet} snippet
 */
model.getById = async (id) => {
  return await Snippet.findById(id)
}

/**
 * Deletes a snippet
 * @param {Snippet} snippet snippet to delete
 */
model.delete = async (snippet) => {
  await Snippet.deleteOne(snippet)
}

/**
 * Edits a snippet with given id and values
 * @param {string} id id of snippet to edit
 * @param {string} progLang programming language of snippet
 * @param {string} codeStr code of snippet
 */
model.edit = async (id, progLang, codeStr) => {
  await Snippet.findByIdAndUpdate(id, { progLang, codeStr })
}
