<template>
  <div class='table-responsive'>
    <table class='table table-striped table-sm'>
      <thead>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Precio</th>
        <th>Restaurant</th>
        <th>Platos</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <fila-menu v-for='menu in menues' @edicionMenu="editarMenu"
                  :item='menu' :key='menu.id'></fila-menu>
      </tbody>
    </table>
  </div>
</template>

<script>
import FilaMenu from './FilaMenu'
export default {
  name: 'TablaMenu',
  components: {FilaMenu},
  props: ['menues'],
  methods: {
    editarMenu: function (item) {
      this.$emit('edicionMenu', item)
    }
  }
}
</script>

<style scoped>

</style>
