import Group from "@/components/group/Group.vue";
// import store from "@/vuex/store";

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
// const onlyAuthUser = async (to, from, next) => {
//   // let token = localStorage.getItem("access-token");
//   // if (store.state.userInfo == null && token) {
//   //   await store.dispatch("GET_MEMBER_INFO", token);
//   // }
//   if (store.state.isLogin === false) {
//     alert("로그인이 필요한 페이지입니다..");
//     // next({ name: "login" });
//     this.$router.push({ name: "Login" });
//   } else {
//     next();
//   }
// };

export default {
  name: "Group",
  path: "/group",
  component: Group,
  children: [
    // community - 게시판
    // 게시판 메인
    {
      path: "all",
      name: "All",
      component: () => import("@/components/group/chat/All.vue")
    },
    {
      path: "free",
      name: "Free",
      component: () => import("@/components/group/chat/Free.vue")
    },
    {
      path: "anonymous",
      name: "Anonymous",
      component: () => import("@/components/group/chat/Anonymous.vue")
    },
    {
      path: "study",
      name: "Study",
      component: () => import("@/components/group/chat/Study.vue")
    },
    {
      path: `chatroom/:chatRoomId/:name/:nickname`,
      name: "ChatRoom",
      component: () => import("@/components/group/ChatRoom.vue"),
      props: true
    }
  ]
};
