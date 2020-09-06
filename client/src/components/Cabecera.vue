<template>
  <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">PandEnvio</a>
    <div class="form-inline my-2 my-lg-0 mr-2">
      <button class="btn btn-outline-secondary my-2 my-sm-0 active" v-on:click="actualizarLogin('admin')">Login admin</button>
      <button class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('duenio')">Login due&ntilde;o</button>
      <button class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('cliente')">Login cliente</button>
      <button class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('repartidor')">Login repartidor</button>
    </div>
  </nav>
</template>

<script>
import { loguear } from '../services/AutenticacionService'

export default {
  name: 'Cabecera',
  methods: {
    actualizarLogin: (nuevoRol) => {
      loguear(nuevoRol)
    }
  }
}
</script>

<style scoped>

</style>
