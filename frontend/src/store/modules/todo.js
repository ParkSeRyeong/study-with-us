import axios from 'axios'
import SERVER from '../../api/api'

const state = {
  userToken: null,
  mainData: {}
  
}

const actions = {
  todoCheck: function (context, data) {
    axios({
      method: 'post',
      url: `${SERVER.URL}/main/`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken
      },
      data: data,
    })
      .then((res) => {
        console.log(res)
        context.commit('TODO_CHECK')
      })
      .catch((err) => {
        console.log(err)
      })
  },
  todoUpdate: function (context, data) {
    axios({
      method:
    })
  }
}

const mutations = {
  TODO_CHECK: function () {
  },
  GET_MAIN_DATA: function (state, data) {
    state.mainData = data
  }
}


export default {
  namespaced: true,
  state,
  actions,
  mutations,
}