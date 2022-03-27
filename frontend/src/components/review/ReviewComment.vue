<template>
  <div class="review-comment-container">
    <div v-for="(comment, index) in comments" :key="index">
      <div class="review-comment-wrapper">
        <div class="review-comment-nickname">{{ comment.nickname === null ? '익명 사용자' : comment.nickname }}</div>
        <div class="review-comment-content">{{ comment.comment }}</div>
        <div class="reviwe-comment-created-date">{{ comment.createdDate }}</div>
      </div>
    </div>
    <div class="review-comment-writer-wrapper">
      <textarea
        class="review-comment-input"
        type="text"
        spellcheck="false"
        v-model="inputComment"
      ></textarea>
      <div class="review-comment-submit-wrapper">
        <button class="review-comment-submit" @click="registerComment">등록</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewContent',
  props: {
    comments: {
      type: Array,
      default: function () {
        return [
          {
            nickname: '별명',
            content: '댓글 본문',
            createdDate: '2022.03.25 12:25:32',
            type: 'regular',
          },
          {
            nickname: '별명',
            content: '댓글 본문',
            createdDate: '2022.03.25 12:25:32',
            type: 'regular',
          },
          {
            nickname: '별명',
            content: '댓글 본문',
            createdDate: '2022.03.25 12:25:32',
            type: 'regular',
          },
        ];
      },
    },
  },
  data(){
    return {
      inputComment: ''
    }
  },
  methods: {
    registerComment(){
      this.$emit('registerComment', this.inputComment)
    }
  }
};
</script>

<style>
.review-comment-wrapper {
  display: flex;
  padding: 9px 3px;
  font-size: 14px;
  border-bottom: 1px solid rgba(160, 37, 37, 0.2);
}

.review-comment-nickname {
  flex: 1;
}
.review-comment-content {
  flex: 4;
}
.review-comment-created-date {
  flex: 1;
}

.review-comment-writer-wrapper {
  margin-top: 40px;
  padding: 15px 3px;
  border-top: 2px solid rgba(160, 37, 37, 1);
  border-bottom: 2px solid rgba(160, 37, 37, 1);
}

.review-comment-input {
  width: 100%;
  height: 90px;
  padding: 13px 10px;
  resize: none;
  box-sizing: border-box;
}
.review-comment-input:focus {
  border: 1px solid rgb(160, 37, 37);
}

.review-comment-submit-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 7px;
}

.review-comment-submit {
  padding: 7px 24px;
  background-color: #e2d8be;
  font-weight: bold;
}
</style>
