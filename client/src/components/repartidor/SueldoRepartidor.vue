<template>
  <div class='modal fade' id='SueldoRepartidor' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Resumen sueldo {{repartidor.nombre}}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <sueldo-detalle :sueldo="sueldo"></sueldo-detalle>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $$ from 'jquery'
import SueldoDetalle from './SueldoDetalle'

export default {
  name: 'SueldoRepartidor',
  components: {SueldoDetalle},
  props: [
    'repartidor',
    'sueldo'
  ],
  methods: {
    show: () => {
      $$('#SueldoRepartidor').modal('show')
    }
  }
}
</script>

<style scoped>

</style>
