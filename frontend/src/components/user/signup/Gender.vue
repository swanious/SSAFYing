<template>
  <div>
    <!-- sex -->
    <b-form-group id="fieldset-8" label="성별" label-for="input-8">
      <ValidationProvider mode="lazy" rules="required" v-slot="{ errors }">
        <b-form-input
          id="input-8"
          v-model="userInput"
          list="sex-list"
          :state="errors[0] ? false : userInput ? true : null"
          trim
        ></b-form-input>
        <span class="text-danger">{{ errors[0] }}</span>
      </ValidationProvider>
      <datalist id="sex-list">
        <option v-for="(sex, idx) in sexes" :key="idx">{{ sex }}</option>
      </datalist>
    </b-form-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInput: "",
      sexes: ["male", "female", "they"]
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
