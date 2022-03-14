<template>
  <div class="nav-container">
    <div class="nav-content-container">
      <router-link class="title-anker" to="/"
        ><div class="title">My Journy Life</div></router-link
      >
      <div class="right-side-container" v-if="$route.path != '/register'">
        <button class="icon-wrapper" @click="openSearchModal">
          <search-icon class="icon" fillColor="#a02525" :size="27" />
        </button>
        <button v-if="isLogin" class="icon-wrapper">
          <notification-icon class="icon" fillColor="#a02525" :size="27" />
        </button>
        <button v-if="isLogin" class="user-icon" @click="mockLogin">
          <user-icon fillColor="#a02525" :size="29" />
        </button>
        <div v-if="!isLogin" class="login-btn-wrapper">
          <button class="login-btn" @click="openLoginModal">로그인</button>
        </div>
        <div v-if="isLogin" class="login-btn-wrapper">
          <button class="logout-btn" @click="logout">로그아웃</button>
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
import SearchModal from '@/components/SearchModal';
import SearchIcon from 'vue-material-design-icons/Magnify.vue';
import NotificationIcon from 'vue-material-design-icons/BellOutline.vue';
import UserIcon from 'vue-material-design-icons/AccountCircle.vue';
import LoginModal from './LoginModal.vue';
import _axios from '../util/_axios'

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
    async logout(){
      const res = await _axios.post('/api/logout', {
        accessToken: this.$store.state.user.accessToken
      })

      if(res.data.success){
        this.$store.commit('performLogout')
        this.$router.push('/')
      } else {
        alert('로그아웃에 실패했습니다. 다시 시도해주세요')
      }
    },
    logoutForce(){
      this.$store.commit('performLogout')
    }
  },
  computed: {
    isLogin(){
      if(this.$store.state.user.isLogin === true) return true
      else return false
    }
  }
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
  width: 1200px;
  height: 100%;
  padding: 0 8px;
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
  margin-right: 10px;
  border-radius: 50%;
}
.icon-wrapper:hover {
  background-color: rgba(255, 255, 255, 0.3);
}
.icon-wrapper:active {
  opacity: 0.5;
}

.login-btn-wrapper {
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

.logout-btn {
  width: 80px;
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
  padding-top: 3px;
  margin-right: 10px;
}
</style>
