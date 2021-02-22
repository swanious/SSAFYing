<template>
  <div>
    <b-form-group
      id="fieldset-1"
      label="old password *"
      label-for="input-1"
      :state="oldPassword ? oldPasswordState : null"
      :invalid-feedback="oldPasswordInvalidFeedback"
    >
      <b-form-input
        id="input-1"
        type="password"
        v-model="oldPassword"
        :state="oldPassword ? oldPasswordState : null"
        required
      ></b-form-input>
    </b-form-group>

    <!-- password -->
    <b-form-group
      id="fieldset-2"
      label="new password *"
      label-for="input-2"
      :state="newPassword ? newPasswordState : null"
      :invalid-feedback="newPasswordInvalidFeedback"
    >
      <b-form-input
        id="input-2"
        type="password"
        v-model="newPassword"
        :state="newPassword ? newPasswordState : null"
        required
        trim
      ></b-form-input>
    </b-form-group>

    <!-- password confirm-->
    <b-form-group
      id="fieldset-3"
      label="new password confirmation *"
      label-for="input-3"
      :state="confirmation ? confirmationState : null"
      :invalid-feedback="confirmationInvalidFeedback"
      confirmationInvalidFeedback
    >
      <b-form-input
        id="input-3"
        type="password"
        v-model="confirmation"
        :state="confirmation ? confirmationState : null"
        required
        trim
      ></b-form-input>
    </b-form-group>
    <b-button variant="primary" @click="changePassword">submit</b-button>
  </div>
</template>

<script>
import { axiosPutPassword } from "@/api/user.js";
import swal from "sweetalert";

export default {
  data() {
    return {
      oldPassword: "",
      newPassword: "",
      confirmation: ""
    };
  },
  computed: {
    oldPasswordState() {
      return this.oldPassword.length > 7;
    },
    newPasswordState() {
      if (this.newPassword === this.oldPassword) {
        return false;
      }
      return this.newPassword.length > 7;
    },
    confirmationState() {
      if (this.newPassword !== this.confirmation) {
        return false;
      }
      return this.newPassword.length > 7;
    },
    oldPasswordInvalidFeedback() {
      if (this.oldPassword.length > 0) {
        return "Enter at least 8 characters.";
      }
      return "Please enter something.";
    },
    newPasswordInvalidFeedback() {
      if (this.oldPassword === this.newPassword) {
        return "기존에 패스워드와 다른 패스워드를 입력해주세요";
      } else if (this.newPassword.length > 0) {
        return "Enter at least 8 characters.";
      }
      return "Please enter something.";
    },
    confirmationInvalidFeedback() {
      if (this.newPassword !== this.confirmation) {
        return "비밀번호 일치여부를 한 번더 확인해주세요";
      } else if (this.newPassword.length > 0) {
        return "Enter at least 8 characters.";
      }
      return "Please enter something.";
    }
  },
  watch: {
    // newPassword(newVal) {
    //   if (this.oldPassword === newVal) {
    //     this.newPassword = "";
    //   }
    // }
    // userInput(newValue) {
    //   this.$emit("input", newValue);
    // }
  },
  methods: {
    changePassword() {
      if (
        this.oldPasswordState &&
        this.newPasswordState &&
        this.confirmationState
      ) {
        const passwordInfo = {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword
        };
        axiosPutPassword(
          passwordInfo,
          res => {
            console.log(res);
            //   console.log(this.$route.params.nickname);

            this.$router.push({
              name: "Profile",
              params: { nickname: this.$route.params.nickname }
            });
          },
          err => {
            console.log(err);
            swal({
              title: "변경 실패",
              text: "기존 비밀번호가 정확하지 않습니다 !",
              icon: "error"
            });
          }
        );
      } else {
        swal({
          title: "변경 실패",
          text: "비밀 번호를 재확인 해주세요 !",
          icon: "error"
        });
      }
    }
  }
};
</script>

<style scoped></style>
