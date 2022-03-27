<template>
  <div class="review-content-wrapper" v-html="content" />
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
.review-content-wrapper {
  box-sizing: border-box;
}
</style>