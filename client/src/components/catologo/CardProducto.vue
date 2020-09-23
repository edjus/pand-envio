<template>
    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <div class="d-none d-lg-block text-center py-2">
          <img :src="producto.imagen" width="120" height="80" class="rounded" />
        </div>
        <h5 class="card-title">{{ producto.nombre }} - {{ producto.restaurant.nombre }}</h5>
        <h6 class="card-subtitle mb-2 text-muted">{{ producto.descripcion }}</h6>
        <p class="card-text">Precio: {{ producto.precio }}</p>
        <button class="btn btn-secondary" @click="agregarProducto">Agregar</button>
      </div>
    </div>
</template>

<script>

export default {
  name: 'CardProducto',
  props: ['producto'],
  methods: {
    agregarProducto () {
      this.$emit('agregarProducto', this.producto)
    }
  }
}
</script>

<style scoped>
</style>
