<template>
<div>
  <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
    <h1 class='h2'>Restaurants</h1>
    <div class='btn-toolbar mb-2 mb-md-0'>
      <button class='btn btn-lg btn-success' v-on:click='abrirFormulario'>
        <i class='fas fa-plus'></i> Agregar
      </button>
    </div>
  </div>
  <tabla-restaurant :restaurantes='restaurantes'></tabla-restaurant>
  <formulario-restaurant
    ref='modal'
    :restaurant='restauranteActual'
    @restaurantConfirmation='guardarRestaurante'>
  </formulario-restaurant>
</div>
</template>

<script>
import TablaRestaurant from './table/TablaRestaurant'
import FormularioRestaurant from './table/FormularioRestaurant'
export default {
  name: 'Restaurants',
  components: {FormularioRestaurant, TablaRestaurant},
  data: function () {
    return {
      restaurantes: [],
      restauranteActual: {id: 0, nombre: '', direccion: ''},
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarRestaurantes()
  },
  methods: {
    nuevoRestauranteActual: function () {
      return {
        id: 0,
        nombre: '',
        direccion: ''
      }
    },
    guardarRestaurante: function (restaurante) {
      if (restaurante.id === 0) {
        delete restaurante.id
      }
      console.log(restaurante)
      fetch(`${this.serverURL}/restaurant`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(restaurante)
      }).then(r => r.json())
        .then(json => {
          this.restaurantes.push(json.restaurant)
          this.restauranteActual = this.nuevoRestauranteActual()
        })
        .catch(ex => console.error('No es posible guardar el restaurant', ex))
    },
    abrirFormulario: function () {
      this.restauranteActual = this.nuevoRestauranteActual()
      this.$refs.modal.show()
    },
    cargarRestaurantes: async function () {
      fetch(`${this.serverURL}/restaurant`)
        .then(r => r.json())
        .then(json => {
          this.restaurantes = json
        })
        .catch(error => console.error('Error cargando restaurantes: ' + error))
    }
  }
}
</script>

<style scoped>

</style>
