<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Categoria</th>
        <th>Descripcion</th>
        <th>Restaurant</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-plato v-for='plato in platos' @edicionPlato="editarPlato"
                       :item='plato' :key='plato.id'></fila-plato>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaPlato from './FilaPlato'
export default {
  name: 'TablaPlato',
  components: {FilaPlato},
  props: ['platos'],
  methods: {
    editarPlato: function (item) {
      this.$emit('edicionPlato', item)
    }
  }
}
</script>

<style scoped>

</style>
