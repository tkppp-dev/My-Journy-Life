<template>
  <base-modal :show="show">
    <template v-slot:modal-content>
      <div class="login-container">
        <div class="login-wrapper" @click.stop>
          <div class="login-title">로그인</div>
          <form @submit.prevent="submitForm">
            <div class="input-box">
              <custom-input
                type="text"
                label="이메일"
                @onChangeText="setEmailAddress"
              />
            </div>
            <div class="input-box">
              <custom-input
                type="password"
                label="비밀번호"
                @onChangeText="setPassword"
              />
            </div>
            <custom-button
              class="login-submit-btn"
              type="submit"
              label="로그인"
            ></custom-button>
          </form>
          <div class="sns-login-comment">소셜 계정으로 로그인</div>
          <div class="sns-login-wrapper">
            <button>
              <img
                class="sns-login-icon"
                src="../assets/icons/google-icon.png"
              />
            </button>
            <button>
              <img
                class="sns-login-icon"
                src="../assets/icons/naver-icon.png"
              />
            </button>
            <button>
              <img
                class="sns-login-icon"
                src="../assets/icons/kakao-icon.png"
              />
            </button>
          </div>
          <div class="register-comment">
            아직 회원이 아니신가요?
            <router-link class="register-anker" to="/register">
              회원가입 하기
            </router-link>
          </div>
        </div>
      </div>
    </template>
  </base-modal>
</template>

<script>
import BaseModal from './BaseModal.vue';
import CustomInput from './CustomInput.vue';
import CustomButton from './CustomButton.vue';
import axios from 'axios'
import _axios from '../util/_axios'
import qs from 'qs'

export default {
  components: { BaseModal, CustomInput, CustomButton },
  props: {
    show: Boolean,
  },
  data() {
    return {
      form: {
        emailAddress: '',
        password: '',
      }
    };
  },
  methods: {
    setEmailAddress(val) {
      this.form.emailAddress = val;
    },
    setPassword(val) {
      this.form.password = val
    },
    async submitForm() {
      if(!/^([\w.\-]+@\w+\.[a-zA-Z]+$)/.test(this.form.emailAddress)){
        alert("이메일 형식이 올바르지 않습니다")
      }
      else if(!/^[\w!@#$%^&*+,.\?\-]{6,15}$/.test(this.form.password)){
        alert("비밀번호 형식이 올바르지 않습니다")
      }
      else {
        try{
          const res = await axios.post("/api/login", qs.stringify(this.form), {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          })

          if(res.status === 200){
            // vuex에 auth token 저장
            this.$store.commit('performLogin',{
              accessToken: res.data.accessToken,
              refreshToken: res.data.refreshToken
            })

            // 로그인 사용자 정보 로드
            const response = await _axios.get(`/api/member/${this.form.emailAddress}`)
            this.$store.commit('setUserInfo', response.data)

            this.$emit('close')
          } else {
            alert('이메일 또는 비밀번호가 일치하지 않습니다')
          }
        } catch(e){
          alert('예상치 못한 문제가 발생했습니다. 다시 시도해주세요')
        }
      }
    },
  },
};
</script>

<style>
.login-container {
  display: flex;
  justify-content: center;
  margin-top: 200px;
}

.login-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 350px;
  padding: 0 65px;

  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
}

.login-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  padding: 50px;
}

.input-box {
  margin-bottom: 36px;
}

.login-submit-btn {
  display: block;
  width: 100%;
  height: 37px;
  margin-bottom: 24px;
  color: #a02525;
  background-color: #e2d8be;
  font-weight: bold;

  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
}

.login-submit-btn:hover {
  opacity: 0.8;
}

.sns-login-comment {
  font-weight: bold;
  text-align: center;
}

.sns-login-wrapper {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 100%;
  height: 68px;
  margin: 12px 0 18px 0;
  background-color: #e2d8be;

  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.2);
}

.sns-login-icon {
  width: 40px;
}
button {
  margin: 0;
  padding: 0;
}

.register-comment {
  font-weight: bold;
  text-align: center;
  margin-bottom: 50px;
}

.register-anker {
  font-weight: bold;
  color: #a02525;
  text-decoration: none;
}

.register-anker:hover {
  color: #a02525;
}

.register-anker:visited {
  color: #a02525;
}
</style>
