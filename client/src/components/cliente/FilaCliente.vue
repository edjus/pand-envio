<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.nombre }}</td>
    <td>{{ item.apellido }}</td>
    <td>{{ item.mail }}</td>
    <td>{{ item.telefono }}</td>
    <td>
      <button class='btn btn-primary' v-on:click="editar"><i class='fas fa-edit'></i> Editar</button>
      <button class='btn btn-danger disabled'><i class='fas fa-trash-alt'></i> Eliminar</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: "FilaCliente",
  props: ['item'],
  methods: {
    editar: function () {
      this.$emit('edicionCliente', this.item)
    },
  }

}
</script>

<style scoped>

</style>
