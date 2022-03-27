<template>
  <div>
    <navbar />
    <main v-if="loaded" class="day-review-container">
      <div class="day-review-wrapper">
        <div class="review-type">여정 일기</div>
        <review-header
          class="review-header"
          :title="header.title"
          :nickname="header.nickname"
          :createdDate="header.createdDate"
          :pageViews="header.pageViews"
          :likeCount="recommendCount.like"
        />
        <day-review-details
          class="day-review-details-wrapper"
          :country="details.country"
          :city="details.city"
          :spot="details.majorSpot"
        />
        <review-content class="review-content" :content="content" />
        <review-like
          :likeCnt="recommendCount.like"
          :dislikeCnt="recommendCount.dislike"
          @updateLikeCount="updateLikeCount"
          @updateDislikeCount="updateDislikeCount"
        />
        <review-comment
          class="review-comment"
          :comments="comments"
          @registerComment="registerComment"
        />
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../../components/Navbar.vue';
import ReviewHeader from '../../components/review/ReviewHeader.vue';
import DayReviewDetails from '../../components/review/DayReviewDetails.vue';
import ReviewContent from '../../components/review/ReviewContent.vue';
import ReviewLike from '../../components/review/ReviewLike.vue';
import ReviewComment from '../../components/review/ReviewComment.vue';
import _axios from '../../util/_axios';
import { format } from 'date-fns';

export default {
  components: {
    Navbar,
    ReviewHeader,
    DayReviewDetails,
    ReviewContent,
    ReviewLike,
    ReviewComment,
  },
  name: 'DayReviewPage',
  component: {
    Navbar,
    ReviewHeader,
    DayReviewDetails,
  },
  data() {
    return {
      reviewId: '',
      header: {
        title: '',
        nickname: '',
        createdDate: '',
        pageViews: '',
      },
      details: {
        country: '',
        city: '',
        majorSpot: '',
      },
      recommendCount: {
        like: '',
        dislike: '',
      },
      content: [],
      comments: null,
      loaded: false,
    };
  },
  methods: {
    async updateLikeCount() {
      try {
        const res = await _axios.patch(`/api/review/day/like/${this.reviewId}`);

        this.recommendCount.like = res.data;
      } catch (e) {
        console.log(e);
        alert('좋아요 갱신 중 오류가 발생했습니다');
      }
    },
    async updateDislikeCount() {
      try {
        const res = await _axios.patch(
          `/api/review/day/dislike/${this.reviewId}`
        );

        this.recommendCount.dislike = res.data;
      } catch (e) {
        console.log(e);
        alert('싫어요 갱신 중 오류가 발생했습니다');
      }
    },
    setComments(comments) {
      this.comments = comments.map((comment) => {
        comment.createdDate = format(
          new Date(comment.createdDate),
          'yyyy.MM.dd hh:mm:ss'
        );
        return comment;
      });
    },
    async registerComment(comment) {
      try {
        const res = await _axios.post(`/api/comment/review/day`, {
          reviewId: this.reviewId,
          comment,
        });

        this.setComments(res.data);
      } catch (e) {
        alert('댓글 등록 중 오류가 발생했습니다.');
      }
    },
  },
  async created() {
    try {
      const res = await _axios.get('/api' + this.$route.fullPath);

      this.reviewId = res.data.id;

      this.header.title = res.data.title;
      this.header.nickname =
        res.data.member.nickname !== null
          ? res.data.member.nickname
          : '익명 사용자';
      this.header.createdDate = format(
        new Date(res.data.createdDate),
        'yyyy.MM.dd hh:mm:ss'
      );
      this.header.pageViews = res.data.views;

      this.details.country = res.data.country;
      this.details.city = res.data.city;
      this.details.majorSpot = res.data.majorSpot;

      this.recommendCount.like = res.data.likeCount;
      this.recommendCount.dislike = res.data.dislikeCount;

      this.content = res.data.content;

      const commentRes = await _axios.get(
        `/api/comment/review/day/${this.reviewId}`
      );
      this.setComments(commentRes.data);

      this.loaded = true;
    } catch (e) {
      console.log(e);
      alert('존재하지 않는 리뷰입니다');
      this.$router.go(-1);
    }
  },
};
</script>

<style>
.day-review-container {
  display: flex;
  justify-content: center;
  font-family: 'apple_sd_gothic_neo', 'sans-serif';
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
