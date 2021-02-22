<template>
  <div class="login-area">
    <div class="container">
      <b-alert show variant="danger" v-if="isLoginError"
        >아이디 또는 비밀번호를 확인하세요.</b-alert
      >
      <!-- 로고 -->
      <div class="in-head">
        <h1><b>SSAFYING</b></h1>
      </div>
      <!-- 로그인 필드셋 -->
      <div class="field-set">
        <div class="login__email my-3">
          <ValidationProvider
            :rules="{ required: true, email: true }"
            v-slot="{ errors }"
            mode="eager"
            name="email"
          >
            <label for="input-1"><b>이메일</b></label>
            <b-form-input
              id="input-1"
              type="email"
              v-model="loginInfo.email"
              :state="errors[0] ? null : loginInfo.email ? true : null"
              trim
              class="login__border"
            ></b-form-input>
            <span>{{ errors[0] }}</span>
          </ValidationProvider>
        </div>

        <div class="login__password my-3">
          <ValidationProvider
            name="password"
            rules="required"
            v-slot="{ errors }"
          >
            <label for="input-2"><b>비밀번호</b></label>
            <b-form-input
              id="input-2"
              type="password"
              v-model="loginInfo.password"
              @keypress.enter="onLogin"
              trim
              class="login__border"
            ></b-form-input>
            <span>{{ errors[0] }}</span>
          </ValidationProvider>
        </div>

        <div class="find-set">
          <ul class="partition-list">
            <li>
              <router-link to="/signup"><b>회원가입</b></router-link>
            </li>
          </ul>
        </div>
      </div>
      <!-- 로그인 버튼 -->
      <div class="btn__wrap">
        <button class="login__btn" @click="onLogin">로그인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { axiosLogin } from "@/api/user.js";
import swal from "sweetalert";

export default {
  data() {
    return {
      loginInfo: {
        email: "",
        password: ""
      },
      isLoginError: false
    };
  },
  methods: {
    async onLogin() {
      // console.log(this.loginInfo);
      localStorage.setItem("access-token", "");
      await axiosLogin(
        this.loginInfo,
        response => {
          if (response.data.message === "success") {
            try {
              let token = response.data["access-token"];
              // this.$store.dispatch("getMemberInfo", token);
              this.$store.dispatch("getMemberInfo", token);
              this.$store.commit("SET_IS_LOGINED", true);
              localStorage.setItem("access-token", token);
              setTimeout(() => {
                this.$router.push("/home");
              }, 500);
            } catch (error) {
              console.log(error);
            }
          } else {
            this.isLoginError = true;
          }
        },
        error => {
          console.log(error);
          this.$router.push({ name: "Login" });
          swal({
            title: "로그인 실패",
            text: "비밀 번호를 재확인 해주세요 !",
            icon: "error"
          });
        }
      );
    }
  }
};
</script>

<style scoped>
.login__border {
  border-radius: 0;
}
</style>
