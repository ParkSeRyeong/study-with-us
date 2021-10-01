import { createStore } from 'vuex';
import login from './modules/login';
import diary from './modules/diary';
import createPersistedState from 'vuex-persistedstate';

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
    diary,
  },
});
