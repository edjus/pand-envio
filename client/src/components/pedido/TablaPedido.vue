<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Fecha</th>
        <th>Cliente</th>
        <th>Estado</th>
        <th>Modalidad</th>
        <th>Precio</th>
        <th>Restaurant</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-pedido v-for='pedido in pedidos' @avanzarPedido="avanzarPedido"
                  :item='pedido' :key='pedido.id'></fila-pedido>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaPedido from './FilaPedido'
export default {
  name: 'TablaPedido',
  components: {FilaPedido},
  props: ['pedidos'],
  methods: {
    avanzarPedido: function (item) {
      this.$emit('avanzarPedido', item)
    }
  }
}
</script>

<style scoped>

</style>
