
import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const obtenerPlatos = async () => {
  try {
    const response = await axios.get(`${SERVER_URL}/plato`)
    return response.data
  } catch (error) {
    console.error('Error cargando productos: ' + error)
  }
}

export { obtenerPlatos }
