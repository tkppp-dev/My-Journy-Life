<template>
  <div class="input-container">
    <label class="label">{{ label }}</label>
    <br>
    <input class="input" :type="type" v-model="input"/>
  </div>
</template>

<script>
export default {
  name: 'Input',
  props: {
    label: String,
    input: String,
    type: String,
  },
};
</script>

<style>
.input-container {
  width: 100%;
}

.label {
  color: #a02525;
  font-weight: bold;
  margin-bottom: 3px;
}

.input {
  width: 100%;
  height: 37px;
}

input { 
  padding: 8px 8px;
  border-width: 1.5em;
  border: 1.5px solid rgba(0,0,0, 0.25);
}

input:focus {
  outline: none;
}
</style>
