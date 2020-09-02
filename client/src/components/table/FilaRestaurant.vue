<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.nombre }}</td>
    <td>{{ item.direccion }}</td>
    <td>
      <button class="btn btn-primary"><i class="fas fa-edit"></i> Editar</button>
      <button class="btn btn-danger"><i class="fas fa-trash-alt"></i> Eliminar</button>

    </td>
  </tr>
</template>

<script>
export default {
  name: "FilaRestaurant",
  props: ['item']
}
</script>

<style scoped>

</style>
