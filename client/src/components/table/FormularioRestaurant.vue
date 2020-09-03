<template>
  <div class='modal fade' id='FormularioRestaurant' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
    <div class='modal-dialog' role='document'>
      <div class='modal-content'>
        <div class='modal-header'>
          <h5 class='modal-title' id='exampleModalLabel'>{{getTitulo()}}</h5>
          <button type='button' class='close' data-dismiss='modal' aria-label='Close'>
            <span aria-hidden='true'>&times;</span>
          </button>
        </div>
        <div class='modal-body'>
          <form>
            <input type='hidden' id='id' name='id' v-model='restaurant.id'>
            <div class='form-group'>
              <label for='nombre' class='col-form-label'>Nombre:</label>
              <input type='text' class='form-control' id='nombre' v-model='restaurant.nombre'>
            </div>
            <div class='form-group'>
              <label for='direccion' class='col-form-label'>Direccion:</label>
              <input type='text' class='form-control' id='direccion' v-model='restaurant.direccion'>
            </div>
          </form>
        </div>
        <div class='modal-footer'>
          <button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>
          <button type='button' class='btn btn-primary' v-on:click="confirm">Aceptar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import $$ from 'jquery'

export default {
  name: 'FormularioRestaurant',
  props: [
    'restaurant'
  ],
  methods: {
    getTitulo: () => {
      if (this.restaurant && this.restaurant.id !== 0) {
        return 'Editando Restaurante'
      }
      return 'Nuevo Restaurante'
    },
    confirm: function () {
      this.$emit('restaurantConfirmation', this.restaurant)
      $$('#FormularioRestaurant').modal('hide')
    },
    show: () => {
      $$('#FormularioRestaurant').modal('show')
    }
  }
}
</script>

<style scoped>

</style>
