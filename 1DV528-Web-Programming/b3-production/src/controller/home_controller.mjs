export const controller = {}

controller.home = async (req, res) => {
  const data = { page: 'home' }
  return res.render('layout', data)
}
