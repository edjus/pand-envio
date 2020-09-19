<template>
  <div class='modal fade' id='FormularioMenu' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
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
            <input type='hidden' id='id' name='id' v-model='menu.id'>
            <div class='form-group'>
              <label for='nombre' class='col-form-label'>Nombre:</label>
              <input type='text' class='form-control' id='nombre' v-model='menu.nombre'>
            </div>
            <div class='form-group'>
              <label for='precio' class='col-form-label'>Precio:</label>
              <input type='text' class='form-control' id='precio' v-model='menu.precio'>
            </div>
            <div class='form-group'>
              <label for='restaurant' class='col-form-label'>Restaurant:</label>
              <campo-select v-model="menu.restaurant" :field="'un restaurant'" :values="restaurantes" id="restaurant" :elegido="menu.restaurant ? menu.restaurant.id : null"></campo-select>
            </div>
            <div class='form-group'>
              <label for='platos' class='col-form-label'>Platos:</label>
              <multiselect id="platos" v-model="value" tag-placeholder="Agregar plato" placeholder="Buscar or agregar un plato" label="name" track-by="code" :options="options" :multiple="true" :taggable="true" @tag="agregarPlato"></multiselect>
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
import Multiselect from 'vue-multiselect'

export default {
  name: 'FormularioMenu',
  components: {CampoSelect, Multiselect},
  data: function () {
    return {
      value: [
        { name: 'Javascript', code: 'js' }
      ],
      options: [
        { name: 'Vue.js', code: 'vu' },
        { name: 'Javascript', code: 'js' },
        { name: 'Open Source', code: 'os' }
      ] }
  },
  props: [
    'menu',
    'restaurantes',
    'platos'
  ],
  computed: {
    getTitulo: function () {
      if (this.menu && this.menu.id !== 0) {
        return 'Editando Menu #' + this.menu.id
      }
      return 'Nuevo Menu'
    }
  },
  methods: {
    agregarPlato (newTag) {
      const tag = {
        name: newTag,
        code: newTag.substring(0, 2) + Math.floor((Math.random() * 10000000))
      }
      this.options.push(tag)
      this.value.push(tag)
      console.log(this.value)
    },
    confirm: function () {
      this.$emit('confirmacionMenu', this.menu)
      $$('#FormularioMenu').modal('hide')
    },
    show: () => {
      $$('#FormularioMenu').modal('show')
    }
  }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<style scoped>

</style>
