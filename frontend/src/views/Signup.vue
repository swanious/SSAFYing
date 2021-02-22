<template>
  <div class="signup-page">
    <v-container class=" d-flex justify-content-center align-items-center">
      <b-card class="signup__card" bg-variant="">
        <b-form-group
          label="회원가입"
          label-cols-lg="3"
          label-size="lg"
          label-class="font-weight-bold"
          class="mb-0"
        >
          <ValidationObserver v-slot="{ handleSubmit }">
            <form @submit.prevent="handleSubmit(onSubmit)">
              <Name v-model="userInfo.username" />
              <Nickname v-model="userInfo.nickname" />
              <Email v-model="userInfo.email" />
              <Password v-model="userInfo.password" />
              <v-row>
                <v-col>
                  <Grade v-model="userInfo.grade" />
                </v-col>
                <v-col>
                  <Birthday v-model="userInfo.birthday" />
                </v-col>
                <v-col>
                  <Gender v-model="userInfo.gender" />
                </v-col>
              </v-row>
              <Nameplate v-model="nameplate" />
              <div class="d-flex justify-content-end">
                <button class="signup__btn" @click="createUser">
                  제출
                </button>
              </div>
            </form>
          </ValidationObserver>
        </b-form-group>
      </b-card>
    </v-container>
  </div>
</template>

<script>
import Name from "@/components/user/signup/Name";
import Nickname from "@/components/user/signup/Nickname";
import Birthday from "@/components/user/signup/Birthday";
import Password from "@/components/user/signup/Password";
import Email from "@/components/user/signup/Email";
import Grade from "@/components/user/signup/Grade";
import Gender from "@/components/user/signup/Gender";
import Nameplate from "@/components/user/signup/Nameplate";
import { axiosSignup } from "@/api/user.js";
import swal from "sweetalert";

export default {
  components: {
    Name,
    Nickname,
    Birthday,
    Password,
    Email,
    Grade,
    Gender,
    Nameplate
  },
  computed: {},
  watch: {
    nameplate(newNameplate) {
      console.log(newNameplate);

      const reader = new FileReader();
      reader.onload = e => {
        this.userInfo.profileImage = e.target.result;
      };
      reader.readAsDataURL(this.nameplate);
    }
  },
  data() {
    return {
      nameplate: null,
      userInfo: {
        nickname: "",
        email: "",
        password: "",
        username: "",
        birthday: "",
        grade: parseInt(""),
        gender: "",
        isActive: 1,
        profileImage: ""
      }
    };
  },
  methods: {
    createUser() {
      console.log("submit");
      console.log(this.userInfo);
      if (this.userInfo.email !== null) {
        axiosSignup(
          this.userInfo,
          response => {
            console.log(response);
            this.$router.push({ name: "Login" });
          },
          error => {
            // this.$router.push({ name: "Login" });
            swal({
              title: "가입 실패",
              text: "회원가입 정보를 제대로 기입해주세요 !",
              icon: "error"
            });
            console.log(error);
          }
        );
      } else {
        swal({
          title: "가입 실패",
          text: "이메일 인증을 진행해주세요 !",
          icon: "error"
        });
      }
    }
  }
};
</script>

<style scoped>
.signup-page {
  width: 100%;
  height: 100vh;
  background-color: var(--color1);
}
.signup__btn {
  background-color: var(--color1) !important;
  color: #ffffff !important;
  font-weight: bold;
  font-size: 18px;
  width: 15%;
  height: 3rem;
  border-radius: 10px;
  word-spacing: 10px;
}
</style>
