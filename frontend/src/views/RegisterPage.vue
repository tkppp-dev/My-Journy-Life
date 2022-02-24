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
            :input="emailAddress"
          />
          <custom-input
            class="register-input"
            label="비밀번호 *"
            type="password"
            :input="password"
          />
          <custom-input
            class="register-input"
            label="비밀번호 확인 *"
            type="password"
            :input="passwordCheck"
          />
          <custom-input
            class="register-input"
            label="별명"
            type="text"
            :input="nickname"
          />
          <div class="input-notification">
            별명을 설정하지 않는 경우 익명으로 설정됩니다
          </div>
          <custom-input
            class="register-input"
            label="이름 *"
            type="text"
            :input="password"
          />
          <custom-input
            class="register-input"
            label="휴대폰 번호 *"
            type="text"
            :input="password"
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
    };
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

.input-notification {
  margin-top: 8px;
  font-size: 15px;
  font-weight: bold;
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
