<template>
  <div class="spot-review-intro-wrapper">
    <div class="spot-review-intro-header">
      <div class="spot-review-header-title">여행지 리뷰</div>
      <div class="spot-review-milestone">더 많은 여행지 리뷰 보기 ></div>
    </div>
    <div v-if="isDataExist" class="spot-review-intro-items">
      <review-intro-item class="spot-review-intro-item" />
      <review-intro-item class="spot-review-intro-item" />
      <review-intro-item class="spot-review-intro-item" />
      <review-intro-item class="spot-review-intro-item" />
    </div>
    <div v-if="!isDataExist" class="spot-review-blank-wrapper">
      Commgin Soon
    </div>
  </div>
</template>

<script>
import ReviewIntroItem from './ReviewIntroItem.vue';

export default {
  components: { ReviewIntroItem },
  name: 'SpotReviewIntro',
  data() {
    return {
      reviewList: [],
      isDataExist: false
    }
  },
  mounted() {
    if(this.reviewList.length !== 0) {
      this.isDataExist = true
    }
  }
};
</script>

<style>
.spot-review-intro-wrapper {
  width: 960px;
  margin-top: 18px;
}

.spot-review-intro-header {
  display: flex;
  align-items: center;
  padding: 6px 12px;
  color: #a02525;
  border-bottom: 2px solid #e2d8be;
}

.spot-review-header-title {
  flex: 1;

  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 18px;
  font-weight: bold;
}

.spot-review-milestone {
  font-family: 'nanum-gothic', 'sans-serif';
  font-weight: bold;
  cursor: pointer;
}

.spot-review-blank-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;

  font-size: 18px;
  font-weight: bold;
}

.spot-review-intro-items {
  display: flex;

  justify-content: space-around;
  padding: 18px 0px;
}

@media (max-width: 960px) {
  .spot-review-intro-item:nth-child(4) {
    display: none;
  }
  .spot-review-intro-wrapper {
    width: 90%;
  }
}

@media (max-width: 790px){
  .spot-review-intro-item:nth-child(3) {
    display: none;
  }
}

@media (max-width: 520px){
  .spot-review-intro-items {
    flex-direction: column;
    align-items: center;
  }
  .spot-review-intro-item:nth-child(n) {
    display: block;
    margin-bottom: 16px;
  }
}
</style>
