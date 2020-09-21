import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const getSueldo = async (repartidor) => {
  try {
    const response = await axios.get(`${SERVER_URL}/repartidor/sueldo/${repartidor.id}`)
    return response.data.sueldo
  } catch (error) {
    console.error('Error cargando sueldo repartidor: ' + error)
  }
}

const getRepartidorById = async (repartidorId) => {
  try {
    const response = await axios.get(`${SERVER_URL}/repartidor/${repartidorId}`)
    return response.data
  } catch (error) {
    console.error('Error cargando repartidor: ' + error)
  }
}

export {
  getSueldo, getRepartidorById
}
