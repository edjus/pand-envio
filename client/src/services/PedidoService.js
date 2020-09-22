import axios from 'axios'

const SERVER_URL = process.env.SERVER_URL

const actualizarPuntuacionPedido = async (data) => {
  await axios.post(`${SERVER_URL}/pedido/${data.pedido.id}/calificarPedido`, {calificacion: data.puntuacion})
}

const obtenerPuntuacionPedido = async (pedidoId) => {
  try {
    const response = await axios.get(`${SERVER_URL}/pedido/${pedidoId}/calificacion`)
    return response.data.puntuacion ? response.data.puntuacion.estrellas : null
  } catch (error) {
    console.error('Error puntuando pedido: ' + error)
  }
}

const cargarPedidosRestaurant = async (restaurantId) => {
  const response = await axios.get(`${SERVER_URL}/pedido/restaurant/${restaurantId}`)
  return response.data
}

const cargarPedidosCliente = async (clienteId) => {
  const response = await axios.get(`${SERVER_URL}/pedido/cliente/${clienteId}`)
  return response.data
}

const cargarPedidos = async () => {
  const response = await axios.get(`${SERVER_URL}/pedido`)
  return response.data
}

const denunciarPedidoNoEntregado = async (pedidoId) => {
  const response = await axios.put(`${SERVER_URL}/pedido/${pedidoId}/noEntregado`)
  return response.data
}

export {
  actualizarPuntuacionPedido, obtenerPuntuacionPedido, cargarPedidosRestaurant, cargarPedidosCliente, cargarPedidos, denunciarPedidoNoEntregado
}
