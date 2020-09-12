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
      <tr v-for='cupon in cupones'>
        <td>{{ cupon.id }}</td>
        <td>{{ cupon.Fecha }}</td>
        <td>{{ cupon.Activo }}</td>
        <td>{{ cupon.cliente.nombre + ' ' + cupon.cliente.apellido}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: ['cupones'],
  name: "TablaCupon"
}
</script>

<style scoped>

</style>
