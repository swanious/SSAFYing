<template>
  <div>
    <ValidationObserver>
      <!-- password -->
      <b-form-group id="fieldset-3" label="비밀번호 *" label-for="input-3">
        <ValidationProvider
          rules="required|max:15|min:8|password:@confirm"
          v-slot="{ errors }"
          name="password"
        >
          <b-form-input
            id="input-3"
            type="password"
            v-model="userInput"
            :state="errors[0] ? false : userInput ? true : null"
            trim
          ></b-form-input>
          <span class="text-danger">{{ errors[0] }}</span>
        </ValidationProvider>
      </b-form-group>

      <!-- password confirm-->
      <b-form-group id="fieldset-4" label="비밀번호 확인 *" label-for="input-4">
        <ValidationProvider name="confirm" rules="required" v-slot="{ errors }">
          <b-form-input
            id="input-4"
            type="password"
            v-model="confirmation"
            :state="errors[0] ? false : userInput ? true : null"
            trim
          ></b-form-input>
          <span class="text-danger">{{ errors[0] }}</span>
        </ValidationProvider>
      </b-form-group>
    </ValidationObserver>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInput: "",
      confirmation: ""
    };
  },
  watch: {
    userInput(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>

<style scoped></style>
