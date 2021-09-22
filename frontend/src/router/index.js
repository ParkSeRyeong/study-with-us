import { createRouter, createWebHistory } from 'vue-router'
import EntrancePage from '@/views/EntrancePage.vue'
import MainPage from '@/views/MainPage.vue'

const routes = [
  {
    path: '/',
    name: 'EntrancePage',
    component: EntrancePage,
  },
  {
    path: '/main',
    name: 'MainPage',
    component: MainPage,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
