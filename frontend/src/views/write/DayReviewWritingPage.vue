<template>
  <div>
    <navbar />
    <main class="main-container">
      <div class="main-wrapper">
        <div class="review-write-type">여정 일기 작성</div>
        <div class="division-line">
          <hr />
        </div>
        <div class="title-wrapper">
          <input
            class="title-input"
            type="text"
            v-model="form.title"
            maxlength="50"
            placeholder="제목을 입력하세요"
          />
        </div>
        <div class="journy-detail-wrapper">
          <input
            class="journy-detail country-select"
            type="search"
            v-model="form.country"
            list="countrys"
            placeholder="나라"
          />
          <datalist id="countrys">
            <option
              v-for="(country, index) in countrys"
              :value="country"
              :key="index"
            >
              {{ country.text }}
            </option>
          </datalist>
          <input
            class="journy-detail city-input"
            type="text"
            v-model="form.city"
            placeholder="지역"
            maxlength="20"
          />
          <input
            class="journy-detail major-travel-spot"
            type="text"
            v-model="form.majorSpot"
            placeholder="주요 여행지 (예: 광안리, 해운대)"
          />
        </div>
        <editor @update="updateContent" />
        <div class="button-wrapper">
          <button class="save-button complete-button" @click="saveReview">
            등록
          </button>
          <button class="save-button temporary-save-button">임시 저장</button>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../../components/Navbar.vue';
import Editor from '../../components/Editor.vue';
import ImageUploadModal from '../../components/modal/ImageUploadModal.vue';
import _axios from '../../util/_axios';

export default {
  components: {
    Navbar,
    Editor,
    ImageUploadModal,
  },
  data() {
    return {
      form: {
        title: '',
        country: '한국',
        city: '',
        majorSpot: '',
        content: '',
        images: [],
      },
      countrys: ['한국', '일본', '미국', '프랑스', '독일', '영국'],
    };
  },
  methods: {
    updateContent(content, images) {
      this.form.content = content;
      this.form.images = images;
    },
    async saveReview() {
      if (this.validateForm()) {
        try {
          const res = await _axios.post('/api/review/day', this.form);
          console.log();

          this.$router.push(
            `/review/day?reviewId=${res.data}&title=${this.form.title}`
          );
        } catch (error) {
          console.log(error);
          alert(
            '예상치 못한 문제로 리뷰 등록에 실패했습니다. 다시 시도해주세요'
          );
        }
      } else {
        alert('작성되지 않은 부분을 채워주세요');
      }
    },
    validateForm() {
      for (let key in this.form) {
        if (key !== 'images') {
          if (this.form[key].length === 0) {
            return false;
          }
        }
      }
      return true;
    },
  },
};
</script>

<style>
.main-container {
  display: flex;
  justify-content: center;
}

.main-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 960px;
}

.review-write-type {
  display: flex;

  width: 100%;

  margin-top: 30px;
  margin-bottom: 8px;

  font-size: 30px;
  font-weight: bold;

  text-align: left;
}

.division-line {
  width: 100%;
}

.title-wrapper {
  width: 100%;
}

.title-input {
  width: 100%;
  height: 45px;

  font-size: 18px;
  font-weight: bold;

  padding: 8px 12px;
  margin: 20px 0 15px 0;

  box-sizing: border-box;
  border: none;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.4);
}

.title-input::placeholder {
  color: rgba(0, 0, 0, 0.4);
}

.journy-detail-wrapper {
  display: flex;
  justify-content: space-between;

  width: 100%;
}

.journy-detail {
  height: 32px;

  margin-bottom: 15px;
  padding: 0px 8px;

  box-sizing: border-box;
  border: none;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.4);
}

.journy-detail:not(:last-child) {
  margin-right: 12px;
}

.country-select {
  flex: 1;
}
.city-input {
  flex: 2;
}
.major-travel-spot {
  flex: 5;
}

.button-wrapper {
  display: flex;
  flex-direction: row-reverse;

  width: 100%;
  margin: 15px 0;
}

.save-button {
  padding: 8px 16px;
  border-radius: 4px;
  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 15px;
  font-weight: 600;
}

.complete-button {
  margin-left: 12px;
  background-color: #e2d8be;
}

.temporary-save-button {
  background-color: rgba(0, 0, 0, 0.1);
}

@media (max-width: 960px) {
  .main-wrapper,
  .review-type,
  .division-line,
  .title-wrapper,
  .journy-detail-wrapper,
  .button-wrapper {
    width: 98%;
  }
}
</style>
