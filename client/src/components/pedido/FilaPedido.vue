<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.fecha }}</td>
    <td>{{item.cliente.nombre }}</td>
    <td>{{ item.estado}}</td>
    <td>{{ item.modalidad}}</td>
    <td>{{ '$' + item.precio}}</td>
    <td>{{ item.restaurant.nombre }}</td>
    <td>
      <button class='btn btn-primary' v-on:click="avanzar"><i class='fas fa-arrow-circle-right'></i> Avanzar estado</button>
      <button class='btn btn-danger' v-on:click="cancelar"><i class='fas fa-window-close'></i> Cancelar</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'FilaPedido',
  props: ['item'],
  methods: {
    avanzar: function () {
      this.$emit('avanzarPedido', this.item)
    },
    cancelar: function () {
      this.$emit('cancelarPedido', this.item)
    }
  }

}
</script>

<style scoped>

</style>
