import { createRouter, createWebHistory } from 'vue-router'
import EntrancePage from '@/views/EntrancePage.vue'
import Signup from '@/views/Signup.vue'
import MainPage from '@/views/MainPage.vue'
import MonthlyDiary from '@/views/MonthlyDiary.vue'
import BottomMenu from '@/components/BottomMenu.vue'

const routes = [
  {
    mode : 'history',
    path: '/',
    redirect: '/signin',
    component: BottomMenu,
    children : [
      {
        path: '/main',
        name: 'MainPage',
        component: MainPage,
      },
      {
        path: '/monthlyDiary',
        name: 'MonthlyDiary',
        component: MonthlyDiary,
      },
      {
        path: '/selfstudy',
        name: 'SelfStudy',
        component: () => import('@/views/SelfStudy.vue'),
      }
    ]
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/signin',
    name: 'Signin',
    component: EntrancePage,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
