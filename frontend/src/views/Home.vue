<template>
  <div>
    <navbar />
    <main class="review-intro-container">
      <day-review-intro />
      <transportation-review-intro />
      <spot-review-intro />
    </main>
    <footer></footer>
  </div>
</template>

<script>
import Navbar from '../components/modal/Navbar.vue';
import DayReviewIntro from '../components/review-intro/DayReviewIntro.vue';
import TransportationReviewIntro from '../components/review-intro/TransportationReviewIntro.vue'
import SpotReviewIntro from '../components/review-intro/SpotReviewIntro.vue'

export default {
  name: 'Home',
  components: {
    Navbar,
    DayReviewIntro,
    TransportationReviewIntro,
    SpotReviewIntro
  },
  data() {
    return {
      input: '',
    };
  },
  methods: {},
};
</script>

<style>
.review-intro-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
