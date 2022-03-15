<template>
  <div>
    <navbar />
    <main class="register-container">
      <div class="register-wrapper">
        <div class="register-title">회원가입</div>
        <div class="register-form-wrapper">
          <custom-input
            id="emailAddress"
            class="register-input"
            label="이메일 *"
            type="text"
            maxlength="255"
            @onChangeText="setEmail"
          />
          <div class="input-notification">
            {{ form.validation.emailAddress.message }}
          </div>
          <custom-input
            id="password"
            class="register-input"
            label="비밀번호 *"
            type="password"
            maxlength="15"
            @onChangeText="setPassword"
          />
          <div class="input-notification">
            {{ form.validation.password.message }}
          </div>
          <custom-input
            id="passwordCheck"
            class="register-input"
            label="비밀번호 확인 *"
            type="password"
            maxlength="15"
            @onChangeText="setPasswordCheck"
          />
          <div class="input-notification">
            {{ form.validation.passwordCheck.message }}
          </div>
          <custom-input
            id="nickname"
            class="register-input"
            label="별명"
            type="text"
            placeholder="별명을 설정하지 않는 경우 익명으로 설정됩니다"
            maxlength="16"
            @onChangeText="setNickname"
          />
          <div class="input-notification">
            {{ form.validation.nickname.message }}
          </div>
          <!-- 휴대폰 인증 삭제
          <custom-input
            id="phoneNumber"
            class="register-input"
            label="휴대폰 번호 *"
            type="text"
            placeholder="'-' 포함한 휴대폰 번호 입력"
          />
          <custom-button
            class="phone-auth-button"
            label="사용자 인증"
            :disabled="!isMobileAuthComplete"
          />
          -->
          <custom-button
            class="register-button"
            label="회원가입 완료"
            :disabled="!isAllFormDataValid"
            @onClickButton="register"
          />
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue';
import CustomButton from '../components/CustomButton.vue';
import CustomInput from '../components/CustomInput.vue';
import _axios from '../util/_axios';
import errorCode from '../util/errorCode';

export default {
  name: 'RegisterPage',
  components: { Navbar, CustomButton, CustomInput },
  data() {
    return {
      form: {
        emailAddress: '',
        password: '',
        passwordCheck: '',
        nickname: '',
        phoneNumber: '',
        validation: {
          emailAddress: {
            isValid: false,
            message: '',
          },
          password: {
            isValid: false,
            message: '',
          },
          passwordCheck: {
            isValid: false,
            message: '',
          },
          nickname: {
            isValid: true,
            message: '',
          },
        },
      },
    };
  },
  methods: {
    async register() {
      try {
        const email = this.form.emailAddress;
        const password = this.form.password;
        const nickname = this.form.nickname;

        await _axios.post('/api/register', {
          emailAddress: email,
          password: password,
          nickname: nickname === '' ? null : nickname,
        });

        this.$router.push('/');

      } catch (e) {
        if (e.response.status === 409) {
          if (
            e.response.data.errorCode === errorCode.DUPLICATED_EMAIL_ADDRESS
          ) {
            alert('이미 가입된 이메일 주소입니다');
          } else if (
            e.response.data.errorCode === errorCode.DUPLICATED_NICKNAME
          ) {
            alert('중복된 별명입니다. 다른 별명을 입력해 주세요');
          }
        } else {
          alert('예상치 못한 에러가 발생했습니다. 다시 시도해주세요')
        }
      }
    },
    setEmail(val) {
      this.form.emailAddress = val.toLowerCase();

      // validation check
      const validPattern = /^([\w.\-]+@\w+\.[a-zA-Z]+$)/;
      const isValid = validPattern.test(this.form.emailAddress);
      const validation = this.form.validation;
      validation.emailAddress.isValid = isValid;

      if (this.form.emailAddress.length === 0) {
        validation.emailAddress.message = '';
        return;
      }

      if (isValid) {
        validation.emailAddress.message = '';
      } else {
        validation.emailAddress.message = '이메일 형식이 올바르지 않습니다';
      }
    },
    setPassword(val) {
      this.form.password = val;

      // validation check
      const validPattern = /^[\w!@#$%^&*+,.\?\-]{6,15}$/;
      const isValid = validPattern.test(this.form.password);
      const validation = this.form.validation;
      validation.password.isValid = isValid;

      this.setPasswordCheck(this.form.passwordCheck);

      const length = this.form.password.length;
      let cnt = 0;
      if (this.form.password.match(/[a-zA-Z]/g) !== null) {
        cnt++;
      }
      if (this.form.password.match(/[0-9]/g) !== null) {
        cnt++;
      }
      if (this.form.password.match(/[!@#$%^&*_=+,.?\-]/g) !== null) {
        cnt++;
      }

      if (length === 0) {
        validation.password.message = '';
      } else {
        if (length < 6) {
          validation.password.message = '비밀번호는 최소 6자 이상이어야 합니다';
        } else if (!isValid) {
          validation.password.message =
            '비밀번호에 허용되지 않는 특수문자가 존재합니다';
        } else {
          if (cnt < 2) {
            validation.password.message =
              '비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 포함되어야 합니다';
          } else {
            validation.password.message = '';
          }
        }
      }
    },
    setPasswordCheck(val) {
      this.form.passwordCheck = val;

      // validation check
      const validation = this.form.validation;
      if (this.form.passwordCheck === this.form.password) {
        validation.passwordCheck.isValid = true;
        validation.passwordCheck.message = '';
      } else {
        validation.passwordCheck.isValid = false;
        if (
          this.form.password.length === 0 ||
          this.form.passwordCheck.length === 0
        ) {
          validation.passwordCheck.message = '';
        } else {
          validation.passwordCheck.message = '비밀번호가 일치하지 않습니다';
        }
      }
    },
    setNickname(val) {
      this.form.nickname = val;

      // validation check
      const validPattern = /^[a-zA-Z0-9가-힣]{2,16}$/;
      let isValid = validPattern.test(this.form.nickname);
      const validation = this.form.validation;

      if (this.form.nickname.length === 0) {
        validation.nickname.isValid = true;
      } else {
        validation.nickname.isValid = isValid;
      }

      if (isValid) {
        validation.nickname.message = '';
      } else {
        if (this.form.nickname.length === 0) {
          validation.nickname.message = '';
        } else if (this.form.nickname.match(/[^a-zA-Z0-9가-힣]/g) !== null) {
          validation.nickname.message = '유효하지 않은 별명입니다';
        } else {
          validation.nickname.message =
            '별명은 한글, 영문, 숫자로만 이루어지고 2-16자 사이여야 합니다';
        }
      }
    },
  },
  computed: {
    isAllFormDataValid() {
      const validation = this.form.validation;
      if (
        validation.emailAddress.isValid &&
        validation.password.isValid &&
        validation.passwordCheck.isValid &&
        validation.nickname.isValid
      ) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style>
.register-container {
  display: flex;
  justify-content: center;
}

.register-wrapper {
  width: 350px;
}

.register-title {
  text-align: center;
  font-size: 30px;
  font-weight: bold;
  color: #a02525;
  margin: 60px 0px;
}

.register-input:not(:first-child) {
  margin-top: 24px;
}

.input-notification,
.input-comment {
  padding-left: 2px;
  margin-top: 8px;
  font-size: 15px;
}

.input-notification {
  width: 100%;
  height: 19px;
  color: red;
}

.phone-auth-button {
  margin-top: 24px;
}

.register-button {
  margin-top: 24px;
}

@media (max-width: 480px) {
  .register-wrapper {
    width: 72%;
  }
}
</style>
