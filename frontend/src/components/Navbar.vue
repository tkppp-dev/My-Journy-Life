<template>
  <div class="nav-container">
    <div class="nav-content-container">
      <div class="title">My Journy Life</div>
      <div class="right-side-container">
        <button class="icon-wrapper" @click="openSearchModal">
          <search-icon class="icon" fillColor="#a02525" :size="27" />
        </button>
        <button v-if="isLogin" class="icon-wrapper">
          <notification-icon class="icon" fillColor="#a02525" :size="27" />
        </button>
        <div v-if="!isLogin" class="login-btn-wrapper">
          <button class="login-btn" @click="mockLogin">
            로그인
          </button>
        </div>
        <button v-if="isLogin" class="user-icon" @click="mockLogin">
          <user-icon fillColor="#a02525" :size="29"/> 
        </button>
      </div>
    </div>
    <teleport to="body">
      <search-modal :show="searchModalVisible" @close="closeSearchModal"/>
    </teleport>
  </div>
</template>

<script>
import SearchModal from '@/components/SearchModal'
import SearchIcon from 'vue-material-design-icons/Magnify.vue';
import NotificationIcon from 'vue-material-design-icons/BellOutline.vue';
import UserIcon from 'vue-material-design-icons/AccountCircle.vue'

export default {
  name: 'Navbar',
  components: {
    SearchModal,
    SearchIcon,
    NotificationIcon,
    UserIcon
  },
  data() {
    return {
      searchModalVisible: false,
      isLogin: false
    };
  },
  methods: {
    openSearchModal(){
      this.searchModalVisible = true
    },
    closeSearchModal(){
      this.searchModalVisible = false
    },
    mockLogin(){
      this.isLogin = !this.isLogin
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
}

.title {
  font-family: 'multicolore_regular', 'sans-serif';
  font-size: 30px;
  color: #a02525;
}

.right-side-container {
  display: flex;  
}

button {
  border: none;
  background-color: transparent;
}

.icon-wrapper {
  text-decoration: none;
  padding: 5px;
  margin-right: 10px;
  border-radius: 50%;
}
.icon-wrapper:hover {
  background-color: rgba(255,255,255, 0.3);
}
.icon-wrapper:active {
  opacity: .5;
}

.login-btn-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-btn {
  width: 72px;
  padding: 2px 0;
  background-color: rgba(255,255,255, 0.6);
  border: none;
  border-radius: 50px;
  font-size: 16px;
  color: #a02525;
  font-weight: bold;
  text-align: center;
}

.user-icon {
  padding-top: 3px;
}
</style>
