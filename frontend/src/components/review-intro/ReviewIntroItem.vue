<template>
  <div class="review-intro-item-container" @click="moveToPost">
    <div class="thumbnail-wrapper">
      <div class="thumbnail-placeholder">
        <img
          class="thumbnail-img"
          src="../../assets/thumbnail-placeholder.png"
        />
      </div>
    </div>
    <div class="review-intro-item-desc-wrapper">
      <div class="review-intro-item-title">
        {{ title }}
      </div>
      <div class="review-intro-item-meta">
        {{ formatedDate }} · 조회수 {{ views }}
      </div>
      <div class="review-intro-item-writer">
        by <strong>{{ nickname === null ? '익명 사용자' : nickname }}</strong>
      </div>
    </div>
  </div>
</template>

<script>
import { format } from 'date-fns';
export default {
  name: 'ReviewIntroItem',
  props: {
    reviewId: [Number, String],
    title: String,
    nickname: [String, null],
    createdDate: [String],
    views: [Number, String],
  },
  methods: {
    moveToPost(){
      this.$router.push(`/review/day?reviewId=${this.reviewId}&title=${this.title}`)
    }
  },
  computed: {
    formatedDate() {
      return format(new Date(this.createdDate), 'yyyy년 MM월 dd일');
    },
  },
};
</script>

<style>
.review-intro-item-container {
  width: 220px;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.2);
  border-radius: 3px;
  cursor: pointer;
}

.review-intro-item-container:hover {
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
}

.thumbnail-wrappper {
  width: 25%;
}
.thumbnail-placeholder {
  position: relative;
  padding-top: 56.25%;
  overflow: hidden;
}
.thumbnail-img {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  max-width: 100%;
  height: auto;
}

.review-intro-item-desc-wrapper {
  padding: 12px 8px;
  font-family: 'nanum-gothic', 'sans-serif';
}

.review-intro-item-title {
  width: 100%;
  height: 36px;
  font-weight: bold;

  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.review-intro-item-meta {
  margin-top: 9px;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.4);
}

.review-intro-item-writer {
  margin-top: 9px;
  font-size: 14px;
}

@media (max-width: 960px) {
  .review-intro-item-container {
    width: 30%;
  }
}

@media (max-width: 790px) {
  .review-intro-item-container {
    width: 45%;
  }
}

@media (max-width: 520px) {
  .review-intro-item-container {
    width: 90%;
  }
}
</style>
