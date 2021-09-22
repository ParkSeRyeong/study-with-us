import { createStore } from 'vuex'
import login from './modules/login'

export default createStore({
  state: {
    window_width: 0,
    window_height: 0,
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    login,
  }
})
