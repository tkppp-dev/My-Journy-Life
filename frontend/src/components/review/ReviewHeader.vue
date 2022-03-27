<template>
  <div class="review-header-wrapper">
    <div class="review-title">
      {{ title }}
    </div>
    <div class="review-meta-data-wrapper">
      <div class="review-nickname review-meta-data">
        {{ nickname }}
      </div>
      <div class="review-created-date review-meta-data">
        {{ createdDate }}
      </div>
      <div class="review-views review-meta-data">
        조회수 {{ pageViews }}
      </div>
      <div class="review-like-count review-meta-data">
        좋아요 {{ likeCount }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewHeader',
  props: {
    title: {
      type: String,
      required: true,
    },
    nickname: {
      type: String,
      required: true
    },
    createdDate: {
      type: String,
      required: true
    },
    pageViews: {
      type: String,
      required: true,
    },
    likeCount: {
      type: [String, Number],
      required: true
    }
  },
};
</script>

<style>
.review-header-wrapper {
  width: 100%;
  font-family: 'nanum-gothic', 'sans-serif';
  padding: 15px 3px;
  box-sizing: border-box;
}

.review-title {
  font-size: 18px;
  font-weight: bold;
}

.review-meta-data-wrapper {
  display: flex;
  margin-top: 6px;

  font-size: 13px;
  color: rgba(51,51,51, 0.8);
  font-weight: bold;
}

.review-meta-data:not(:last-child) {
  padding-right: 8px;
}
.review-meta-data:not(:first-child) {
  padding-left: 8px;
}
.review-meta-data:not(:nth-child(2n)) {
  border-right: 1px solid rgba(51,51,51, 0.8);
}

.review-created-date {
  flex: 1;
  border-left: none;
}

</style>
