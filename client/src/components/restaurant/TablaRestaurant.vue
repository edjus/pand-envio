<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Direccion</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-restaurant v-for='restaurante in restaurantes' @restaurantEdicion="editarRestaurant"
                 :item='restaurante' :key='restaurante.id'></fila-restaurant>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaRestaurant from './FilaRestaurant'
export default {
  name: 'TablaRestaurant',
  components: {FilaRestaurant},
  props: ['restaurantes'],
  methods: {
    editarRestaurant: function (item) {
      this.$emit('restaurantEdicion', item)
    }
  }
}
</script>

<style scoped>

</style>
