const url = 'wss://cscloud7-197.lnu.se/'
const websocket = new WebSocket(url)
const ISSUES_URL = 'https://cscloud7-197.lnu.se/issues/json/'

websocket.onmessage = async (event) => {
  try {
    const json = JSON.parse(event.data)
    if (json.isWebHook === true) {
      updateIssues()
    }
  } catch (error) {}
}

/**
 *
 */
async function updateIssues () {
  const issueList = document.querySelector('body > div > div.issues > ul')
  if (issueList) {
    const issues = await getIssues()

    issueList.innerHTML = ''
    for (let i = 0; i < issues.length; i++) {
      const issue = issues[i]
      const issueElement = createIssueElement(issue)
      issueList.appendChild(issueElement)
    }
    return
  }

  const wrapper = document.querySelector('.content-wrapper')
  const issue = document.querySelector('.issue-detailed')
  if (issue) {
    const issueId = parseInt(issue.getAttribute('id'))
    const updatedIssue = await getIssue(issueId)
    const updatedIssueElement = createIssueDetailedElement(updatedIssue)
    wrapper.innerHTML = ''
    wrapper.appendChild(updatedIssueElement)
  }
}

/**
 * Gets a list of all issues.
 * @returns {Array} List of issues.
 */
async function getIssues () {
  const fetchRes = await fetch(ISSUES_URL, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  const json = await fetchRes.json()
  return json
}

/**
 * Gets an issue with a given ID.
 * @param {string} issueId issue ID.
 * @returns {JSON} issue.
 */
async function getIssue (issueId) {
  const fetchRes = await fetch(ISSUES_URL + issueId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  const json = await fetchRes.json()
  return json
}

/**
 * Creates an issue element to add to the issue list.
 * @param {JSON} issue issue to create element for.
 * @returns {Element} DOM element
 */
function createIssueElement (issue) {
  const listItem = document.createElement('li')

  const issueDiv = document.createElement('div')
  issueDiv.classList.add('issue')
  issueDiv.id = `issue-${issue.id}`

  listItem.appendChild(issueDiv)

  const lineOneDiv = document.createElement('div')
  lineOneDiv.classList.add('issue-line-one')

  const titleDiv = document.createElement('div')
  titleDiv.classList.add('issue-title')

  const issueIcon = document.createElement('i')
  issueIcon.classList.add('fa-solid')
  issueIcon.classList.add('fa-file')
  titleDiv.appendChild(issueIcon)

  const titleLink = document.createElement('a')
  titleLink.href = `/issues/view/${issue.id}`
  const titleHeader = document.createElement('h3')
  titleHeader.textContent = issue.title
  titleLink.appendChild(titleHeader)
  titleDiv.appendChild(titleLink)

  const stateDiv = document.createElement('div')
  stateDiv.classList.add('issue-state')
  stateDiv.style.backgroundColor = issue.state.color
  stateDiv.textContent = issue.state.state

  lineOneDiv.appendChild(titleDiv)
  titleDiv.appendChild(stateDiv)

  const lineOneRightDiv = document.createElement('div')
  lineOneRightDiv.classList.add('issue-line-one-right')

  const assigneesList = document.createElement('ul')
  assigneesList.classList.add('assignees-list')
  for (const assignee of issue.assignees) {
    const assigneeItem = document.createElement('li')
    const assigneeLink = document.createElement('a')
    assigneeLink.href = assignee.web_url
    const assigneeImg = document.createElement('img')
    assigneeImg.classList.add('user-img')
    assigneeImg.src = assignee.avatar_url
    assigneeImg.title = assignee.username
    assigneeLink.appendChild(assigneeImg)
    assigneeItem.appendChild(assigneeLink)
    assigneesList.appendChild(assigneeItem)
  }

  const commentsLink = document.createElement('a')
  commentsLink.href = `/issues/view/${issue.id}#comments`
  commentsLink.innerHTML = `${issue.num_comments} <i class="fa-solid fa-comments"></i>`

  lineOneRightDiv.appendChild(assigneesList)
  lineOneRightDiv.appendChild(commentsLink)

  lineOneDiv.appendChild(lineOneRightDiv)

  const lineTwoDiv = document.createElement('div')
  lineTwoDiv.classList.add('issue-line-two')

  const infoDiv = document.createElement('div')
  infoDiv.classList.add('issue-info')
  infoDiv.innerHTML = `# ${issue.id} <br>
                         <i class="fa-solid fa-calendar"></i> ${issue.created_on} <br>`
  if (issue.weight) {
    infoDiv.innerHTML += `<i class="fa-solid fa-weight-hanging"></i> ${issue.weight} <br>`
  }
  if (issue.milestone) {
    infoDiv.innerHTML += `<i class="fa-solid fa-clock"></i> <a class='text-anchor' href='${issue.milestone.web_url}' hover-text="Expires on: ${issue.milestone.due_date}">${issue.milestone.title}</a>`
  }

  lineTwoDiv.appendChild(infoDiv)

  const updateInfoDiv = document.createElement('div')
  updateInfoDiv.classList.add('issue-update')
  updateInfoDiv.innerHTML = `Last update: ${issue.updated_on}`

  lineTwoDiv.appendChild(updateInfoDiv)

  issueDiv.appendChild(lineOneDiv)
  issueDiv.appendChild(lineTwoDiv)

  const lineThreeDiv = document.createElement('div')
  lineThreeDiv.classList.add('issue-line-three')

  const labelList = document.createElement('ul')
  labelList.classList.add('label-list')
  for (const label of issue.labels) {
    const labelItem = document.createElement('li')
    const labelDiv = document.createElement('div')
    labelDiv.classList.add('issue-label')
    labelDiv.style.backgroundColor = label.color
    labelDiv.style.color = label.text_color
    labelDiv.textContent = label.label
    labelItem.appendChild(labelDiv)
    labelList.appendChild(labelItem)
  }

  lineThreeDiv.appendChild(labelList)

  issueDiv.appendChild(lineThreeDiv)

  return listItem
}

/**
 * Creates an issue element to show in detailed view.
 * @param {JSON} issue issue to create element  for.
 * @returns {Element} DOM element
 */
function createIssueDetailedElement (issue) {
  const detailedIssueDiv = document.createElement('div')
  detailedIssueDiv.classList.add('issue-detailed', 'two-side')
  detailedIssueDiv.id = issue.id

  const leftSideDiv = document.createElement('div')
  leftSideDiv.classList.add('left-side')

  const titleHeader = document.createElement('h2')
  titleHeader.classList.add('issue-title-big')
  titleHeader.textContent = issue.title

  const secondRow = document.createElement('div')
  secondRow.classList.add('row')

  const state = document.createElement('p')
  state.style.backgroundColor = issue.state.color
  state.classList.add('issue-state')
  state.textContent = issue.state.state

  secondRow.appendChild(state)

  const createdOn = document.createElement('p')
  createdOn.classList.add('row')
  createdOn.innerHTML = ` Issue created on ${issue.created_on} by 
    <a href="${issue.author.web_url}" class="text-anchor" hover-text="${issue.author.name}">
        <img class="user-img" src="${issue.author.avatar_url}" />
    </a>`

  secondRow.appendChild(createdOn)

  const detailsDiv = document.createElement('div')
  detailsDiv.classList.add('description')
  detailsDiv.innerHTML = `<p>${issue.description}</p>`

  const ratingDiv = document.createElement('div')
  ratingDiv.classList.add('row')
  const upvoteDiv = document.createElement('div')
  upvoteDiv.classList.add('rating', 'upvote')
  upvoteDiv.innerHTML = `<i class="fa-regular fa-thumbs-up"> ${issue.upvotes}</i>`
  const downvoteDiv = document.createElement('div')
  downvoteDiv.classList.add('rating', 'downvote')
  downvoteDiv.innerHTML = `<i class="fa-regular fa-thumbs-down"> ${issue.downvotes}</i>`
  ratingDiv.appendChild(upvoteDiv)
  ratingDiv.appendChild(downvoteDiv)

  const commentsDiv = document.createElement('div')
  commentsDiv.classList.add('comments')
  commentsDiv.innerHTML = `<h2 class="issue-comments-heading">Comments (${issue.comments ? issue.comments.length : 0})</h2>
                                 <ul class="comments-list"></ul>`
  const commentsList = commentsDiv.querySelector('.comments-list')
  if (issue.comments) {
    for (const comment of issue.comments) {
      const commentItem = document.createElement('li')
      commentItem.innerHTML = `<div class="comment">
                                            <a href="${comment.author_web_url}"><img class="user-img" src="${comment.author_avatar_url}" alt="" /></a>
                                            <div class="comment-details">
                                                <div class="row">
                                                    <a href="${comment.author_web_url}">${comment.author}</a>
                                                    <p>${comment.created_on}</p>
                                                </div>
                                                <p>${comment.body}</p>
                                            </div>
                                        </div>`
      commentsList.appendChild(commentItem)
    }
  }

  leftSideDiv.appendChild(titleHeader)
  leftSideDiv.appendChild(secondRow)
  leftSideDiv.appendChild(detailsDiv)
  leftSideDiv.appendChild(ratingDiv)
  leftSideDiv.appendChild(commentsDiv)

  const detailedInfoDiv = document.createElement('div')
  detailedInfoDiv.classList.add('issue-detailed-info')

  if (issue.assignees.length > 0) {
    const assigneesDiv = document.createElement('div')
    assigneesDiv.classList.add('issue-detailed-assignees')
    assigneesDiv.innerHTML = `<p class="issue-detailed-info-label">${issue.assignees.length} Assignees:</p>
                                      <ul class="assignees-list-detailed"></ul>`
    const assigneesList = assigneesDiv.querySelector('.assignees-list-detailed')
    for (const assignee of issue.assignees) {
      const assigneeItem = document.createElement('li')
      assigneeItem.innerHTML = `<a class="assignee-detailed" href="${assignee.web_url}"><img class="user-img" src="${assignee.avatar_url}" />${assignee.name}</a>`
      assigneesList.appendChild(assigneeItem)
    }
    detailedInfoDiv.appendChild(assigneesDiv)
  }

  if (issue.labels.length > 0) {
    const labelsDiv = document.createElement('div')
    labelsDiv.innerHTML = `<p class="issue-detailed-info-label">Labels:</p>
                                   <ul class="labels-list"></ul>`
    const labelsList = labelsDiv.querySelector('.labels-list')
    for (const label of issue.labels) {
      const labelItem = document.createElement('li')
      labelItem.innerHTML = `<div style="background-color: ${label.color}; color: ${label.text_color}" class="issue-label">${label.label}</div>`
      labelsList.appendChild(labelItem)
    }
    detailedInfoDiv.appendChild(labelsDiv)
  }

  if (issue.milestone) {
    const milestoneDiv = document.createElement('div')
    milestoneDiv.innerHTML = `<p class="issue-detailed-info-label">Milestone:</p>
                                       <a class='text-anchor' href='${issue.milestone.web_url}' hover-text="Expires on: ${issue.milestone.due_date}">${issue.milestone.title}</a>`
    detailedInfoDiv.appendChild(milestoneDiv)
  }

  if (issue.weight) {
    const weightDiv = document.createElement('div')
    weightDiv.innerHTML = `<p class="issue-detailed-info-label">Weight:</p>
                                   <p>${issue.weight}</p>`
    detailedInfoDiv.appendChild(weightDiv)
  }

  if (issue.due_date) {
    const dueDateDiv = document.createElement('div')
    dueDateDiv.innerHTML = `<p class="issue-detailed-info-label">Due date:</p>
                                    <p>${issue.due_date}</p>`
    detailedInfoDiv.appendChild(dueDateDiv)
  }

  detailedIssueDiv.appendChild(leftSideDiv)
  detailedIssueDiv.appendChild(detailedInfoDiv)

  return detailedIssueDiv
}

updateIssues()
