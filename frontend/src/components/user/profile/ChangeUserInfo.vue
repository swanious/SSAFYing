<template>
  <v-app id="inspire">
    <Header />
    <div class="m-5">
      <v-row>
        <v-col cols="2"></v-col>
        <v-col>
          <b-card class="signup__card" bg-variant="light">
            <b-form-group
              label="Change Password"
              label-cols-lg="3"
              label-size="lg"
              label-class="font-weight-bold"
              class="mb-0"
            >
              <!-- <ValidationObserver v-slot="{ handleSubmit }"> -->
              <!-- <form @submit.prevent="handleSubmit(onSubmit)"> -->
              <ChangePasswordInput v-model="userInfo.newPassword" />

              <!-- </form> -->
              <!-- </ValidationObserver> -->
            </b-form-group>
          </b-card>
        </v-col>
        <v-col cols="2"></v-col>
      </v-row>
    </div>
  </v-app>
</template>

<script>
import Header from "@/components/common/Header.vue";
import ChangePasswordInput from "@/components/user/profile/ChangePasswordInput";
// import { axiosSignup } from "@/api/user.js";

export default {
  name: "ChangeUserInfo",
  components: {
    Header,
    ChangePasswordInput
  },

  data() {
    return {
      userInfo: {
        oldPassword: "",
        newPassword: ""
      }
    };
  },
  methods: {
    onSubmit() {
      console.log("submit");
      console.log(this.userInfo);
    }
  }
};
</script>

<style>
.signup__card {
  padding-top: 64px;
}
</style>
