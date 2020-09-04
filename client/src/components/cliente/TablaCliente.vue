<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Mail</th>
        <th>Telefono</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-cliente v-for='cliente in clientes' @edicionCliente="editarCliente"
                       :item='cliente' :key='cliente.id'></fila-cliente>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaCliente from './FilaCliente'
export default {
  name: 'TablaCliente',
  components: {FilaCliente},
  props: ['clientes'],
  methods: {
    editarCliente: function (item) {
      this.$emit('edicionCliente', item)
    },
  }
}
</script>

<style scoped>

</style>
