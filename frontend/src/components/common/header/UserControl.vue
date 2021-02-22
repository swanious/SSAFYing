<template>
  <div class="header__user">
    <!-- 알림 -->
    <Alert />

    <!-- 프로필 -->
    <UserTab />

    <!-- 햄버거바 -->
    <Hamburger />
  </div>
</template>

<script>
import Alert from "./Alert.vue";
import UserTab from "./UserTab.vue";
import Hamburger from "./Hamburger.vue";

export default {
  components: {
    Alert,
    UserTab,
    Hamburger
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
    }
  }
};
</script>
