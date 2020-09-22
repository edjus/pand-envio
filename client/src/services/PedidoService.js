import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const actualizarPuntuacionPedido = async (data) => {
  try {
    await axios.post(`${SERVER_URL}/pedido/${data.pedido.id}/calificarPedido`, {calificacion: data.puntuacion})
  } catch (error) {
    console.error('Error puntuando pedido: ' + error)
  }
}

const obtenerPuntuacionPedido = async (pedidoId) => {
  try {
    const response = await axios.get(`${SERVER_URL}/pedido/${pedidoId}/calificacion`)
    return response.data.puntuacion ? response.data.puntuacion.estrellas : null
  } catch (error) {
    console.error('Error puntuando pedido: ' + error)
  }
}

export {
  actualizarPuntuacionPedido, obtenerPuntuacionPedido
}
