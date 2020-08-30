<template>
  <div id="app">
    <cabecera/>
    <aplicacion/>
  </div>
</template>

<script>
import Cabecera from './components/Cabecera'
import Aplicacion from './components/Aplicacion'
export default {
  name: 'App',
  components: {Aplicacion, Cabecera}
}
</script>
