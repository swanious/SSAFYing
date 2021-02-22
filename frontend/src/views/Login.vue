<template>
  <div>
    <div class="login-page">
      <div class="container d-flex align-items-center">
        <LoginArea />
      </div>
    </div>
  </div>
</template>

<script>
import LoginArea from "../components/user/login/LoginArea.vue";

export default {
  data: () => {
    return {};
  },
  components: {
    LoginArea
  }
};
</script>
<style scoped>
@import "../assets/css/login.css";
@import "../assets/css/style.css";
</style>
