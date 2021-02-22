<template>
  <div>
    <!-- birthday -->
    <b-form-group id="fieldset-7" label="생년월일" label-for="input-7">
      <ValidationProvider
        mode="lazy"
        name="confirm"
        rules="required"
        v-slot="{ errors }"
      >
        <b-form-input
          id="input-7"
          type="date"
          v-model="userInput"
          :state="errors[0] ? false : userInput ? true : null"
          trim
        ></b-form-input>
        <span class="text-danger">{{ errors[0] }}</span>
      </ValidationProvider>
    </b-form-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInput: ""
    };
  },
  watch: {
    userInput(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>

<style></style>
