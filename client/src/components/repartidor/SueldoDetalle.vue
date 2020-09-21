<template>
  <div>
    <p>Sueldo Base: <strong>${{sueldo.sueldoBase}}</strong></p>
    <p>Adicionales por pedido: <strong>${{sueldo.adicionalPorPedidos}}</strong></p>
    <p>Adicionales por lluvia: <strong>${{sueldo.adicionalPorLluvia}}</strong></p>
    <hr style="border: 1px dashed darkgrey">
    <p class="mt-2">Sueldo final: <strong>${{sueldo.sueldoFinal}}</strong></p>
  </div>
</template>

<script>
export default {
  name: 'SueldoDetalle',
  props: ['sueldo']
}
</script>

<style scoped>

</style>
