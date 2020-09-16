<template>
  <div class='modal fade' id='FormularioPlato' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
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
            <input type='hidden' id='id' name='id' v-model='plato.id'>
            <div class='form-group'>
              <label for='nombre' class='col-form-label'>Nombre:</label>
              <input type='text' class='form-control' id='nombre' v-model='plato.nombre'>
            </div>
            <div class='form-group'>
              <label for='precio' class='col-form-label'>Precio:</label>
              <input type='text' class='form-control' id='precio' v-model='plato.precio'>
            </div>
            <div class='form-group'>
              <label for='categoria' class='col-form-label'>Categoria:</label>
              <campo-select v-model="plato.categoria" :field="'una categoria'" :values="categorias" id="categoria" :elegido="plato.categoria ? plato.categoria.id : null"></campo-select>
            </div>
            <div class='form-group'>
              <label for='descripcion' class='col-form-label'>Descripcion:</label>
              <input type='text' class='form-control' id='descripcion' v-model='plato.descripcion'>
            </div>
            <div class='form-group'>
              <label for='restaurant' class='col-form-label'>Restaurant:</label>
              <campo-select v-model="plato.restaurant" :field="'un restaurant'" :values="restaurantes" id="restaurant" :elegido="plato.restaurant ? plato.restaurant.id : null"></campo-select>
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
import {getCategorias, getNombreCategoria} from '../../services/PlatoService'
export default {
  name: 'FormularioPlato',
  components: {CampoSelect},
  data: function () {
    return {
      categorias: getCategorias()
    }
  },
  props: [
    'plato',
    'restaurantes'
  ],
  computed: {
    getTitulo: function () {
      if (this.plato && this.plato.id !== 0) {
        return 'Editando Plato #' + this.plato.id
      }
      return 'Nuevo Plato'
    }
  },
  methods: {
    confirm: function () {
      this.plato.categoria = getNombreCategoria(this.plato.categoria)
      this.$emit('confirmacionPlato', this.plato)
      $$('#FormularioPlato').modal('hide')
    },
    show: () => {
      $$('#FormularioPlato').modal('show')
    }
  }
}
</script>

<style scoped>

</style>
