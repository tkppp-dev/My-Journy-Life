<template>
  <button class="button" type="button" @click.stop="onClickButton" :disabled="disabled">{{ label }}</button>
</template>

<script>
export default {
  name: 'CustomButton',
  props: {
    label: String,
    disabled: {
      type: Boolean,
      default: false
    },
  },
  methods: {
    onClickButton(){
      this.$emit('onClickButton')
    }
  }
}
</script>

<style>
.button {
  display: block;
  width: 100%;
  height: 37px;
  margin-bottom: 24px;
  background-color: #e2d8be;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
  
  font-size: 18px;;
  font-weight: bold;
  color: #a02525;
}

.button:hover {
  opacity: 0.8;
}

.button:disabled {
  cursor: default;
  opacity: 0.5;
}
</style>