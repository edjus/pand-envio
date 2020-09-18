<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Pedidos</h1>
    </div>
    <tabla-pedido :pedidos='pedidos' @avanzarPedido="avanzarPedido"></tabla-pedido>
  </div>
</template>

<script>
import TablaPedido from './TablaPedido'

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
      fetch(`${this.serverURL}/pedido`)
        .then(r => r.json())
        .then(json => {
          this.pedidos = json
          this.$notify({
            group: 'notifications',
            type: 'success',
            title: 'Carga de pedidos exitosa'
          })
        })
        .catch(error => {
          console.error('Error cargando pedidos: ' + error)
          this.$notify({
            group: 'notifications',
            type: 'error',
            title: 'No se pueden cargar pedidos',
            text: error
          })
        })
    }
  }
}
</script>

<style scoped>

</style>
