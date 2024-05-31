import * as dotenv from 'dotenv'
dotenv.config()

export const service = {}
const TOKEN_PARAM = '?access_token=' + process.env.ACCESS_TOKEN

service.getLabelByTitle = async (labelTitle) => {
  const URL =
        'https://gitlab.lnu.se/api/v4/projects/40437/labels' + TOKEN_PARAM

  const fetchRes = await fetch(URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  const json = await fetchRes.json()
  let labelJson = {}
  json.forEach((label) => {
    if (label.name === labelTitle) {
      labelJson = {
        label: label.name,
        text_color: label.text_color,
        color: label.color
      }
    }
  })
  return labelJson
}

service.getAllIssues = async () => {
  const URL = 'https://gitlab.lnu.se/api/v4/projects/40437/' + TOKEN_PARAM

  let fetchRes = await fetch(URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  let json = await fetchRes.json()
  const ISSUES_URL = json._links.issues + TOKEN_PARAM

  fetchRes = await fetch(ISSUES_URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  json = await fetchRes.json()

  const issues = []

  for (const issue of json) {
    // json.forEach((issue) => {
    const options = { month: 'long', day: 'numeric', year: 'numeric' }

    const dateCreated = new Date(issue.created_at)
    const formattedDateCreated = dateCreated.toLocaleDateString(
      'en-US',
      options
    )

    const dateUpdated = new Date(issue.updated_at)
    const formattedDateUpdated = dateUpdated.toLocaleDateString(
      'en-US',
      options
    )

    const assignees = []

    issue.assignees.forEach((assignee) => {
      assignees.push({
        avatar_url: assignee.avatar_url,
        web_url: assignee.web_url,
        username: assignee.username
      })
    })

    const author = {
      avatar_url: issue.author.avatar_url,
      web_url: issue.author.web_url
    }

    const milestone = issue.milestone
      ? {
          id: issue.milestone.id,
          title: issue.milestone.title,
          description: issue.milestone.description,
          state: issue.milestone.state,
          due_date: issue.milestone.due_date,
          web_url: issue.milestone.web_url
        }
      : null

    const labels = []

    for (const label of issue.labels) {
      const result = await service.getLabelByTitle(label)
      labels.push(result)
    }

    const state =
            issue.state === 'opened'
              ? { state: 'Open', color: '#008000' }
              : { state: 'Closed', color: '#800000' }

    issues.push({
      id: issue.iid,
      title: issue.title,
      created_on: formattedDateCreated,
      updated_on: formattedDateUpdated,
      assignees,
      author,
      web_url: issue.web_url,
      weight: issue.weight,
      milestone,
      num_comments: issue.user_notes_count,
      labels,
      state
    })
  }

  return issues
}

service.getIssue = async (issueId) => {
  const URL = `https://gitlab.lnu.se/api/v4/projects/40437/issues/${issueId}/${TOKEN_PARAM}`

  const fetchRes = await fetch(URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  const issue = await fetchRes.json()

  const options = { month: 'long', day: 'numeric', year: 'numeric' }

  const dateCreated = new Date(issue.created_at)
  const formattedDateCreated = dateCreated.toLocaleDateString('en-US', options)

  const dateUpdated = new Date(issue.updated_at)
  const formattedDateUpdated = dateUpdated.toLocaleDateString('en-US', options)

  const assignees = []

  issue.assignees.forEach((assignee) => {
    assignees.push({
      avatar_url: assignee.avatar_url,
      web_url: assignee.web_url,
      username: assignee.username,
      name: assignee.name
    })
  })

  const author = {
    name: issue.author.name,
    avatar_url: issue.author.avatar_url,
    web_url: issue.author.web_url
  }

  const milestone = issue.milestone
    ? {
        id: issue.milestone.id,
        title: issue.milestone.title,
        description: issue.milestone.description,
        state: issue.milestone.state,
        due_date: issue.milestone.due_date,
        web_url: issue.milestone.web_url
      }
    : null

  const labels = []

  for (const label of issue.labels) {
    const result = await service.getLabelByTitle(label)
    labels.push(result)
  }

  const state =
        issue.state === 'opened'
          ? { state: 'Open', color: '#008000' }
          : { state: 'Closed', color: '#800000' }

  const NOTES_URL = `https://gitlab.lnu.se/api/v4/projects/40437/issues/${issueId}/notes${TOKEN_PARAM}`
  const notesFetchRes = await fetch(NOTES_URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  const notesJson = await notesFetchRes.json()

  const comments = []

  notesJson.forEach((note) => {
    if (note.system === false) {
      const dateCreated = new Date(note.created_at)
      const formattedDateCreated = dateCreated.toLocaleDateString(
        'en-US',
        options
      )

      comments.push({
        author: note.author.name,
        author_avatar_url: note.author.avatar_url,
        author_web_url: note.author.web_url,
        created_on: formattedDateCreated,
        body: note.body
      })
    }
  })

  return {
    id: issue.iid,
    title: issue.title,
    description: issue.description,
    upvotes: issue.upvotes,
    downvotes: issue.downvotes,
    created_on: formattedDateCreated,
    updated_on: formattedDateUpdated,
    assignees,
    author,
    web_url: issue.web_url,
    weight: issue.weight,
    milestone,
    num_comments: issue.user_notes_count,
    comments,
    labels,
    state,
    due_date: issue.due_date
  }
}
