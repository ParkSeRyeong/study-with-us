import SERVER from "../../api/api";
import axios from "axios";

const state = {
  isStudy: [],
  othertime: "",
  focustime: "",
  isWeekly: false,
};

// actions
const actions = {
  getMonthly({ commit }, info) {
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
        commit("GET_MONTHLY_STUDY", res.data);
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
    state.isWeekly = !state.isWeekly;
    state.isStudy = data.dailyColor;
    state.othertime = data.totalOtherTime;
    state.focustime = data.totalFocusTime;
  },
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
};
