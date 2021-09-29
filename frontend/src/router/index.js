import { createRouter, createWebHistory } from 'vue-router'
import EntrancePage from '@/views/EntrancePage.vue'
import Signup from '@/views/Signup.vue'
import MainPage from '@/views/MainPage.vue'
import MonthlyDiary from '@/views/MonthlyDiary.vue'

const routes = [
  {
    path: '/',
    name: 'EntrancePage',
    component: EntrancePage,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/main',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/selfstudy',
    name: 'SelfStudy',
    component: () => import('@/views/SelfStudy.vue'),
  },{
    path: '/monthly',
    name: 'MonthlyDiary',
    component: MonthlyDiary,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
