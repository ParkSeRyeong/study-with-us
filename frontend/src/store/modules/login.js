import axios from 'axios'
import createPersistedState from 'vuex-persistedstate'
import jwt_decode from 'jwt-decode'


const state = {
  userToken: null,
}

const actions = {
  getJWT: function (context, credentials) {
    axios({
      method: 'post',
      url: 'http://j5a206.p.ssafy.io:8080/user/login',
      data: credentials,
    })
      .then((res) => {
        console.log(res.data.token)
        context.commit('saveJWT', res.data.token)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  deleteJWT: function (context) {
    context.commit('deleteJWT')
  },
}

const mutations = {
  saveJWT: function (state, token) {
    state.userToken = token
  },
  deleteJWT: function (state) {
    state.userToken = null
  }
}

const getters = {
  decodedToken: function (state) {
    if (state.userToken) {
      console.log(jwt_decode(state.userToken))
      return jwt_decode(state.userToken)
    }
    else {
      return null
    }
  }
}

export default {
  namespaced: true,
  plugins: [createPersistedState()],
  state,
  actions,
  mutations,
  getters,
}