<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.nombre }}</td>
    <td>{{ '$' + item.precio }}</td>
    <td>{{ item.restaurant.nombre }}</td>
    <td>{{getPlatos}}</td>
    <td>
      <button class='btn btn-primary' v-on:click="editar"><i class='fas fa-edit'></i> Editar</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'FilaMenu',
  props: ['item'],
  computed: {
    getPlatos () {
      if (this.item.platos.length === 0) {
        return 'no hay platos'
      }
      return this.item.platos.map(plato => plato.nombre).join(' | ')
    }
  },
  methods: {
    editar: function () {
      this.$emit('edicionMenu', this.item)
    }
  }
}
</script>

<style scoped>

</style>
