// import jwt_decode from "jwt-decode";
import { findById } from "@/api/user.js";
import axios from "axios";
import { axiosGetMain } from "@/api/redis.js";

export default {
  writeArticle({ commit }, articleItem) {
    commit("WRITE_ARTICLE", articleItem);
  },
  deleteArticle({ commit }, articleItem) {
    commit("DELETE_ARTICLE", articleItem);
  },
  updateArticle({ commit }, articleItem) {
    commit("UPDATE_ARTICLE", articleItem);
  },
  createComment({ commit }, commentItem) {
    commit("CREATE_COMMENT", commentItem);
  },
  commentDelete({ commit }, commentItem) {
    commit("DELETE_COMMENT", commentItem);
  },
  commentUpdate({ commit }, commentItem) {
    commit("UPDATE_COMMENT", commentItem);
  },
  async getMemberInfo({ commit }, token) {
    // console.log(token);
    // let decode = jwt_decode(token);
    // console.log(decode.userId);

    await findById(
      // decode.userId,
      token,
      response => {
        if (response.data.message === "success") {
          // console.log(response.data.userDto);
          commit("SET_USER_INFO", response.data.userDto);
          // router.go(router.currentRoute);
        } else {
          console.log("유저 정보 없음!!");
          this.$router.push("/");
        }
      },
      error => {
        console.log(error);
      }
    );
  },
  logout({ commit }) {
    commit("LOGOUT");
  },
  async getSideBarData({ commit }) {
    let commuItems = { anonymous: null, free: null, grade: null };
    let infoItems = { it: null, job: null, life: null };
    let recommends = {};
    let weatherItem = {};

    await axiosGetMain(
      res => {
        // console.log(res.data);
        recommends = res.data.recommend;
        commuItems.anonymous = res.data.anonymous;
        commuItems.free = res.data.free;
        commuItems.grade = res.data.grade;
        infoItems.it = res.data.it;
        infoItems.job = res.data.job;
        infoItems.life = res.data.life;
        // console.log(recommends);
      },
      err => {
        console.log(err);
      }
    );
    await axios
      .get(
        `http://api.openweathermap.org/data/2.5/weather?lat=35.2&lon=126.8&appid=${process.env.VUE_APP_WEATHER_KEY}&lang=kr`
      )
      .then(res => {
        console.log(res.data);
        weatherItem = res.data;
        // console.log(typeof res.data);
      })
      .catch(err => {
        console.log(err);
      });
    let sideBarItmes = {
      commuItems,
      infoItems,
      recommends,
      weatherItem
    };
    commit("GET_SIDEBAR_DATA", sideBarItmes);
  }
};
