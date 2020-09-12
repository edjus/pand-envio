<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.nombre }}</td>
    <td>{{ item.dni }}</td>
    <td>{{ item.disponible ? 'SI' : 'NO'}}</td>
    <td>{{ item.restaurant.nombre }}</td>
    <td>
      <button class='btn btn-primary' v-on:click="editar"><i class='fas fa-edit'></i> Editar</button>
      <button class='btn btn-danger' v-on:click="eliminar"><i class='fas fa-trash-alt'></i> Eliminar</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'FilaRepartidor',
  props: ['item'],
  methods: {
    editar: function () {
      this.$emit('edicionRepartidor', this.item)
    },
    eliminar: function () {
      this.$emit('eliminarRepartidor', this.item)
    }
  }

}
</script>

<style scoped>

</style>
