<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.nombre }}</td>
    <td>{{ '$' + item.precio }}</td>
    <td>{{ item.categoria}}</td>
    <td>{{ item.descripcion}}</td>
    <td>{{ item.restaurant.nombre }}</td>
    <td>
      <button class='btn btn-primary' v-on:click="editar"><i class='fas fa-edit'></i> Editar</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'FilaPlato',
  props: ['item'],
  methods: {
    editar: function () {
      this.$emit('edicionPlato', this.item)
    }
  }

}
</script>

<style scoped>

</style>
