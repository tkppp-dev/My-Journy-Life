<template>
  <div class="input-wrapper">
    <label class="label">{{ label }}</label>
    <br>
    <input class="input" :type="type" v-model="inputData" :maxlength="maxlength" @input="onChangeInput"/>
  </div>
</template>

<script>
export default {
  name: 'Input',
  props: {
    label: String,
    type: String,
    maxlength: String,
  },
  data() {
    return {
      inputData: ''
    }
  },
  methods: {
    onChangeInput(){
      this.$emit('onChangeText', this.inputData)
    }
  }
};
</script>

<style>
.input-wrapper {
  width: 100%;
}

.label {
  color: #a02525;
  font-weight: bold;
}

.input {
  width: 100%;
  height: 37px;
  box-sizing: border-box;
  margin-top: 4px;
  padding: 0 8px;

  border-width: 1.5em;
  border: 1px solid rgba(0,0,0, 0.1);
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
}

.input:focus {
  outline: none;
}
</style>
