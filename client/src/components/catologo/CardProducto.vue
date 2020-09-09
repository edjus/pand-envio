<template>
    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">{{ producto.nombre }} - {{ producto.id }}</h5>
        <h6 class="card-subtitle mb-2 text-muted">{{ producto.descripcion }}</h6>
        <p class="card-text">Precio: {{ producto.precio }}</p>
        <a href="#" class="card-link">Agregar</a>
      </div>
    </div>
</template>

<script>
export default {
  name: 'Dashboard',
  props: ['producto']
}
</script>

<style scoped>
</style>
