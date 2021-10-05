import axios from "axios";
import jwt_decode from "jwt-decode";
import SERVER from "../../api/api";

const state = {
  userToken: null,
};

const actions = {
  getJWT: function(context, credentials) {
    axios({
      method: "post",
      url: `${SERVER.URL}/user/login`,
      data: credentials
    })
      .then((res) => {
        console.log(res.data.jwt)
        context.commit("saveJWT", res.data.jwt)
      })
      .catch((err) => {
        console.log(err);
      });
  },
  deleteJWT: function(context) {
    context.commit("deleteJWT");
  },
};

const mutations = {
  saveJWT: function(state, token) {
    state.userToken = token;
    console.log(state.userToken)
  },
  deleteJWT: function(state) {
    state.userToken = null;
  },
};

const getters = {
  decodedToken: function (state) {
    if (state.userToken) {
      console.log(state.userToken);
      return jwt_decode(state.userToken);
    } else {
      return null;
    }
  },
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters,
};
