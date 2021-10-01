import SERVER from "../../api/api";
import axios from "axios";

const state = {
  isStudy: [],
};

// actions
const actions = {
  getMonthly({ commit }, info) {
    console.log("store - diary.js - getMonthly 진입");

    // back에서 monthly 정보를 가져오기?
    axios({
      method: "get",
      url: `${SERVER.URL}` + "/diary/monthly/" + info.day,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: info.token,
      },
    })
      .then((res) => {
        console.log(res);
        commit("GET_MONTHLY_STUDY", res.data.dailyColor);
      })
      .catch((err) => {
        console.log(err);
        alert("다시 시도해주십시오.");
      });
  },
};

// mutations
const mutations = {
  GET_MONTHLY_STUDY(state, data) {
    state.isStudy = data;
  },
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
};
