<template>
  <base-modal :show="show">
    <template v-slot:modal-content>
      <div class="login-container">
        <div class="login-wrapper" @click.stop>
          <div class="title">로그인</div>
          <div class="input-box">
            <custom-input type="text" label="이메일" :input="emailAddress" />
          </div>
          <div class="input-box">
            <custom-input
              type="password"
              label="비밀번호"
              :input="password"
              @keyup:enter="console.log(password)"
            />
          </div>
          <button class="login-submit-btn">로그인</button>
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
            <button class="register-anker">회원가입 하기</button>
          </div>
        </div>
      </div>
    </template>
  </base-modal>
</template>

<script>
import BaseModal from './BaseModal.vue';
import CustomInput from './Input.vue';

export default {
  components: { BaseModal, CustomInput },
  props: {
    show: Boolean,
  },
  data() {
    return {
      emailAddress: '',
      password: '',
    };
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
  width: 480px;
  padding: 0 65px;

  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
}

.title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  padding: 50px;
}

.input-box {
  margin-bottom: 24px;
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
  margin: 12px 0;
  background-color: #e2d8be;

  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);

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
}
</style>
