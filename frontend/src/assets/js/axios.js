import axios from "axios";
import login from "@/store/modules/login.js";

const serverURL = "http://localhost:8080/api";
// const serverURL = 'https://j5a206.p.ssafy.io/api'
// http://localhost:8080

console.log(login.state);

// axios 객체 생성
export default axios.create({
  baseURL: serverURL,
  headers: {
    "Access-Control-Allow-Origin": "*",
    Authorization: login.state.userToken,
  },
  withCredentials: true,
});
