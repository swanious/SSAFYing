<template>
  <div class="user__hamburger">
    <button class="hamburger__menu mr-2" @click="hamburgerToggle">
      <font-awesome-icon id="header__icon" icon="bars" size="2x" />
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    hamburgerToggle: function() {
      const menu = document.querySelector(".pulldown");
      menu.classList.toggle("active");
    }
  }
};
</script>

<style></style>
