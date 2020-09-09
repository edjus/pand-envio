<template>
  <div class="container-fluid">
    <div class="row">
      <botonera/>
      <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <router-view @actualizarItems="actualizarItems"/>
      </main>
    </div>
  </div>

</template>

<script>
import Botonera from './Botonera'
export default {
  name: 'Aplicacion',
  components: {Botonera},
  methods: {
    actualizarItems () {
      this.$emit('actualizarItems')
    }
  }
}
</script>

<style scoped>

</style>
