<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Clientes</h1>
      <div class='btn-toolbar mb-2 mb-md-0'>
        <button class='btn btn-lg btn-success' v-on:click='abrirFormulario'>
          <i class='fas fa-plus'></i> Agregar
        </button>
      </div>
    </div>
    <tabla-cliente :clientes='clientes' @edicionCliente="editarCliente"></tabla-cliente>
    <formulario-cliente ref='modal' :cliente="clienteActual" @confirmacionCliente="guardarcliente"></formulario-cliente>
  </div>
</template>

<script>
import TablaCliente from "./TablaCliente";
import FormularioCliente from "./FormularioCliente";
export default {
  name: 'Clientes',
  components: {FormularioCliente, TablaCliente},
  data: function () {
    return {
      clientes: [],
      clienteActual: this.nuevoCliente(),
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.cargarclientes()
  },
  methods: {
    clearNotifications: function() {
      this.$notify({
        group: "notifications",
        clean: true
      });
    },
    editarCliente: function(cliente) {
      this.clienteActual = Object.assign({},cliente)
      this.$refs.modal.show()
    },
    nuevoCliente: function () {
      return {
        id: 0,
        nombre: '',
        apellido: '',
        mail: '',
        telefono: '',
        ubicacion: {
          calle: '',
          altura: undefined,
          pisoYDepartamento: ''
        }
      }
    },
    actualizarListadoclientes: function(cliente) {
      const index = this.clientes.findIndex(x => x.id === cliente.id);
      if (index >= 0 ) {
        this.clientes[index] = cliente
        this.clientes = this.clientes.map(r => r)
      } else {
        this.clientes.push(cliente)
      }
    },
    guardarcliente: function (cliente) {
      this.clearNotifications();
      if (cliente.id === 0) {
        delete cliente.id
      }
      fetch(`${this.serverURL}/cliente`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(cliente)
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: "notifications",
              type: "success",
              title: "Cliente guardado exitosamente",
            });
            this.actualizarListadoclientes(json.cliente)
            this.clienteActual = this.nuevoCliente()
          } else {
            console.error('Error cargando clientes: ' + json._embedded.errors[0])
            this.$notify({
              group: "notifications",
              type: "error",
              duration: 5000,
              title: "No se puede guardar el cliente",
              text: json._embedded.errors[0]
            });
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    abrirFormulario: function () {
      this.clienteActual = this.nuevoCliente()
      this.$refs.modal.show()
    },
    cargarclientes: async function () {
      this.clearNotifications();
      fetch(`${this.serverURL}/cliente`)
        .then(r => r.json())
        .then(json => {
          this.clientes = json
          this.$notify({
            group: "notifications",
            type: "success",
            title: "Carga de clientes exitosa"
          });
        })
        .catch(error => {
          console.error('Error cargando clientes: ' + error)
          this.$notify({
            group: "notifications",
            type: "error",
            title: "No se pueden cargar clientes",
            text: error
          });
        })
    }
  }
}
</script>

<style scoped>

</style>
