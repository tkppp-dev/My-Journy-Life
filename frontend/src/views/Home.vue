<template>
  <navbar />
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import CustomInput from '@/components/Input.vue'

export default {
  name: 'Home',
  components: {
    Navbar,
    CustomInput
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