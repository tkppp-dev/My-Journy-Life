<template>
  <div class="transportion-review-intro-wrapper">
    <div class="transportion-review-intro-header">
      <div class="transportion-review-header-title">교통편 리뷰</div>
      <div class="transportion-review-milestone">더 많은 교통편 리뷰 보기 ></div>
    </div>
    <div class="transportion-review-intro-items">
      <review-intro-item class="transportion-review-intro-item" />
      <review-intro-item class="transportion-review-intro-item" />
      <review-intro-item class="transportion-review-intro-item" />
      <review-intro-item class="transportion-review-intro-item" />
    </div>
  </div>
</template>

<script>
import ReviewIntroItem from './ReviewIntroItem.vue';

export default {
  components: { ReviewIntroItem },
  name: 'TransportionReviewIntro',
};
</script>

<style>
.transportion-review-intro-wrapper {
  width: 960px;
  margin-top: 18px;
}

.transportion-review-intro-header {
  display: flex;
  align-items: center;
  padding: 6px 12px;
  color: #a02525;
  border-bottom: 2px solid #e2d8be;
}

.transportion-review-header-title {
  flex: 1;

  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 18px;
  font-weight: bold;
}

.transportion-review-milestone {
  font-family: 'nanum-gothic', 'sans-serif';
  font-weight: bold;
  cursor: pointer;
}

.transportion-review-intro-items {
  display: flex;

  justify-content: space-around;
  padding: 18px 0px;
}

@media (max-width: 960px) {
  .transportion-review-intro-item:nth-child(4) {
    display: none;
  }
  .transportion-review-intro-wrapper {
    width: 90%;
  }
}

@media (max-width: 790px){
  .transportion-review-intro-item:nth-child(3) {
    display: none;
  }
}

@media (max-width: 520px){
  .transportion-review-intro-items {
    flex-direction: column;
    align-items: center;
  }
  .transportion-review-intro-item:nth-child(n) {
    display: block;
    margin-bottom: 16px;
  }
}
</style>
