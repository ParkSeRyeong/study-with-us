import SERVER from "../../api/api";
import axios from "axios";

const state = {
    Studytime: [],
};

// actions
const actions = {
    getDaily({ commit }, info) {
        console.log(info.day);
        axios({
            method: "get",
            url: `${SERVER.URL}/diary/daily/${info.day}`,
            headers: {
                "Access-Control-Allow-Origin": "*",
                Authorization: info.token,
            },
            //day:info.day,
        })
            .then((res) => {
                console.log(res);
                commit("GET_DAILY_STUDY", res.data);
            })
            .catch((err) => {
                console.log(err);
                alert("다시 시도해주십시오.");
            });
    },
};

// mutations
const mutations = {
    GET_DAILY_STUDY(state, data) {
        state.Studytime = data;
    },
};

export default {
    namespaced: true,
    state,
    actions,
    mutations,
};
