<template>
  <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">PandEnvio</a>
    <a class="btn btn-secondary pull-left active" href="/pedido" v-show="esRol('cliente')" title="Pedido actual">
      <i class="fas fa-shopping-cart"></i> ({{ cantidadItems}})
      </a>
    <div class="form-inline my-2 my-lg-0 mr-2">
      <span class="clima mr-5"> Clima:  <em>{{clima}}</em></span>
      <button id="btnadmin" class="btn btn-outline-secondary my-2 my-sm-0" @click="actualizarLogin('admin')">Login admin</button>
      <button id="btnduenio" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" @click="actualizarLogin('duenio')">Login due&ntilde;o</button>
      <button id="btncliente" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" @click="actualizarLogin('cliente')">Login cliente</button>
      <button id="btnrepartidor" class="btn btn-outline-secondary my-2 my-sm-0 ml-2" @click="actualizarLogin('repartidor')">Login repartidor</button>
    </div>
  </nav>
</template>

<script>
import { loguear, rolActual, esRol } from '../services/AutenticacionService'
import $$ from 'jquery'
import * as AppService from "../services/AppService";
import {getClima} from "../services/AppService";

export default {
  name: 'Cabecera',
  props: ['cantidadItems'],
  data () {
    return {
      esCliente: true,
      clima: '',
    }
  },
  methods: {
    actualizarLogin (nuevoRol) {
      loguear(nuevoRol)
    },
    esRol (rol) {
      return esRol(rol)
    }
  },
  mounted: function() {
    const rol = rolActual()
    $$(`#btn${rol}`).addClass('active')
    const self = this
    getClima().then(function(clima) {
      self.clima = clima
    })
    this.$root.$on('nuevoClima',  function (nuevoClima) {
      self.clima = nuevoClima
    });
  }
}
</script>

<style scoped>
.clima {
  color: #dee2e6;
  font-weight: bold;
  font-size: 1.3em;
  font-family: "Amazon Ember";
}
</style>
