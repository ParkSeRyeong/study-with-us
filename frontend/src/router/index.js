import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '@/views/MainPage.vue'

const routes = [
  {
    path: '/e',
    name: 'EntrancePage',
    component: () => import('@/views/EntrancePage.vue')
  },
  {
    path: '/main',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/',
    name: 'SelfStudy',
    component: () => import('@/views/SelfStudy.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
