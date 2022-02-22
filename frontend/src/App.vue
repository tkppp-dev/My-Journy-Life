<!--
Modal component with customizable slots and CSS transitions.
-->

<script>
import Modal from '@/components/SearchModal.vue';

export default {
  components: {
    Modal,
  },
  data() {
    return {
      showModal: false,
    };
  },
};
</script>

<template>
  <div>
    <button id="show-modal" @click="showModal = true">Show Modal</button>

    <teleport to="body">
      <!-- use the modal component, pass in the prop -->
      <modal :show="showModal" @close="showModal = false">
        <template #header>
          <h3>custom header</h3>
        </template>
      </modal>
    </teleport>
  </div>
</template>
