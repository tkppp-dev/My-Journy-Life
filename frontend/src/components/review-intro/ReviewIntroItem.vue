<template>
  <div class="review-intro-item-container">
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
        [XX의 우당탕탕 서울여행] 서울은 복잡해 - 1일차
      </div>
      <div class="review-intro-item-meta">2022년 3월 12일 · 9개의 댓글</div>
      <div class="review-intro-item-writer">by <strong>익명</strong></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewIntroItem',
};
</script>

<style>
.review-intro-item-container {
  width: 220px;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}

.review-intro-item-container:hover {
  width: 240px;
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
  .review-intro-item-container:hover {
    width: 33%;
  }
}

@media (max-width: 790px) {
  .review-intro-item-container {
    width: 45%;
  }
  .review-intro-item-container:hover {
    width: 48%;
  }
}

@media (max-width: 520px) {
  .review-intro-item-container {
    width: 90%;
  }
  .review-intro-item-container:hover {
    width: 95%;
  }
}
</style>
