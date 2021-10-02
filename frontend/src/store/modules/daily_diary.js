import SERVER from "../../api/api";
import axios from "axios";

const state = {
    Studytime: [],
};

// actions
const actions = {
    getDaily({ commit }, info) {
        axios({
            method: "get",
            url: `${SERVER.URL}/diary/daily/${info.day}`,
            headers: {
                "Access-Control-Allow-Origin": "*",
                Authorization: info.token,
            },
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
        if(state.Studytime.length === 0 ) {
            state.Studytime.push(data.focustime);
            state.Studytime.push(data.phonetime);
            state.Studytime.push(data.sleeptime);
        }
        else{
            state.Studytime[0] = data.focustime;
            state.Studytime[1] = data.phonetime;
            state.Studytime[2] = data.sleeptime;
        }
    },
};

export default {
    namespaced: true,
    state,
    actions,
    mutations,
};
