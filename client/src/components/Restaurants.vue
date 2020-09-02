<template>
<div>
  <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
    <h1 class="h2">Restaurants</h1>
    <div class="btn-toolbar mb-2 mb-md-0">
      <button class="btn btn-lg btn-success">
        <i class="fas fa-plus"></i> Agregar
      </button>
    </div>
  </div>
  <tabla-restaurant :restaurantes="restaurantes"></tabla-restaurant>
</div>
</template>

<script>
import TablaRestaurant from "./table/TablaRestaurant";
export default {
  name: 'Restaurants',
  components: {TablaRestaurant},
  data: () => {
    return {
      restaurantes: [],
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarRestaurantes()
  },
  methods: {
    cargarRestaurantes: async function () {
      fetch(`${this.serverURL}/restaurant`)
        .then(r => r.json())
        .then(json => {
          this.restaurantes = json
          console.log(this.restaurantes)
        })
        .catch(error => console.error('Error cargando restaurantes: ' + error))
    },
  }
}
</script>

<style scoped>

</style>
