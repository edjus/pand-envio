<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Fecha</th>
        <th>Codigo</th>
        <th>Activo</th>
        <th>Cliente</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for='cupon in cupones' v-bind:class="getClass(cupon)">
        <td>{{ cupon.id }}</td>
        <td>{{ cupon.fecha }}</td>
        <td>{{ cupon.codigo }}</td>
        <td>{{ cupon.activo }}</td>
        <td>{{ cupon.cliente.nombre + ' ' + cupon.cliente.apellido}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: ['cupones'],
  name: "TablaCupon",
  methods:{
    getClass (cupon) {
      return cupon.activo ? 'activo' : 'usado'
    },
  }
}
</script>

<style scoped>
.activo {
  background-color: #ecffe9 !important;
}
.usado {
  background-color: #ffe9ec !important;
}
</style>
