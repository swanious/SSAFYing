<template>
  <!-- email -->
  <div>
    <b-form-group
      id="fieldset-2"
      label="이메일 *"
      label-for="input-2"
      :state="emailVerify1 ? true : userInput ? false : null"
      valid-feedback="사용가능한 이메일입니다."
    >
      <div class="d-flex">
        <b-form-input
          id="input-2"
          type="email"
          v-model="userInput"
          :state="emailVerify1 ? true : null"
          trim
          required
        ></b-form-input>
        <b-button
          class="ml-2"
          style="font-size:15px;width:150px;"
          @click="emailVerifyCheck"
          :disabled="emailVerify2"
          >이메일 인증</b-button
        >
      </div>
    </b-form-group>
    <b-form-group v-show="emailVerify1" label="인증코드 *" label-for="code">
      <b-form-input
        id="code"
        placeholder="인증코드를 입력해주세요"
        v-model="userInputCode"
        :state="emailVerify2"
        required
        trim
      ></b-form-input>
    </b-form-group>
  </div>
</template>

<script>
import { axiosPostEmailVerify } from "@/api/user.js";
import swal from 'sweetalert'
export default {
  watch: {
    // userInput(newValue) {
    //   // this.$emit("input", newValue);
    // },
    userInputCode(newValue) {
      if (newValue === this.correctCode) {
        this.emailVerify2 = true;
        this.$emit("input", this.userInput);
      } else {
        this.emailVerify2 = false;
      }
    }
  },
  data() {
    return {
      userInput: "",
      emailVerify1: false,
      emailVerify2: false,
      correctCode: "",
      userInputCode: ""
    };
  },
  methods: {
    emailVerifyCheck() {
      const emailItem = {
        email: this.userInput
      };
      axiosPostEmailVerify(
        emailItem,
        res => {
          console.log(res);
          if (res.data.message === "success") {
            this.emailVerify1 = true;
            this.correctCode = res.data.uuid.slice(0, 5);
            console.log(this.correctCode);
            swal({
              title: "메일 발송 성공",
              text: "인증 메일을 보냈습니다. 메일함을 확인하세요 !",
              icon: "success"
            });
          } else {
            swal({
              title: "이메일 중복",
              text: "이미 존재하는 이메일입니다.",
              icon: "info"
            });
          }
        },
        err => {
            swal({
              title: "ERROR",
              text: "양식에 맞게 올바른 이메일을 입력해주세요.",
              icon: "error"
            });
          console.log(err);
        }
      );
    }
  }
};
</script>

<style></style>
