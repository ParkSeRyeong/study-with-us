import axios from 'axios'
import jwt_decode from 'jwt-decode'


const state = {
  userToken: null,
}

const actions = {
  getJWT: function (context, credentials) {
    axios({
      method: 'post',
      url: 'http://localhost:8080/user/login',
      data: credentials,
    })
      .then((res) => {
        console.log(res.data.jwt)
        context.commit('saveJWT', res.data.jwt)
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
    console.log(jwt_decode(state.userToken))
    if (state.userToken) {
      return jwt_decode(state.userToken)
    }
    else {
      return null
    }
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters,
}