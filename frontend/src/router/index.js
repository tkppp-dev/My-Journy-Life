import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue'
import Home from '@/views/Home.vue'
import RegisterPage from '@/views/RegisterPage.vue'
import DayReviewWritingPage from '../views/DayReviewWritingPage'

const serviceName = 'My Journey Life'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: {
      title: serviceName
    }
  },
  {
    path: '/register',
    name: 'RegisterPage',
    component: RegisterPage,
    meta: {
      title: `회원가입 : ${serviceName}`
    }
  },
  {
    path: '/review/day/write',
    name: 'DayReviewWritingPage',
    component: DayReviewWritingPage,
    meta: {
      title: `새 여정 일기 작성 : ${serviceName}`
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.afterEach((to, from) => {
  nextTick(() => {
    document.title = to.meta.title;
  });
});

export default router