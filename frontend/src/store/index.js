import { createStore } from 'vuex'
import login from './modules/login'
import createPersistedState from 'vuex-persistedstate'


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
  mutations: {
  },
  actions: {
  },
  getters: {
    
  },
  modules: {
    login,
  },

})
