<template>
  <div id="app">
    <cabecera/>
    <notifications group="notifications" position="bottom right" />
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
