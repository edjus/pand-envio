<template>
  <div id="app">
    <cabecera :cantidadItems="cantidadItems"/>
    <notifications group="notifications" position="bottom right" />
    <aplicacion @actualizarItems="actualizarItems"/>
  </div>
</template>

<script>
import Cabecera from './components/Cabecera'
import Aplicacion from './components/Aplicacion'
import * as AppService from './services/AppService'
import { esRol } from './services/AutenticacionService'

export default {
  name: 'App',
  data () {
    return {
      cantidadItems: 0,
      clima: ''
    }
  },
  components: {Aplicacion, Cabecera},
  methods: {
    actualizarItems () {
      this.cantidadItems = localStorage.getItem('cantidadItems')
    }
  },
  async mounted () {
    if (esRol('cliente')) {
      AppService.obtenerPedidoActual()
      this.cantidadItems = localStorage.getItem('cantidadItems')
    }
  }
}
</script>
