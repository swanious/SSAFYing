<template>
  <b-form-group id="fieldset-1" label="이름 *" label-for="input-0">
    <ValidationProvider
      :rules="{ required: true }"
      v-slot="{ errors }"
      mode="aggressive"
      name="name"
    >
      <b-form-input
        id="input-0"
        v-model="userInput"
        :state="errors[0] ? false : userInput ? true : null"
      >
      </b-form-input>
      <span class="text-danger">{{ errors[0] }}</span>
    </ValidationProvider>
  </b-form-group>
</template>

<script>
export default {
  name: "Name",

  data() {
    return {
      userInput: "",
      isVerified: false
    };
  },
  watch: {
    userInput(newVal) {
      this.$emit("input", newVal);
    }
  }
};
</script>

<style></style>
