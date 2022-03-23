import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    user: {
      isLogin: false,
      accessToken: null,
      refreshToken: null,
      info: null,
    },
  },
  mutations: {
    performLogin(state, payload) {
      state.user.isLogin = true;
      state.user.accessToken = payload.accessToken;
      state.user.refreshToken = payload.refreshToken;
    },
    performLogout(state) {
      state.user = {
        isLogin: false,
        accessToken: null,
        refreshToken: null,
        info: null,
      }
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
