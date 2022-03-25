<template>
  <div>
    <navbar />
    <main class="day-review-container">
      <div class="day-review-wrapper">
        <div class="review-type">여정 일기</div>
        <review-header
          class="review-header"
          title="부산 여행 1일차"
          nickname="타타고"
          createdDate="2022-03-25 13:20"
          pageViews="11"
          likeCount="123"
        />
        <day-review-details 
          class="day-review-details-wrapper"
          country="한국"
          city="부산"
          spot="광안리, 민락회센터"
        />
        <review-content class="review-content" :content="content" />
        <review-like />
        <review-comment class="review-comment" />
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../../components/Navbar.vue';
import ReviewHeader from '../../components/review/ReviewHeader.vue';
import DayReviewDetails from '../../components/review/DayReviewDetails.vue'
import ReviewContent from '../../components/review/ReviewContent.vue';
import _axios from '../../util/_axios';
import ReviewLike from '../../components/review/ReviewLike.vue';
import ReviewComment from '../../components/review/ReviewComment.vue';

export default {
  components: { Navbar, ReviewHeader, DayReviewDetails, ReviewContent, ReviewLike, ReviewComment },
  name: 'DayReviewPage',
  component: {
    Navbar,
    ReviewHeader,
    DayReviewDetails
  },
  data() {
    return {
      content : `
        <h2>What is Lorem Ipsum?</h2>
        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
        <h2>Why do we use it?</h2>
        <p>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).</p>
      `
    }
  },
  created() {},
};
</script>

<style>
.day-review-container {
  display: flex;
  justify-content: center;
}
.day-review-wrapper {
  width: 960px;
}
@media (max-width: 960px) {
  .day-review-wrapper {
    width: 97%;
  }
}

.review-type {
  border-bottom: 2px solid rgba(160, 37, 37, 1);
  width: 100%;

  padding: 24px 0px 12px 0px;

  font-size: 30px;
  font-weight: bold;
}

.review-header {
  border-bottom: 1px solid rgba(160, 37, 37, 0.4);
}

.day-review-details-wrapper {
  margin: 18px 0px;
  padding: 0px 3px;
}

.review-content {
  padding: 0px 3px;
}

.review-comment {
  border-top: 2px solid rgba(160, 37, 37, 1);
}
</style>
