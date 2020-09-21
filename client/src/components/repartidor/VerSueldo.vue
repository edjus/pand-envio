<template>
  <div class="jumbotron">
    <h1 class="display-4">Resumen salarial de {{repartidor.nombre}}</h1>
    <sueldo-detalle :sueldo="sueldo"></sueldo-detalle>
  </div>
</template>

<script>
import {getRepartidorById, getSueldo} from '../../services/RepartidorService'
import {idRepartidorActual} from '../../services/AutenticacionService'
import SueldoDetalle from './SueldoDetalle'

export default {
  name: 'VerSueldo',
  components: {SueldoDetalle},
  data: function () {
    return {
      repartidor: {},
      sueldo: {}
    }
  },
  async created () {
    const repartidorId = idRepartidorActual()
    this.repartidor = await getRepartidorById(repartidorId)
    this.sueldo = await getSueldo(this.repartidor)
  }
}
</script>

<style scoped>

</style>
