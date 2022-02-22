<!--
Modal component with customizable slots and CSS transitions.
-->

<script>
import Home from '@/views/Home.vue'
import Modal from '@/components/SearchModal.vue';

export default {
  components: {
    Modal,
    Home
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
    <home />
  </div>
</template>

<style>
@font-face {
    font-family: 'multicolore_regular';
    src: url('./assets/fonts/multicolore-webfont.woff2') format('woff2'),
         url('./assets/fonts/multicolore-webfont.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>