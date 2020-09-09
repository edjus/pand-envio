<template>
  <div class="row">
      <div class="d-none d-lg-block col-lg-2 text-center py-2">
          <img :src="'https://www.hola.com/imagenes/cocina/recetas/2014100174000/albondigas-cordero/0-784-791/albondigas-de-cordero-con-salsa-de-tomate-m.jpg'" width="120" height="80" class="rounded" />
      </div>
      <div class="col-12 text-sm-center col-lg-5 text-lg-left">
          <h5><small>{{item.producto}}</small></h5>
      </div>
      <div class="col-12 text-sm-center col-lg-5 text-lg-right row">
          <div class="col-4 text-md-right" style="padding-top:5px;">
              <h6><strong>{{item.precio_unitario}}<span class="text-muted"> x </span>{{item.cantidad}}</strong></h6>
          </div>
          <div class="col-6 col-sm-4 col-lg-6">
              <div class="float-right mx-1">
                  <button type="submit" class="btn btn-primary" >
                      <i class="fas fa-plus"></i>
                  </button>
              </div>
              <div class="float-right mx-1">
                  <button type="submit" class="btn btn-danger" >
                      <i class="fas fa-minus"></i>
                  </button>
              </div>
          </div>
          <div class="col-2 col-sm-4 col-lg-2 text-right">
              <button type="submit" class="btn btn-outline-danger" >
                  <i class="fas fa-trash"></i>
              </button>
          </div>
      </div>
      <div></div>
  </div>
</template>

<script>
export default {
  name: 'ItemPedido',
  props: ['item']
}
</script>

<style scoped>

</style>
