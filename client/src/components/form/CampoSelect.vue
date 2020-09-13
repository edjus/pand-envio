<template>
  <div class="form-group">
    <select class="form-control" v-model="selected" @change="updateValue()">
      <option disabled :value="null">Seleccione {{field}}</option>
      <option v-for="value in values" :value="value.id" :key="value.id">
        {{ value.nombre }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  name: 'CampoSelect',
  props: ['values', 'field', 'elegido'],
  data: function () {
    return {
      selected: null
    }
  },
  watch: {
    elegido: function (newVal, oldVal) {
      if (newVal !== undefined) {
        this.selected = newVal
      }
    }
  },
  methods: {
    updateValue: function () {
      this.$emit('input', this.selected)
    }
  }
}
</script>
