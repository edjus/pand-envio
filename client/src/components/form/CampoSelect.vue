<template>
  <div class="form-group">
    <select class="form-control" v-model="selected" @change="updateValue()">
      <option disabled :value="null">Select a {{field}}</option>
      <option v-for="value in values" :value="value.id" :key="value.id">
        {{ value.nombre }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  name: 'CampoSelect',
  props: ['values', 'field'],
  data: function () {
    return {
      selected: null
    }
  },
  methods: {
    updateValue: function () {
      this.$emit('input', this.selected)
    }
  }
}
</script>
