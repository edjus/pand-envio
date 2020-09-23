import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const cargarRestaurantes = async (restauranteId) => {
  try {
    let url = `${SERVER_URL}/restaurant`
    if (restauranteId) {
      url += '/' + restauranteId
    }
    const response = await axios.get(url)
    if (restauranteId) {
      return [response.data]
    }
    return response.data
  } catch (error) {
    console.error('Error cargando restaurante: ' + error)
  }
}

export {cargarRestaurantes}
