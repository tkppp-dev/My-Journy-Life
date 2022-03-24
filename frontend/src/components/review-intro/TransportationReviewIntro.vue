<template>
  <div class="transportation-review-intro-wrapper">
    <div class="transportation-review-intro-header">
      <div class="transportation-review-header-title">교통편 리뷰</div>
      <div class="transportation-review-milestone">
        더 많은 교통편 리뷰 보기 >
      </div>
    </div>
    <div v-if="isDataExist" class="transportation-review-intro-items">
      <review-intro-item class="transportation-review-intro-item" />
      <review-intro-item class="transportation-review-intro-item" />
      <review-intro-item class="transportation-review-intro-item" />
      <review-intro-item class="transportation-review-intro-item" />
    </div>
    <div v-if="!isDataExist" class="transportation-blank-wrapper">
      Commgin Soon
    </div>
  </div>
</template>

<script>
import ReviewIntroItem from './ReviewIntroItem.vue';

export default {
  components: { ReviewIntroItem },
  name: 'transportationReviewIntro',
  data() {
    return {
      reviewList: [],
      isDataExist: false,
    };
  },
  mounted() {
    if (this.reviewList.length !== 0) {
      this.isDataExist = true;
    }
  },
};
</script>

<style>
.transportation-review-intro-wrapper {
  width: 960px;
  margin-top: 18px;
}

.transportation-review-intro-header {
  display: flex;
  align-items: center;
  padding: 6px 12px;
  color: #a02525;
  border-bottom: 2px solid #e2d8be;
}

.transportation-review-header-title {
  flex: 1;

  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 18px;
  font-weight: bold;
}

.transportation-review-milestone {
  font-family: 'nanum-gothic', 'sans-serif';
  font-weight: bold;
  cursor: pointer;
}

.transportation-blank-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;

  font-size: 18px;
  font-weight: bold;
}

.transportation-review-intro-items {
  display: flex;

  justify-content: space-around;
  padding: 18px 0px;
}

@media (max-width: 960px) {
  .transportation-review-intro-item:nth-child(4) {
    display: none;
  }
  .transportation-review-intro-wrapper {
    width: 90%;
  }
}

@media (max-width: 790px) {
  .transportation-review-intro-item:nth-child(3) {
    display: none;
  }
}

@media (max-width: 520px) {
  .transportation-review-intro-items {
    flex-direction: column;
    align-items: center;
  }
  .transportation-review-intro-item:nth-child(n) {
    display: block;
    margin-bottom: 16px;
  }
}
</style>
