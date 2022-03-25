<template>
  <div v-html="content" />
</template>

<script>
export default {
  name: 'ReviewContent',
  props: {
    content: {
      type: String,
      required: true,
    }
  }
}
</script>

<style>

</style>