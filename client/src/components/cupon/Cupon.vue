<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Cupones</h1>
      <div class='btn-toolbar mb-2 mb-md-0'>
      </div>
    </div>
    <tabla-cupon :cupones='cupones'></tabla-cupon>
  </div>
</template>

<script>
import TablaCupon from './TablaCupon'

export default {
  name: 'Cupon',
  components: {TablaCupon},
  data: function () {
    return {
      cupones: [],
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarCupones()
  },
  methods: {
    clearNotifications: function () {
      this.$notify({
        group: 'notifications',
        clean: true
      })
    },
    cargarCupones: async function () {
      this.clearNotifications()
      fetch(`${this.serverURL}/cupones`)
        .then(r => r.json())
        .then(json => {
          this.cupones = json
          this.$notify({
            group: 'notifications',
            type: 'success',
            title: 'Carga de cupones exitosa'
          })
        })
        .catch(error => {
          console.error('Error cargando cupones: ' + error)
          this.$notify({
            group: 'notifications',
            type: 'error',
            title: 'No se pueden cargar cupones',
            text: error
          })
        })
    }
  }
}
</script>

<style scoped>

</style>
