<template>
  <v-app-bar id="header" app color="white" role="navigation">
    <div class="header__logo">
      <router-link to="/home" id="header__text">SSAFYing</router-link>
    </div>
    <HeaderMenus />
    <UserControl />

    <!-- 풀다운 메뉴 -->
    <div class="pulldown">
      <div class="pulldown__header">
        <div class="alert">
          <!-- 라우터 달 예정 -->
          <font-awesome-icon icon="bell" />
        </div>
        <div v-if="userInfo">
          <router-link
            :to="{
              name: 'Profile',
              params: { nickname: userInfo.nickname }
            }"
            style="color:#343a40"
          >
            <v-avatar color="white" size="50" rounded></v-avatar>
            <div>
              <span>안녕하세요 {{ userInfo.nickname }}님</span>
              <div style="font-size:12px; font-weight:bold">
                {{ userInfo.email }}
              </div>
            </div>
          </router-link>
        </div>
        <div v-else>
          <span>안녕하세요 SSAFYing 님</span>
        </div>
      </div>
      <div class="pulldown__menu" v-for="(title, i) in titles" :key="i">
        <router-link :to="links[i]">
          <div @click="hamburgerOff">
            <font-awesome-icon class="mr-3" :icon="icons[i]" />
            <span>{{ title }}</span>
          </div>
        </router-link>
      </div>
      <div class="pulldown__usermenu">
        <div class="btn">
          <button @click="onClickLogout">
            <font-awesome-icon class="mr-1" icon="door-open" />
            <span>로그아웃</span>
          </button>
        </div>
      </div>
    </div>
  </v-app-bar>
</template>

<script>
import HeaderMenus from "@/components/common/header/HeaderMenus.vue";
import UserControl from "@/components/common/header/UserControl.vue";
import { mapState } from "vuex";

export default {
  name: "Header",
  data: () => {
    return {
      userInfo: {},
      icons: ["chalkboard", "users", "bullhorn", "shopping-cart"],
      links: [
        "/board/community",
        "/group/all",
        "/board/information",
        "/board/market"
      ],
      titles: ["게시판", "모임", "정보", "싸피장터"]
    };
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
  },
  computed: {
    ...mapState(["isLogin"])
  },
  components: {
    HeaderMenus,
    UserControl
  },
  methods: {
    onClickLogout() {
      this.$store
        .dispatch("logout")
        .then(() => this.$router.push({ name: "Login" }));
    },
    hamburgerOff: function() {
      const menu = document.querySelector(".pulldown");
      console.log(menu);
      if (menu.classList.contains("active")) menu.classList.remove("active");
    }
  }
};
</script>
<style scoped>
@import "../../assets/css/header.css";
</style>
