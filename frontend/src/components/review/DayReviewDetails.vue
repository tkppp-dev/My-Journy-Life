<template>
  <div>
    <div class="day-review-details">
      <div class="day-review-details-item">국가 : {{ country }}</div>
      <div class="day-review-details-item">도시 : {{ city }}</div>
      <div class="day-review-details-item">주요 여행지 : {{ spot }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DayReviewDetails',
  props: {
    country: {
      type: String,
      required: true,
    },
    city: {
      type: String,
      required: true,
    },
    spot: {
      type: String,
      required: true,
    },
  },
};
</script>

<style>
.day-review-details-title {
  font-size: 16px;
  font-weight: bold;
}

.day-review-details {
  display: flex;
  font-size: 15px;
  font-weight: bold;
}
.day-review-details-item:not(:last-child) {
  padding-right: 12px;
  border-right: 1px solid rgba(51,51,51, 0.8);
}

.day-review-details-item:not(:first-child) {
  padding-left: 12px;
}

</style>
