<template>
  <div>
    <navbar />
    <main class="register-container">
      <div class="register-wrapper">
        <div class="register-title">회원가입</div>
        <div class="register-form-wrapper">
          <custom-input
            class="register-input"
            label="이메일 *"
            type="text"
            @onChangeText="setEmail"
          />
          <div class="input-notification">
            {{ validationMessage.emailAddress }}
          </div>
          <custom-input
            class="register-input"
            label="비밀번호 *"
            type="password"
            maxlength="15"
            @onChangeText="setPassword"
          />
          <div class="input-notification">
            {{ validationMessage.password }}
          </div>
          <custom-input
            class="register-input"
            label="비밀번호 확인 *"
            type="password"
            maxlength="15"
            @onChangeText="setPasswordCheck"
          />
          <div class="input-notification">
            {{ validationMessage.passwordCheck }}
          </div>
          <custom-input
            class="register-input"
            label="별명"
            type="text"
            @onChangeText="setPasswordCheck"
          />
          <div class="input-comment">
            별명을 설정하지 않는 경우 익명으로 설정됩니다
          </div>
          <div
            v-if="validationMessage.nickname.length !== 0"
            class="input-notification"
          >
            {{ validationMessage.nickname }}
          </div>
          <custom-input class="register-input" label="이름 *" type="text" />
          <custom-input
            class="register-input"
            label="휴대폰 번호 *"
            type="text"
          />
          <custom-button class="phone-auth-button" label="사용자 인증" />
          <custom-button class="register-button" label="회원가입 완료" />
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue';
import CustomButton from '../components/CustomButton.vue';
import CustomInput from '../components/CustomInput.vue';
export default {
  name: 'RegisterPage',
  components: { Navbar, CustomButton, CustomInput },
  data() {
    return {
      emailAddress: '',
      password: '',
      passwordCheck: '',
      nickname: '',
      username: '',
      phoneNumber: '',
      validationMessage: {
        emailAddress: '',
        password: '',
        passwordCheck: '',
      },
    };
  },
  methods: {
    setEmail(val) {
      this.emailAddress = val;

      // validation check
      if (
        this.emailAddress.length === 0 ||
        this.emailAddress.split('@').length === 2
      ) {
        this.validationMessage.emailAddress = '';
      } else {
        this.validationMessage.emailAddress = '이메일 형식이 올바르지 않습니다';
      }
    },
    setPassword(val) {
      this.password = val;

      // validation check
      this.setPasswordCheck(this.passwordCheck);

      const length = this.password.length;

      if (length > 0 && length < 6) {
        this.validationMessage.password =
          '비밀번호는 최소 6자 이상이어야 합니다';
        return;
      } else if (
        this.password.match(/[^a-zA-Z0-9!@#$%^&*\-_=+,.?]/g) !== null
      ) {
        this.validationMessage.password =
          '비밀번호에 허용되지 않는 문자가 존재합니다';
        return;
      } else {
        this.validationMessage.password = '';
      }

      let cnt = 0;
      if (this.password.match(/[a-zA-Z]/g) !== null) {
        cnt++;
      }
      if (this.password.match(/[0-9]/g) !== null) {
        cnt++;
      }
      if (this.password.match(/[!@#$%^&*\-_=+,.?]/g) !== null) {
        cnt++;
      }

      if (cnt < 2 && length > 0) {
        this.validationMessage.password =
          '비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 포함되어야 합니다';
        return;
      } else {
        this.validationMessage.password = '';
      }
    },
    setPasswordCheck(val) {
      this.passwordCheck = val;

      // validation check

      if (this.password.length === 0 || this.passwordCheck.length === 0) {
        this.validationMessage.passwordCheck = '';
        return;
      }

      if (this.password !== this.passwordCheck) {
        this.validationMessage.passwordCheck = '비밀번호가 일치하지 않습니다';
      } else {
        this.validationMessage.passwordCheck = '';
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
  margin-top: 36px;
}

@media (max-width: 480px) {
  .register-wrapper {
    width: 72%;
  }
}
</style>
