<template>
  <div class="table-responsive">
    <table class="table table-striped table-sm">
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Direccion</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-restaurant v-for="restaurante in restaurantes"
                 :item="restaurante" :key="restaurante.id"></fila-restaurant>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaRestaurant from "./FilaRestaurant";
export default {
  name: "TablaRestaurant",
  components: {FilaRestaurant},
  props: ['restaurantes']
}
</script>

<style scoped>

</style>
