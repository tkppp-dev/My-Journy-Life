<template>
  <div>
    <navbar />
    <main class="day-review-list-wrapper">
      <div class="review-type">여정 일기</div>
      <div class="review-list-header">
        <div class="review-list-header-item review-list-header-title">제목</div>
        <div class="review-list-header-item">작성자</div>
        <div class="review-list-header-item">작성일</div>
        <div class="review-list-header-item">조회수</div>
        <div class="review-list-header-item">좋아요</div>
      </div>
      <div>
        <review-list-item
          v-for="review in reviews"
          :key="review.reviewId"
          :review="review"
        />
      </div>
      <div class="reivew-list-page-number-list">
        <div
          class="review-list-page-number"
          v-for="pageNumber in pageRange"
          :class="{ 'current-page-number': pageNumber === currentPage }"
          :key="pageNumber"
          @click="getOtherPageNumberList(pageNumber + 1)"
        >
          {{ pageNumber + 1 }}
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../../components/Navbar.vue';
import ReviewListItem from '../../components/review-list/ReviewListItem.vue';
import _axios from '../../util/_axios';

export default {
  components: { Navbar, ReviewListItem },
  data() {
    return {
      totalPages: null,
      currentPage: null,
      reviews: [],
      pageRange: [],
    };
  },
  methods: {
    async getDayReviewList(){
      try {
      const pageNumber =
        this.$route.query.currentPage === undefined
          ? 0
          : this.$route.query.currentPage - 1;

      const res = await _axios.get(
        `/api/review/day/list?currentPage=${pageNumber}`
      );

      this.totalPages = res.data.totalPages;
      this.currentPage = res.data.currentPage;
      this.reviews = res.data.reviews;

      const range = [];
      let offset;
      if (this.currentPage < 2) {
        offset = this.currentPage;
      } else if (this.totalPages - this.currentPage < 3) {
        offset = 5 - (this.totalPages - this.currentPage);
      } else {
        offset = 2;
      }

      for (
        let i = this.currentPage - offset;
        i < this.currentPage + 5 - offset;
        i++
      ) {
        if (i < 0) continue;
        if (i >= this.totalPages) break;
        range.push(i);
      }
      this.pageRange = range;
    } catch (e) {
      console.log(e);
      alert(`에러 발생 : ${e.message}`);
    }
    }, 
    async getOtherPageNumberList(pageNumber) {
      if (pageNumber !== this.currentPage) {
        await this.$router.push(`/review/day/list?currentPage=${pageNumber}`);
        this.getDayReviewList()
      }
    },
  },
  async created() {
    this.getDayReviewList()
  },
};
</script>

<style>
.day-review-list-wrapper {
  width: 960px;
  margin: 0 auto;
}

@media (max-width: 960px) {
  .day-review-list-wrapper {
    width: 98%;
  }
}

.review-list-header {
  display: flex;
  padding: 12px 0px;
  border-bottom: 0.12rem solid rgba(160, 37, 37, 1);
}

.review-list-header-item {
  flex: 1;
  font-size: 15px;
  font-weight: bold;
  text-align: center;
}
.review-list-header-title {
  flex: 6;
}

.reivew-list-page-number-list {
  display: flex;
  justify-content: center;
  margin: 18px 0px;
  font-size: 15px;
}

.current-page-number {
  font-weight: bold;
}
.review-list-page-number {
  padding: 0px 6px;
  cursor: pointer;
}
.review-list-page-number:not(:last-child) {
  border-right: 1px solid rgba(0, 0, 0, 0.4);
}
</style>
