import { createRouter, createWebHistory } from 'vue-router';
import EntrancePage from '@/views/EntrancePage.vue';
import Signup from '@/views/Signup.vue';
import MainPage from '@/views/MainPage.vue';
import Diary from '@/views/Diary.vue';
import MonthlyDiary from '@/views/MonthlyDiary.vue';
import BottomMenu from '@/components/BottomMenu.vue';
import WeeklyDiary from '@/views/WeeklyDiary.vue';

const routes = [
  {
    mode: 'history',
    path: '/',
    redirect: '/login',
    component: BottomMenu,
    children: [
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
      },
    ],
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
  },
  {
    path: '/diary',
    name: 'Diary',
    component: Diary,
  },
  {
    path: '/monthly',
    name: 'MonthlyDiary',
    component: MonthlyDiary,
  },
  {
    path: '/login',
    name: 'EntrancePage',
    component: EntrancePage,
  },
  {
    path: '/weeklydiary',
    name: 'WeeklyDiary',
    component: WeeklyDiary,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
