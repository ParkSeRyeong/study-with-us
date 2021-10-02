import axios from "axios";
import SERVER from "../../api/api";

const state = {
  userToken: null,
  mainData: {},
};

const actions = {
  getJWT: function(context, data) {
    context.commit("saveJWT", data);
  },
  getMainData: function(context) {
    axios({
      method: "get",
      url: `${SERVER.URL}` + `/main`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken,
      },
    })
      .then((res) => {
        context.commit("GET_MAIN_DATA", res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  getTest({ commit }) {
    axios({
      method: "get",
      url: `${SERVER.URL}` + `/main`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: state.userToken,
      },
    })
      .then((res) => {
        commit("GET_MAIN_DATA", res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};

const mutations = {
  saveJWT: function(state, token) {
    state.userToken = token;
  },
  GET_MAIN_DATA: function(state, data) {
    state.mainData = data;
  },
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
};
