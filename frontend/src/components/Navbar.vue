<template>
  <div class="nav-container">
    <div class="nav-content-container">
      <router-link class="title-anker" to="/">
        <div class="title">My Journey Life</div>
      </router-link>
      <div class="right-side-container" v-if="$route.path != '/register'">
        <button class="icon-wrapper" @click="openSearchModal">
          <search-icon class="icon" fillColor="#a02525" :size="28" />
        </button>
        <div v-if="isLogin" class="nav-btn-wrapper">
          <button
            class="write-btn"
            @click="clickWriteDropdownBtn"
            @blur="closeWriteDropdown"
          >
            새 리뷰 쓰기
          </button>
        </div>
        <div v-if="isFocusOnWriteDropdown" class="nav-dropdown-wrapper">
          <div class="nav-dropdown write-dropdown">
            <div class="nav-dropdown-item" @mousedown="onClickWrite('diary')">
              여정 일기
            </div>
            <div class="nav-dropdown-item">교통편 리뷰</div>
            <div class="nav-dropdown-item">여행지 리뷰</div>
          </div>
        </div>
        <button
          v-if="isLogin"
          class="user-icon"
          @click="clickUserDropdownBtn"
          @blur="closeUserDropdown"
        >
          <user-icon fillColor="#a02525" :size="29" />
        </button>
        <div v-if="isFocusOnUserDropdown" class="nav-dropdown-wrapper">
          <div class="nav-dropdown user-dropdown">
            <div class="nav-dropdown-item">마이페이지</div>
            <div class="nav-dropdown-item">임시글</div>
            <div class="nav-dropdown-item">메세지</div>
            <div class="nav-dropdown-item">알림</div>
            <div class="nav-dropdown-item" @mousedown.stop="logoutForce">
              로그아웃
            </div>
          </div>
        </div>
        <div v-if="!isLogin" class="nav-btn-wrapper">
          <button class="login-btn" @click="loginForce">로그인</button>
        </div>
      </div>
    </div>
    <teleport to="body">
      <search-modal :show="searchModalVisible" @close="closeSearchModal" />
    </teleport>
    <teleport to="body">
      <login-modal :show="loginModalVisible" @close="closeLoginModal" />
    </teleport>
  </div>
</template>

<script>
import SearchModal from './modal/SearchModal';
import SearchIcon from 'vue-material-design-icons/Magnify.vue';
import NotificationIcon from 'vue-material-design-icons/BellOutline.vue';
import UserIcon from 'vue-material-design-icons/AccountCircle.vue';
import LoginModal from './modal/LoginModal.vue';
import _axios from '../util/_axios';

export default {
  name: 'Navbar',
  components: {
    SearchModal,
    SearchIcon,
    NotificationIcon,
    UserIcon,
    LoginModal,
  },
  data() {
    return {
      searchModalVisible: false,
      loginModalVisible: false,
      isFocusOnWriteDropdown: false,
      isFocusOnUserDropdown: false,
    };
  },
  methods: {
    openSearchModal() {
      this.searchModalVisible = true;
    },
    closeSearchModal() {
      this.searchModalVisible = false;
    },
    openLoginModal() {
      this.loginModalVisible = true;
    },
    closeLoginModal() {
      this.loginModalVisible = false;
    },
    clickWriteDropdownBtn() {
      this.isFocusOnWriteDropdown = !this.isFocusOnWriteDropdown;
    },
    closeWriteDropdown() {
      this.isFocusOnWriteDropdown = false;
    },
    clickUserDropdownBtn() {
      this.isFocusOnUserDropdown = !this.isFocusOnUserDropdown;
    },
    closeUserDropdown() {
      this.isFocusOnUserDropdown = false;
    },

    async logout() {
      try {
        const res = await _axios.post('/api/logout', {
          accessToken: this.$store.state.user.accessToken,
        });

        this.$store.commit('performLogout');
        this.$router.push('/');
      } catch (e) {
        alert('로그아웃에 실패했습니다. 다시 시도해주세요');
      }
    },
    loginForce() {
      this.$store.commit('performLogin', {
        accessToken: 'accessToken',
        refreshToken: 'refreshToken',
      });
    },
    logoutForce() {
      this.$store.commit('performLogout');
    },

    onClickWrite(type) {
      if (type === 'diary') {
        this.$router.push('/review/day/write');
      } else if (type === 'transportation') {
      } else if (type === 'spot') {
      }
    },
  },
  computed: {
    isLogin() {
      if (this.$store.state.user.isLogin === true) return true;
      else return false;
    },
  },
};
</script>

<style>
.nav-container {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 56px;
  background-color: #e2d8be;
}

.nav-content-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 960px;
  height: 100%;
  padding: 0 8px;
}

@media (max-width: 500px) {
  .nav-container {
    display: flex;
    justify-content: center;
    width: 100%;
    height: 96px;
    background-color: #e2d8be;
  }
  .nav-content-container {
    flex-wrap: wrap;
  }
  .title-anker {
    width: 100%;
    text-align: center;
    margin: 6px 0px;
  }
  .right-side-container {
    width: 100%;
    justify-content: flex-end;
  }
}

.title-anker {
  text-decoration: none;
}

.title {
  font-family: 'multicolore_regular', 'sans-serif';
  font-size: 30px;
  color: #a02525;
}

.right-side-container {
  display: flex;
}

.icon-wrapper {
  text-decoration: none;
  padding: 5px;
  margin-right: 8px;
  border-radius: 50%;
}
.icon-wrapper:hover {
  background-color: rgba(255, 255, 255, 0.3);
}
.icon-wrapper:active {
  opacity: 0.5;
}

.nav-btn-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-btn {
  width: 72px;
  height: 30px;
  padding: 2px 0;
  background-color: rgba(255, 255, 255, 0.6);
  border: none;
  border-radius: 50px;
  font-size: 16px;
  color: #a02525;
  font-weight: bold;
  text-align: center;
}

.write-btn {
  width: 100px;
  height: 30px;
  padding: 2px 0;
  background-color: rgba(255, 255, 255, 0.6);
  border: none;
  border-radius: 50px;
  font-size: 16px;
  color: #a02525;
  font-weight: bold;
  text-align: center;
}

.user-icon {
  margin-left: 8px;
  padding: 5px;
}

.nav-dropdown-wrapper {
  position: relative;
}

.nav-dropdown {
  z-index: 999;
  position: absolute;
  margin-top: 54px;
  right: 0px;

  background-color: white;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
}

.write-dropdown {
  width: 150px;
}

.user-dropdown {
  width: 120px;
}

.nav-dropdown-item {
  padding: 15px;
  font-size: 16px;
  font-weight: bold;
  color: #a02525;

  cursor: pointer;
}

.nav-dropdown-item:hover {
  background-color: rgba(0, 0, 0, 0.05);
}
</style>
