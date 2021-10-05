import axios from "axios";
import SERVER from "../../api/api";

const state = {
  userToken: null,
  mainData: {},
  todos: {},
}

const actions = {
  getJWT: function (context, data) {
    context.commit('saveJWT', data)
  },
  getMainData: function (context) {
    axios({
      method: 'get',
      url: `${SERVER.URL}/main/`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken
      }
    })
      .then((res) => {
        console.log(res.data)
        context.commit('GET_MAIN_DATA', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  todoCheck: function (context, data) {
    axios({
      method: 'post',
      url: `${SERVER.URL}/main/toggleTodo`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken
      },
      data: data,
    })
      .then((res) => {
        console.log(res)
        context.commit('TODO_CHECK', data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  todoUpdate: function (context, todos) {
    axios({
      method: 'put',
      url: `${SERVER.URL}/main/update`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken
      },
      data: {'todo': todos},
    })
      .then((res) => {
        console.log(res)
        context.commit('TODO_UPDATE', todos)
      })
      .catch((err) => {
        console.log(err)
      })
  }
}

const mutations = {
  saveJWT: function (state, token) {
    state.userToken = token
  },
  GET_MAIN_DATA: function (state, data) {
    state.mainData = data
    console.log(state.mainData.todo)
    if (!(state.mainData.todo == null)) {
      state.todos = state.mainData.todo
      console.log(Object.keys(state.todos).length)
    }
  },
  TODO_CHECK: function (state, data) {
    state.todos[data.todo] = data.done
  },
  TODO_UPDATE: function (state, data) {
    state.todos = data
  }
}


export default {
  namespaced: true,
  state,
  actions,
  mutations,
};
