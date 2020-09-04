<template>
  <div class='modal fade' id='FormularioCliente' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
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
            <input type='hidden' id='id' name='id' v-model='cliente.id'>
            <div class='form-group'>
              <label for='nombre' class='col-form-label'>Nombre:</label>
              <input type='text' class='form-control' id='nombre' v-model='cliente.nombre'>
            </div>
            <div class='form-group'>
              <label for='apellido' class='col-form-label'>Apellido:</label>
              <input type='text' class='form-control' id='apellido' v-model='cliente.apellido'>
            </div>
            <div class='form-group'>
              <label for='mail' class='col-form-label'>Mail:</label>
              <input type='text' class='form-control' id='mail' v-model='cliente.mail'>
            </div>
            <div class='form-group'>
              <label for='telefono' class='col-form-label'>Telefono:</label>
              <input type='text' class='form-control' id='ge' v-model='cliente.telefono'>
            </div>
            <div class='form-group'>
              <label for='calle' class='col-form-label'>Calle:</label>
              <input type='text' class='form-control' id='calle' v-model='cliente.ubicacion.calle'>
            </div>
            <div class='form-group'>
              <label for='calle' class='col-form-label'>altura:</label>
              <input type='text' class='form-control' id='altura' v-model='cliente.ubicacion.altura'>
            </div>
            <div class='form-group'>
              <label for='pisoYDepartamento' class='col-form-label'>Piso y Departamento:</label>
              <input type='text' class='form-control' id='pisoYDepartamento' v-model='cliente.ubicacion.pisoYDepartamento'>
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
  name: 'FormularioCliente',
  props: [
    'cliente'
  ],
  computed: {
    getTitulo: function () {
      if (this.cliente && this.cliente.id !== 0) {
        return 'Editando Cliente #' + this.cliente.id
      }
      return 'Nuevo Cliente'
    }
  },
  methods: {
    confirm: function () {
      this.$emit('confirmacionCliente', this.cliente)
      $$('#FormularioCliente').modal('hide')
    },
    show: () => {
      $$('#FormularioCliente').modal('show')
    }
  }
}
</script>

<style scoped>

</style>
