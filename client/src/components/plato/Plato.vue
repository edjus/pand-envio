<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Platos</h1>
      <div class='btn-toolbar mb-2 mb-md-0'>
        <button class='btn btn-lg btn-success' v-on:click='abrirFormulario'>
          <i class='fas fa-plus'></i> Agregar
        </button>
      </div>
    </div>
    <tabla-plato :platos='platos' @edicionPlato="editarPlato"></tabla-plato>
    <formulario-plato ref='modal' :plato="platoActual" :restaurantes="restaurantes" @confirmacionPlato="guardarPlato"></formulario-plato>
  </div>
</template>

<script>
import TablaPlato from './TablaPlato'
import FormularioPlato from './FormularioPlato'
import {getCategoriaDesde} from '../../services/PlatoService'

export default {
  name: 'Plato',
  components: {FormularioPlato, TablaPlato},
  data: function () {
    return {
      platos: [],
      restaurantes: [],
      platoActual: this.nuevoPlato(),
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarPlatos()
    this.cargarRestaurantes()
  },
  methods: {
    clearNotifications: function () {
      this.$notify({
        group: 'notifications',
        clean: true
      })
    },
    editarPlato: function (plato) {
      this.platoActual = Object.assign({}, plato)
      this.platoActual.categoria = getCategoriaDesde(this.platoActual.categoria)
      this.$refs.modal.show()
    },
    nuevoPlato: function () {
      return {
        id: 0,
        nombre: '',
        precio: 0,
        categoria: null,
        descripcion: '',
        restaurant: null
      }
    },
    actualizarListadoPlatos: function (plato) {
      const index = this.platos.findIndex(x => x.id === plato.id)
      if (index >= 0) {
        this.platos[index] = plato
        this.platos = this.platos.map(r => r)
      } else {
        this.platos.push(plato)
      }
    },
    guardarPlato: function (plato) {
      this.clearNotifications()
      if (plato.id === 0) {
        delete plato.id
      }
      fetch(`${this.serverURL}/plato`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(plato)
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'Plato guardado exitosamente'
            })
            this.actualizarListadoPlatos(json.plato)
            this.platoActual = this.nuevoPlato()
          } else {
            console.error('Error cargando platos: ' + json._embedded.errors[0])
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede guardar el plato',
              text: json._embedded.errors[0]
            })
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    abrirFormulario: function () {
      this.platoActual = this.nuevoPlato()
      this.$refs.modal.show()
    },
    cargarPlatos: async function () {
      this.clearNotifications()
      fetch(`${this.serverURL}/plato`)
        .then(r => r.json())
        .then(json => {
          this.platos = json
          this.$notify({
            group: 'notifications',
            type: 'success',
            title: 'Carga de platos exitosa'
          })
        })
        .catch(error => {
          console.error('Error cargando platos: ' + error)
          this.$notify({
            group: 'notifications',
            type: 'error',
            title: 'No se pueden cargar platos',
            text: error
          })
        })
    },
    cargarRestaurantes: async function () {
      fetch(`${this.serverURL}/restaurant`)
        .then(r => r.json())
        .then(json => {
          this.restaurantes = json
        })
        .catch(error => console.error('Error cargando restaurantes: ' + error))
    }
  }
}
</script>

<style scoped>

</style>
