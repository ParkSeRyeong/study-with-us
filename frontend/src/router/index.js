import { createRouter, createWebHistory } from 'vue-router'
import EntrancePage from '@/views/EntrancePage.vue'
import Signup from '@/views/Signup.vue'
import MainPage from '@/views/MainPage.vue'
import BottomMenu from '@/components/BottomMenu.vue'

const routes = [
  {
    mode : 'history',
    path: '/',
    redirect: '/login',
    component: BottomMenu,
    children : [
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
      }
    ]
  },
  {
    path: '/login',
    name: 'EntrancePage',
    component: EntrancePage,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
