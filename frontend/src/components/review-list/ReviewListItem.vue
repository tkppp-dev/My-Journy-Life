<template>
  <div class="review-list-item-wrapper">
    <div class="review-list-item review-list-item-title">
      <div class="review-list-title" @click="moveToReviewPage">{{ review.title }}</div>
    </div>
    <div class="review-list-item">
      {{ review.nickname === null ? '익명 사용자' : review.nickname }}
    </div>
    <div class="review-list-item">{{ formatedDate }}</div>
    <div class="review-list-item">{{ review.views }}</div>
    <div class="review-list-item">{{ review.likeCount }}</div>
  </div>
</template>

<script>
import { format } from 'date-fns';
export default {
  name: 'Review-list-item-wrapper',
  props: {
    review: {
      type: Object,
      required: true,
    },
  },
  methods: {
    moveToReviewPage() {
      this.$router.push(
        `/review/day?reviewId=${this.review.reviewId}&title=${this.review.title}`
      );
    },
  },
  computed: {
    formatedDate() {
      const now = new Date();
      const standard = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate()
      );
      const createdDate = new Date(this.review.createdDate);
      if (standard < createdDate) {
        return format(createdDate, 'hh:mm');
      } else {
        return format(createdDate, 'MM-dd');
      }
    },
  },
};
</script>

<style>
.review-list-item-wrapper {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 6px 0px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);

  font-size: 14px;
}
.review-list-item-wrapper:hover {
  background-color: rgba(0, 0, 0, 0.03);
}
.review-list-item {
  flex: 1;
}
.review-list-item:not(:first-child) {
  text-align: center;
}

.review-list-item-title {
  padding-left: 6px;
  flex: 6;
}
.review-list-title {
  display: inline;
  cursor: pointer;
}
</style>
