<template>
  <div class="container-fluid">
    <div class="row">
      <botonera/>
      <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
        <router-view/>
      </main>
    </div>
  </div>

</template>

<script>
import Botonera from './Botonera'
export default {
  name: 'Aplicacion',
  components: {Botonera}
}
</script>

<style scoped>

</style>
