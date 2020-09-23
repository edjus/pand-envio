<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Pedidos</h1>
    </div>
    <tabla-pedido :pedidos='pedidos' @avanzarPedido="avanzarPedido" @cancelarPedido="cancelarPedido" @actualizarPuntuacion="actualizarPuntuacion" @noEntregado="noEntregado"></tabla-pedido>
  </div>
</template>

<script>
import TablaPedido from './TablaPedido'
import {getRestauranteIdLogueado, idRepartidorActual, idUsuarioActual} from '../../services/AutenticacionService'
import {
  actualizarPuntuacionPedido, avanzarPedido, cancelarPedido,
  cargarPedidos, cargarPedidosRepartidor,
  cargarPedidosRestaurant,
  denunciarPedidoNoEntregado
} from '../../services/PedidoService'
export default {
  name: 'Pedido',
  components: {TablaPedido},
  data: function () {
    return {
      pedidos: [],
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarPedidos()
  },
  methods: {
    clearNotifications: function () {
      this.$notify({
        group: 'notifications',
        clean: true
      })
    },
    actualizarPuntuacion: async function (data) {
      try {
        await actualizarPuntuacionPedido(data)
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'El pedido se puntuó exitosamente con ' + data.puntuacion
        })
      } catch (error) {
        this.$notify({
          group: 'notifications',
          type: 'error',
          duration: 5000,
          title: 'No se pudo puntuar el pedido',
          text: error.response.data
        })
      }
    },
    noEntregado: async function (pedido) {
      try {
        await denunciarPedidoNoEntregado(pedido.id)
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'El pedido se denunció como no entregado exitosamente'
        })
      } catch (error) {
        this.$notify({
          group: 'notifications',
          type: 'error',
          duration: 5000,
          title: 'No se puede denunciar el pedido como no entregado',
          text: error.response.data
        })
      }
    },
    cancelarPedido: async function (pedido) {
      try {
        const response = await cancelarPedido(pedido.id)
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'El pedido se canceló exitosamente'
        })
        this.actualizarListadoPedidos(response.pedido)
      } catch (error) {
        this.$notify({
          group: 'notifications',
          type: 'error',
          duration: 5000,
          title: 'No se pudo cancelar el pedido',
          text: error.response.data
        })
      }
    },
    avanzarPedido: async function (pedido) {
      try {
        const response = await avanzarPedido(pedido.id)
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'El pedido avanzó de estado exitosamente'
        })
        this.actualizarListadoPedidos(response.pedido)
      } catch (error) {
        this.$notify({
          group: 'notifications',
          type: 'error',
          duration: 5000,
          title: 'No se pudo avanzar el pedido',
          text: error.response.data
        })
      }
    },
    actualizarListadoPedidos: function (pedido) {
      if (pedido) {
        const index = this.pedidos.findIndex(x => x.id === pedido.id)
        if (index >= 0) {
          this.pedidos[index] = pedido
          this.pedidos = this.pedidos.map(r => r)
        } else {
          this.pedidos.push(pedido)
        }
      }
    },
    cargarPedidos: async function () {
      this.clearNotifications()
      const restauranteId = getRestauranteIdLogueado()
      const clienteId = idUsuarioActual()
      const repartidorId = idRepartidorActual()
      try {
        if (restauranteId) {
          this.pedidos = await cargarPedidosRestaurant(restauranteId)
        } else if (clienteId) {
          this.pedidos = await cargarPedidosRestaurant(clienteId)
        } else if (repartidorId) {
          this.pedidos = await cargarPedidosRepartidor(repartidorId)
        } else {
          this.pedidos = await cargarPedidos()
        }
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'Carga de pedidos exitosa'
        })
      } catch (error) {
        this.$notify({
          group: 'notifications',
          type: 'error',
          title: 'No se pueden cargar pedidos',
          text: error.response.data
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
