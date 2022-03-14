<template>
  <div>
    <navbar />
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import _axios from '../util/_axios'

export default {
  name: 'Home',
  components: {
    Navbar,
  },
  data() {
    return {
      input: '',
    };
  },
  methods: {
  }
};
</script>

<style></style>
