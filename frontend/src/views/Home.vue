<template>
  <navbar />
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'Home',
  components: {
    Navbar,
  },
  data() {
    return {
      input: ''
    }
  }

}
</script>

<style>

</style>