<template>
  <div>
    <!-- email -->
    <b-form-group id="fieldset-9" label="학생증 인증" label-for="input-9">
      <ValidationProvider rules="image" v-slot="{ errors }" mode="eager">
        <b-form-file id="input-9" v-model="userInput" trim></b-form-file>
        <span class="text-danger">{{ errors[0] }}</span>
      </ValidationProvider>
    </b-form-group>
  </div>
</template>

<script>
export default {
  watch: {
    userInput(newValue) {
      this.$emit("input", newValue);
    }
  },
  data() {
    return {
      userInput: null
    };
  }
};
</script>

<style></style>
