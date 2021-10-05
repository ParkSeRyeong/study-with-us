import SERVER from "../../api/api";
import axios from "axios";

const state = {
  studyInfo: [],
  otherInfo:[],
  isWeekly: true,
  focustime: "",
  othertime: "",
  DayAndWeek: "",
};

// actions
const actions = {
  getWeekly({ commit }, info) {
    // back에서 monthly 정보를 가져오기?
    axios({
      method: "get",
      url: `${SERVER.URL}` + "/diary/weekly/" + info.day,
      headers: {
        "Access-Control-Allow-Origin": "*",
        Authorization: info.token,
      },
    })
      .then((res) => {
        console.log(res);
        commit("GET_Weekly_Study", res.data);
      })
      .catch((err) => {
        console.log(err);
        alert("다시 시도해주십시오.");
      });
  },
};

// mutations
const mutations = {
  GET_Weekly_Study(state, data) {
    state.isWeekly = !state.isWeekly;
    state.focustime = data[0].totalFocusTime;
    state.othertime = data[0].totalOtherTime;
    state.DayAndWeek = data[0].dayAndWeek;
    if(state.studyInfo.length===0) {
      for (var i = 0; i < 7; i++) {
        state.studyInfo.push(data[i].focusPercent);
        state.otherInfo.push(data[i].otherPercent);
      }
    }
    else{
      for (var j = 0; j < 7; j++) {
        state.studyInfo[j] = data[j].focusPercent;
        state.otherInfo[j] = data[i].otherPercent;
      }
    }
    console.log(state.studyInfo)
    console.log(state.otherInfo)
  },
};

export default {
  namespaced: true,
  state,
  actions,
  mutations,
};
