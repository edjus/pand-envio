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
  actualizarPuntuacionPedido,
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
    cancelarPedido: function (pedido) {
      fetch(`${this.serverURL}/pedido/${pedido.id}/cancelar`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'}
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'El pedido se canceló exitosamente'
            })
            this.actualizarListadoPedidos(json.pedido)
          } else {
            console.error('Error cancelando pedido: ' + json._embedded.errors[0])
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede cancelar el pedido',
              text: json._embedded.errors[0]
            })
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    avanzarPedido: function (pedido) {
      fetch(`${this.serverURL}/pedido/${pedido.id}/siguienteEstado`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'}
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'El pedido avanzó de estado exitosamente'
            })
            this.actualizarListadoPedidos(json.pedido)
          } else {
            console.error('Error avanzando pedido: ' + json._embedded.errors[0])
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede avanzar el pedido',
              text: json._embedded.errors[0]
            })
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    actualizarListadoPedidos: function (pedido) {
      const index = this.pedidos.findIndex(x => x.id === pedido.id)
      if (index >= 0) {
        this.pedidos[index] = pedido
        this.pedidos = this.pedidos.map(r => r)
      } else {
        this.pedidos.push(pedido)
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
        console.error('Error cargando pedidos: ' + error)
        this.$notify({
          group: 'notifications',
          type: 'error',
          title: 'No se pueden cargar pedidos',
          text: error
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
