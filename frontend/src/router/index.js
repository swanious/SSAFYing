import Vue from "vue";
import VueRouter from "vue-router";

import Login from "../views/Login.vue";
import Signup from "../views/Signup.vue";
import Profile from "../views/Profile.vue";

import board from "./board.js";
import group from "./group.js";
import admin from "./admin.js";
import store from "@/vuex/store";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // let token = localStorage.getItem("access-token");
  // if (store.state.userInfo == null && token) {
  //   await store.dispatch("GET_MEMBER_INFO", token);
  // }
  if (store.state.isLogin === false) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "Login" });
  } else {
    next();
  }
};

// const fetchData = async (to, from, next) => {
//   store.dispatch("getSideBarData");
//   next();
// };

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login
  },
  {
    path: "/home",
    name: "Home",
    beforeEnter: async (to, from, next) => {
      await store.dispatch("getSideBarData");
      if (store.state.isLogin === false) {
        alert("로그인이 필요한 페이지입니다..");
        // next({ name: "login" });
        router.push({ name: "Login" });
      } else {
        next();
      }
    },
    component: () => import("../views/Home2.vue")
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup
  },
  {
    path: "/profile/:nickname/",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: Profile
  },
  {
    path: "/user/changeInfo/:nickname",
    name: "ChangeUserInfo",
    beforeEnter: onlyAuthUser,
    component: () => import("@/components/user/profile/ChangeUserInfo")
  },
  {
    path: "/search/:tag",
    name: "SearchResult",
    beforeEnter: async (to, from, next) => {
      // console.log(this.$route.params);
      // await store.dispatch("getSideBarData");
      if (store.state.isLogin === false) {
        alert("로그인이 필요한 페이지입니다..");
        // next({ name: "login" });
        router.push({ name: "Login" });
      } else {
        next();
      }
    },
    component: () => import("@/components/common/header/SearchResult")
  },
  {
    ...board,
    beforeEnter: onlyAuthUser
  },
  {
    ...group,
    beforeEnter: onlyAuthUser
  },
  admin
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
