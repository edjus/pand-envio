<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Repartidores</h1>
      <div class='btn-toolbar mb-2 mb-md-0'>
        <button class='btn btn-lg btn-success' v-on:click='abrirFormulario'>
          <i class='fas fa-plus'></i> Agregar
        </button>
      </div>
    </div>
    <tabla-repartidor :repartidores='repartidores' @edicionRepartidor="editarRepartidor" @eliminarRepartidor="eliminarRepartidor" @verSueldo="verSueldo"></tabla-repartidor>
    <sueldo-repartidor ref='modalSueldo' :repartidor="repartidorActual" :sueldo="sueldo"></sueldo-repartidor>
    <formulario-repartidor ref='modal' :repartidor="repartidorActual" :restaurantes="restaurantes" @confirmacionRepartidor="guardarRepartidor"></formulario-repartidor>
  </div>
</template>

<script>
import TablaRepartidor from './TablaRepartidor'
import FormularioRepartidor from './FormularioRepartidor'
import {getSueldo} from '../../services/RepartidorService'
import SueldoRepartidor from './SueldoRepartidor'
import {getRestauranteIdLogueado} from '../../services/AutenticacionService'
import {cargarRestaurantes} from '../../services/RestaurantService'

export default {
  name: 'Repartidor',
  components: {SueldoRepartidor, FormularioRepartidor, TablaRepartidor},
  data: function () {
    return {
      repartidores: [],
      restaurantes: [],
      sueldo: {},
      repartidorActual: this.nuevoRepartidor(),
      serverURL: process.env.SERVER_URL
    }
  },
  async created () {
    await this.cargarRepartidores(getRestauranteIdLogueado())
    this.restaurantes = await cargarRestaurantes(getRestauranteIdLogueado())
  },
  methods: {
    clearNotifications: function () {
      this.$notify({
        group: 'notifications',
        clean: true
      })
    },
    verSueldo: async function (repartidor) {
      this.sueldo = await getSueldo(repartidor)
      this.repartidorActual = Object.assign({}, repartidor)
      this.$refs.modalSueldo.show()
    },
    editarRepartidor: function (repartidor) {
      this.repartidorActual = Object.assign({}, repartidor)
      this.$refs.modal.show()
    },
    eliminarRepartidor: function (repartidor) {
      this.clearNotifications()
      fetch(`${this.serverURL}/repartidor/${repartidor.id}`, {
        method: 'DELETE'
      })
        .then(res => res.json())
        .then(json => {
          if (json.error) {
            console.error('Error borrando repartidor: ' + json.message)
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede borrar el repartidor',
              text: json.message
            })
          } else {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'Repartidor borrado exitosamente'
            })
            this.borrarDelListadoDeRepartidores(repartidor)
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    nuevoRepartidor: function () {
      return {
        id: 0,
        nombre: '',
        dni: 0,
        disponible: true,
        restaurant: null
      }
    },
    borrarDelListadoDeRepartidores: function (repartidor) {
      this.repartidores = this.repartidores.filter(r => r.id !== repartidor.id)
    },
    actualizarListadoRepartidores: function (repartidor) {
      const index = this.repartidores.findIndex(x => x.id === repartidor.id)
      if (index >= 0) {
        this.repartidores[index] = repartidor
        this.repartidores = this.repartidores.map(r => r)
      } else {
        this.repartidores.push(repartidor)
      }
    },
    guardarRepartidor: function (repartidor) {
      this.clearNotifications()
      if (repartidor.id === 0) {
        delete repartidor.id
      }
      fetch(`${this.serverURL}/repartidor`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(repartidor)
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'Repartidor guardado exitosamente'
            })
            this.actualizarListadoRepartidores(json.repartidor)
            this.repartidorActual = this.nuevoRepartidor()
          } else {
            console.error('Error cargando repartidores: ' + json._embedded.errors[0])
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede guardar el repartidor',
              text: json._embedded.errors[0]
            })
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    abrirFormulario: function () {
      this.repartidorActual = this.nuevoRepartidor()
      this.$refs.modal.show()
    },
    cargarRepartidores: async function (restauranteId) {
      this.clearNotifications()
      let url = `${this.serverURL}/repartidor`
      if (restauranteId) {
        url += '/restaurant/' + restauranteId
      }
      fetch(url)
        .then(r => r.json())
        .then(json => {
          this.repartidores = json
          this.$notify({
            group: 'notifications',
            type: 'success',
            title: 'Carga de repartidores exitosa'
          })
        })
        .catch(error => {
          console.error('Error cargando repartidores: ' + error)
          this.$notify({
            group: 'notifications',
            type: 'error',
            title: 'No se pueden cargar repartidores',
            text: error
          })
        })
    }
  }
}
</script>

<style scoped>

</style>
