<template>
  <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">PandEnvio</a>
    <div class="form-inline my-2 my-lg-0 mr-2">
      <button id="btnadmin" class="btn btn-outline-secondary my-2 my-sm-0" v-on:click="actualizarLogin('admin')">Login admin</button>
      <button id="btnduenio" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('duenio')">Login due&ntilde;o</button>
      <button id="btncliente" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('cliente')">Login cliente</button>
      <button id="btnrepartidor" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" v-on:click="actualizarLogin('repartidor')">Login repartidor</button>
    </div>
  </nav>
</template>

<script>
import { loguear, rolActual } from '../services/AutenticacionService'
import $$ from 'jquery'

export default {
  name: 'Cabecera',
  methods: {
    actualizarLogin: (nuevoRol) => {
      loguear(nuevoRol)
    }
  },
  mounted: () => {
    const rol = rolActual()
    $$(`#btn${rol}`).addClass('active')
  }
}
</script>

<style scoped>

</style>
