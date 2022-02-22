<template>
  <Transition name="modal">
    <div
      v-if="show"
      class="modal-wrapper"
      @click.prevent="$emit('close')"
    >
      <slot name="modal-content"> default content </slot>
    </div>
  </Transition>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
    };
  },
  props: {
    show: Boolean,
  },
};
</script>

<style>
.modal-wrapper {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  transition: opacity 0.3s ease;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
