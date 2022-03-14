import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate'

const defaultUserData = {
  isLogin: false,
  accessToken: null,
  refreshToken: null,
  info: null,
};

export default createStore({
  state: {
    user: defaultUserData,
  },
  mutations: {
    performLogin(state, payload) {
      state.user.isLogin = true;
      state.user.accessToken = payload.accessToken;
      state.user.refreshToken = payload.refreshToken;
    },
    performLogout(state) {
      state.user = defaultUserData
    },
    setUserInfo(state, payload) {
      state.user.info = payload
    },
    setReissuedAccessToken(state, payload){
      state.user.accessToken = payload
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState()
  ]
});
