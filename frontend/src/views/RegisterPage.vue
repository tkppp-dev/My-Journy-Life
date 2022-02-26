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
            maxlength="255"
            @onChangeText="setEmail"
          />
          <div class="input-notification">
            {{ validation.emailAddress.message }}
          </div>
          <custom-input
            class="register-input"
            label="비밀번호 *"
            type="password"
            maxlength="15"
            @onChangeText="setPassword"
          />
          <div class="input-notification">
            {{ validation.password.message }}
          </div>
          <custom-input
            class="register-input"
            label="비밀번호 확인 *"
            type="password"
            maxlength="15"
            @onChangeText="setPasswordCheck"
          />
          <div class="input-notification">
            {{ validation.passwordCheck.message }}
          </div>
          <custom-input
            class="register-input"
            label="별명"
            type="text"
            placeholder="별명을 설정하지 않는 경우 익명으로 설정됩니다"
            @onChangeText="setNickname"
          />
          <div class="input-notification">
            {{ validation.nickname.message }}
          </div>
          <custom-input
            class="register-input"
            label="휴대폰 번호 *"
            type="text"
            placeholder="'-' 포함한 휴대폰 번호 입력"
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
          isValid: false,
          message: '',
        },
        phoneNumber: {
          isValid: false,
        },
      },
    };
  },
  methods: {
    setEmail(val) {
      this.emailAddress = val.toLowerCase();

      // validation check
      const validPattern = /^([\w.\-]+@\w+\.[a-zA-Z]+$)/;
      const isValid = validPattern.test(this.emailAddress);
      this.validation.emailAddress.isValid = isValid;

      if (this.emailAddress.length === 0) {
        this.validation.emailAddress.message = '';
        return;
      }

      if (isValid) {
        this.validation.emailAddress.message = '';
      } else {
        this.validation.emailAddress.message =
          '이메일 형식이 올바르지 않습니다';
      }
    },
    setPassword(val) {
      this.password = val;

      // validation check
      const validPattern = /^[\w!@#$%^&*+,.\?\-]{6,15}$/;
      const isValid = validPattern.test(this.password);
      this.validation.password.isValid = isValid;

      this.setPasswordCheck(this.passwordCheck);

      const length = this.password.length;
      let cnt = 0;
      if (this.password.match(/[a-zA-Z]/g) !== null) {
        cnt++;
      }
      if (this.password.match(/[0-9]/g) !== null) {
        cnt++;
      }
      if (this.password.match(/[!@#$%^&*_=+,.?\-]/g) !== null) {
        cnt++;
      }

      if (length === 0) {
        this.validation.password.message = '';
      } else {
        if (length < 6) {
          this.validation.password.message =
            '비밀번호는 최소 6자 이상이어야 합니다';
        } else if (!isValid) {
          this.validation.password.meesage =
            '비밀번호에 허용되지 않는 특수문자가 존재합니다';
        } else {
          if (cnt < 2) {
            this.validation.password.message =
              '비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 포함되어야 합니다';
          } else {
            this.validation.password.message = '';
          }
        }
      }
    },
    setPasswordCheck(val) {
      this.passwordCheck = val;

      // validation check
      if (this.passwordCheck === this.password) {
        this.validation.passwordCheck.isValid = true;
        this.validation.passwordCheck.message = '';
      } else {
        this.validation.passwordCheck.isValid = false;
        if (this.password.length === 0 || this.passwordCheck.length === 0) {
          this.validation.passwordCheck.message = '';
        } else {
          this.validation.passwordCheck.message =
            '비밀번호가 일치하지 않습니다';
        }
      }
    },
    setNickname(val) {
      this.nickname = val;

      // validation check
      const validPattern = /^[a-zA-Z0-9가-힣]{2,16}$/;
      const isValid = validPattern.test(this.nickname);
      this.validation.nickname.isValid = isValid;

      if (isValid) {
        this.validation.nickname.message = '';
      } else {
        if (this.nickname.length === 0) {
          this.validation.nickname.message = '';
        } else if (this.nickname.match(/[^a-zA-Z0-9가-힣]/g) !== null) {
          this.validation.nickname.message = '유효하지 않은 별명입니다';
        } else {
          this.validation.nickname.message =
            '별명은 한글, 영문, 숫자로만 이루어지고 2-16자 사이여야 합니다';
        }
      }
    },
    setPhoneNumber(val) {
      this.phoneNumber = val

      // validation check
      const validPattern = /^01\d-\d{3,4}-\d{4}$/
      const isValid = validPattern.test(this.phoneNumber)
      this.validation.phoneNumber.isValid = isValid

    }
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
