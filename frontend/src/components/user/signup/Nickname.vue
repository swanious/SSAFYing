<template>
  <!-- nickname -->
  <b-form-group
    id="fieldset-1"
    label="닉네임 *"
    label-for="input-1"
    :state="isVerified"
    :invalid-feedback="nicknameInvalidFeedback"
  >
    <b-row>
      <b-col xs="10">
        <ValidationProvider
          :rules="{ required: true }"
          v-slot="{ errors }"
          mode="eager"
          name="nickname"
        >
          <div class="d-flex">
            <b-form-input
              id="input-1"
              v-model="userInput"
              trim
              :state="errors[0] ? false : isVerified"
              required
            ></b-form-input>
            <span class="text-danger">{{ errors[0] }}</span>

            <b-button
              :disabled="isVerified"
              class="ml-2"
              style="font-size:15px;width:150px;"
              @click="duplicateCheck"
              >중복 확인</b-button
            >
          </div>
        </ValidationProvider>
      </b-col>
      <!-- <b-col xs="2">
        <b-button :disabled="isVerified" @click="duplicateCheck"
          >중복체크</b-button
        >
      </b-col> -->
    </b-row>
  </b-form-group>
</template>

<script>
// import { extend } from "vee-validate";
import { nicknameDuplicateCheck } from "@/api/user.js";
import swal from "sweetalert";

// extend("min4", value => {
//   return value.length > 3;
// });

export default {
  name: "Nickname",

  data() {
    return {
      userInput: "",
      isVerified: null
    };
  },
  computed: {
    nicknameInvalidFeedback() {
      return "중복된 닉네임입니다. 다른 닉네임을 설정해주세요";
    }
  },
  watch: {
    userInput() {
      this.$emit("input", this.userInput);
    }
  },
  methods: {
    duplicateCheck() {
      nicknameDuplicateCheck(
        this.userInput,
        () => {
          swal({
            title: "중복 확인",
            text: "중복 확인 성공 !",
            icon: "success"
          });
          this.isVerified = true;
        },
        () => {
          this.isVerified = false;
          swal({
            title: "중복 확인",
            text: "중복 확인 실패",
            icon: "error"
          });
        }
      );
    }
  }
};
</script>

<style scoped>
.unduplicated {
}
</style>
