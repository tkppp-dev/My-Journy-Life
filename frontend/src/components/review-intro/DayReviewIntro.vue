<template>
  <div class="day-review-intro-wrapper">
    <div class="day-review-intro-header">
      <div class="day-review-header-title">여정 일기</div>
      <div class="day-review-milestone">더 많은 일기 보기 ></div>
    </div>
    <div v-if="isDataExist" class="day-review-intro-items">
      <review-intro-item
        class="day-review-intro-item"
        v-for="review in reviewList"
        :key="review.reviewId"
        :reviewId="review.reviewId"
        :title="review.title"
        :nickname="review.nickname"
        :createdDate="review.createdDate"
        :views="review.views"
      />
    </div>
    <div v-if="!isDataExist" class="day-review-blank-wrapper">Commgin Soon</div>
  </div>
</template>

<script>
import ReviewIntroItem from './ReviewIntroItem.vue';
import _axios from '../../util/_axios';

export default {
  components: { ReviewIntroItem },
  name: 'DayReviewIntro',
  data() {
    return {
      reviewList: [],
      isDataExist: false,
    };
  },
  async created() {
    try {
      const res = await _axios.get('api/review/day/list');

      this.reviewList = res.data;
      if(this.reviewList.length > 0) this.isDataExist = true;
    } catch (e) {
      console.log(e);
      alert('에러발생');
    }
  },
};
</script>

<style>
.day-review-intro-wrapper {
  width: 960px;
  margin-top: 18px;
}

.day-review-intro-header {
  display: flex;
  align-items: center;
  padding: 6px 12px;
  color: #a02525;
  border-bottom: 2px solid #e2d8be;
}

.day-review-header-title {
  flex: 1;

  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 18px;
  font-weight: bold;
}

.day-review-milestone {
  font-family: 'nanum-gothic', 'sans-serif';
  font-weight: bold;
  cursor: pointer;
}

.day-review-blank-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;

  font-size: 18px;
  font-weight: bold;
}

.day-review-intro-items {
  display: flex;

  justify-content: space-around;
  padding: 18px 0px;
}

@media (max-width: 960px) {
  .day-review-intro-item:nth-child(4) {
    display: none;
  }
  .day-review-intro-wrapper {
    width: 90%;
  }
}

@media (max-width: 790px) {
  .day-review-intro-item:nth-child(3) {
    display: none;
  }
}

@media (max-width: 520px) {
  .day-review-intro-items {
    flex-direction: column;
    align-items: center;
  }
  .day-review-intro-item:nth-child(n) {
    display: block;
    margin-bottom: 16px;
  }
}
</style>
