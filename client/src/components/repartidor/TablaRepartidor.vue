<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>DNI</th>
        <th>Disponible</th>
        <th>Restaurant</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-repartidor v-for='repartidor in repartidores' @edicionRepartidor="editarRepartidor" @eliminarRepartidor="eliminarRepartidor" @verSueldo="verSueldo"
                    :item='repartidor' :key='repartidor.id'></fila-repartidor>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaRepartidor from './FilaRepartidor'
export default {
  name: 'TablaRepartidor',
  components: {FilaRepartidor},
  props: ['repartidores'],
  methods: {
    editarRepartidor: function (item) {
      this.$emit('edicionRepartidor', item)
    },
    eliminarRepartidor: function (item) {
      this.$emit('eliminarRepartidor', item)
    },
    verSueldo: function (item) {
      this.$emit('verSueldo', item)
    }
  }
}
</script>

<style scoped>

</style>
