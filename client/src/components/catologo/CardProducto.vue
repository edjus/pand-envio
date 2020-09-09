<template>
    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">{{ producto.nombre }} - {{ producto.restaurant.id }}</h5>
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
