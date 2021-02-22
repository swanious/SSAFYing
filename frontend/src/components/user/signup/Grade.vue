<template>
  <div>
    <!-- grade -->
    <b-form-group id="fieldset-6" label="SSAFY 기수 *" label-for="input-6">
      <ValidationProvider
        mode="lazy"
        rules="required|between:1,5"
        v-slot="{ errors }"
        name="ssafy grade"
      >
        <b-form-input
          id="input-6"
          v-model="userInput"
          list="grade-list"
          :state="errors[0] ? false : userInput ? true : null"
          trim
        ></b-form-input>
        <span class="text-danger">{{ errors[0] }}</span>
      </ValidationProvider>
      <datalist id="grade-list">
        <option v-for="(grade, idx) in grades" :key="idx">{{ grade }}</option>
      </datalist>
    </b-form-group>
  </div>
</template>

<script>
export default {
  watch: {
    userInput(newValue) {
      // console.log(newValue);
      this.$emit("input", newValue);
    }
  },
  data() {
    return {
      userInput: "",
      grades: ["1", "2", "3", "4", "5"]
    };
  }
};
</script>

<style></style>
