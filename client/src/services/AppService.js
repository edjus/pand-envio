
import axios from 'axios'
import { idUsuarioActual } from './AutenticacionService'

const SERVER_URL = process.env.SERVER_URL
const MODALIDAD_DEFECTO = 'para_retirar'
const CANTIDAD_DEFECTO = 1

const obtenerPlatos = async () => {
  try {
    const response = await axios.get(`${SERVER_URL}/plato`)
    return response.data
  } catch (error) {
    console.error('Error cargando productos: ' + error)
  }
}

const crearPedido = async (restaurantId) => {
  if (localStorage.getItem('pedidoActual')) {
    return
  }
  const clienteId = idUsuarioActual()
  const data = { cliente_id: clienteId, restaurant_id: restaurantId, modalidad: MODALIDAD_DEFECTO }
  const response = await axios.post(`${SERVER_URL}/pedido`, data)

  localStorage.setItem('pedidoActual', response.data.pedido.id)
}

const agregarItem = async (productoId, restaurantId) => {
  await crearPedido(restaurantId)
  const pedidoId = localStorage.getItem('pedidoActual')
  const data = { producto_id: productoId, cantidad: CANTIDAD_DEFECTO }
  const response = await axios.post(`${SERVER_URL}/pedido/${pedidoId}/item`, data)

  localStorage.setItem('cantidadItems', response.data.pedido.items.length)
}

const obtenerPedidoActual = async () => {
  try {
    const clienteId = idUsuarioActual()
    const response = await axios.get(`${SERVER_URL}/pedido/actual/${clienteId}`)
    localStorage.setItem('cantidadItems', response.data.pedido.items.length)
    localStorage.setItem('pedidoActual', response.data.pedido.id)
    return response.data.pedido
  } catch (error) {
    localStorage.removeItem('cantidadItems')
    localStorage.removeItem('pedidoActual')
    console.log(`error: ${error}`)
    return null
  }
}

const confirmarPedido = async (pedidoId) => {
  await axios.put(`${SERVER_URL}/pedido/${pedidoId}/siguienteEstado`)
  location.reload()
}

const removerProducto = async (pedidoId, productoId) => {
  try {
    await axios.delete(`${SERVER_URL}/pedido/${pedidoId}/producto/${productoId}`)
    location.reload()
  } catch (error) {
    console.log(error)
  }
}

export { obtenerPlatos, crearPedido, agregarItem, obtenerPedidoActual, confirmarPedido, removerProducto }
