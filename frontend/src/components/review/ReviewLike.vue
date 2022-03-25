<template>
  <div class="review-like-wrapper">
    <div class="like-button-wrapper">
      <like-icon fillColor="#a02525" />
      <div class="like-count">{{ likeCnt }}</div>
    </div>
    <div class="unlike-button-wrapper">
      <div class="unlike-count">{{ unlikeCnt }}</div>
      <unlike-icon fillColor="#808080" />
    </div>
  </div>
</template>

<script>
import LikeIcon from 'vue-material-design-icons/ThumbUpOutline.vue';
import UnlikeIcon from 'vue-material-design-icons/ThumbDownOutline.vue';

export default {
  name: 'ReviewLike',
  components: {
    LikeIcon,
    UnlikeIcon,
  },
  props: {
    likeCnt: {
      type: [String, Number],
      default: 0,
    },
    unlikeCnt: {
      type: [String, Number],
      default: 0,
    },
  },
};
</script>

<style>
.review-like-wrapper {
  display: flex;
  justify-content: center;
  margin: 40px 0;
}

.like-button-wrapper,
.unlike-button-wrapper {
  display: flex;
  align-items: center;

  padding: 6px 16px;
  border-radius: 6px;

  cursor: pointer;
}

.like-button-wrapper {
  margin-right: 12px;
  box-shadow: 0 0 1px #a02525;
  border: 2px solid #a02525;
}

.unlike-button-wrapper {
  box-shadow: 0 0 1px rgba(128, 128, 128, 0.8);
  border: 2px solid rgba(128, 128, 128, 0.8);
}

.like-button-wrapper:hover {
  background-color: rgba(160, 37, 37, 0.05);
}

.unlike-button-wrapper:hover {
  background-color: rgba(128, 128, 128, 0.1);
}

.like-count {
  margin-left: 8px;
}
.unlike-count {
  margin-right: 8px;
}
</style>
