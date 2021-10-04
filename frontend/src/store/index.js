import { createStore } from 'vuex';
import login from './modules/login';
import monthlydiary from './modules/monthlydiary';
import mainPage from './modules/mainPage';
// import diary from "./modules/diary";
import daily_diary from './modules/daily_diary';
import createPersistedState from 'vuex-persistedstate';
import weeklydiary from './modules/weeklydiary';

export default createStore({
  plugins: [
    createPersistedState({
      paths: ['login'],
    }),
  ],
  state: {
    window_width: 0,
    window_height: 0,
  },
  mutations: {},
  actions: {},
  getters: {},
  modules: {
    login,
    monthlydiary,
    mainPage,
    daily_diary,
    weeklydiary,
    // diary,
  },
});
