import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const cargarMenuRestaurant = async (restaurantId) => {
  const response = await axios.get(`${SERVER_URL}/menu/restaurant/${restaurantId}`)
  return response.data
}

const cargarMenu = async () => {
  const response = await axios.get(`${SERVER_URL}/menu`)
  return response.data
}

const guardarMenu = async (menu) => {
  if (menu.id === 0) {
    delete menu.id
  }
  const response = await axios.post(`${SERVER_URL}/menu`, menu)
  return response.data
}

export {
  cargarMenuRestaurant,
  cargarMenu,
  guardarMenu
}
