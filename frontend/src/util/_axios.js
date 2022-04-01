import axios from 'axios';
import store from '../store';

const instance = axios.create({
  //baseURL: `http://localhost:8080'`
});

const prefix = 'Bearer ';

instance.interceptors.request.use(
  async (config) => {
    const accessToken = store.state.user.accessToken;

    config.headers = {
      Authorization: accessToken === null ? null : prefix + accessToken,
    };
    return config;
  },
  (error) => {
    console.log(error);
    Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const orignalRequest = error.config;
    if (error.response.status === 401 && !orignalRequest._retry) {
      console.log('AccessToken Expired.');
      orignalRequest._retry = true;
      try {
        const resp = await axios.post('/api/login/auth/reissue', {
          emailAddress: store.state.user.emailAddress,
          accessToken: store.state.user.accessToken,
          refreshToken: store.state.user.refreshToken,
        });

        store.commit('setReissuedAccessToken', resp.data.accessToken);
        orignalRequest.headers['Authorization'] =
          prefix + resp.data.accessToken;
        return axios(orignalRequest);
      } catch (error) {
        console.log(error);
        store.commit('performLogout');
      }
    } else {
      console.log(error);
    }
    return Promise.reject(error);
  }
);

export default instance;
