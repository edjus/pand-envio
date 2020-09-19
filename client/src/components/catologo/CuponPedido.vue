<template>
    <div class="col-12 col-md-5">
      <div class="row">
        <div class="col-7">
        </div>
        <span><strong>Cupón</strong></span>
        <div class="col-8">
          <input v-model="cupon.codigo" type="text" class="form-control" placeholder="código de cupón"/>
        </div>
        <div class="col-4" style="margin-top:2px">
          <span v-if="!esCuponInvalido"><small>-{{cupon.porcentaje}}%</small></span>
          <button type="submit" class="btn btn-sm btn-outline-success form-control" v-if="esCuponInvalido" @click="agregarCupon">
            Aplicar
          </button>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  name: 'CuponPedido',
  props: ['cupon'],
  methods: {
    agregarCupon () {
      this.$emit('aplicarCupon', this.cupon.codigo)
    },
    esCuponInvalido () {
      return this.cupon.codigo === 'NULO'
    }
  }
}
</script>

<style scoped>

</style>
