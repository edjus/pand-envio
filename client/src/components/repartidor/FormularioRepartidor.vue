<template>
  <div class='modal fade' id='FormularioRepartidor' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
    <div class='modal-dialog' role='document'>
      <div class='modal-content'>
        <div class='modal-header'>
          <h5 class='modal-title' id='exampleModalLabel'>{{getTitulo}}</h5>
          <button type='button' class='close' data-dismiss='modal' aria-label='Close'>
            <span aria-hidden='true'>&times;</span>
          </button>
        </div>
        <div class='modal-body'>
          <form>
            <input type='hidden' id='id' name='id' v-model='repartidor.id'>
            <div class='form-group'>
              <label for='nombre' class='col-form-label'>Nombre:</label>
              <input type='text' class='form-control' id='nombre' v-model='repartidor.nombre'>
            </div>
            <div class='form-group'>
              <label for='dni' class='col-form-label'>DNI:</label>
              <input type='text' class='form-control' id='dni' v-model='repartidor.dni'>
            </div>
            <div class='form-group'>
              <label for='restaurant' class='col-form-label'>Restaurant:</label>
              <campo-select v-model="repartidor.restaurant" :field="'un restaurant'" :values="restaurantes" id="restaurant" :elegido="repartidor.restaurant ? repartidor.restaurant.id : null"></campo-select>
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
import CampoSelect from '../form/CampoSelect'

export default {
  name: 'FormularioRepartidor',
  components: {CampoSelect},
  props: [
    'repartidor',
    'restaurantes'
  ],
  computed: {
    getTitulo: function () {
      if (this.repartidor && this.repartidor.id !== 0) {
        return 'Editando Repartidor #' + this.repartidor.id
      }
      return 'Nuevo Repartidor'
    }
  },
  methods: {
    confirm: function () {
      this.$emit('confirmacionRepartidor', this.repartidor)
      $$('#FormularioRepartidor').modal('hide')
    },
    show: () => {
      $$('#FormularioRepartidor').modal('show')
    }
  }
}
</script>

<style scoped>

</style>
