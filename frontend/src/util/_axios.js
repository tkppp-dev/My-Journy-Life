import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8080'
})

function getAccessToken(){
  return ""
}

function getRefreshToken(){
  return ""
}

instance.interceptors.request.use(
  async config => {
    const token = getAccessToken()
    config.headers = {
      'Authorization': `Bearer ${token}` 
    }
    return config
  },
  (error) => {
    Promise.reject(error)
  }
)

instance.interceptors.response.use(
  response => response,
  async (error) => {
    const orignalRequest = error.config
    if(error.response.status === 401 && !orignalRequest._retry){
      console.log("AccessToken Expired.")

      try{
        const resp = await axios.post("/api/login/auth/reissue", {
          accessToken: getAccessToken(),
          refreshToken: getRefreshToken()
        })

        if(resp.data.success = false){
          return resp.data.message
        }
        
        // access 토큰 저장 로직 필요

        orignalRequest.headers['Authorization'] = `Bearer ${resp.data.accessToken}`
        return axios(orignalRequest)
      } catch(error){
        console.log(error)
      } 
    }
    return Promise.reject(error)
  }
)

export default instance