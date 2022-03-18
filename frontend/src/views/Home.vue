<template>
  <div>
    <navbar />
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';

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

<style>
</style>
