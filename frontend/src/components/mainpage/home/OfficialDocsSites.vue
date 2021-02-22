<template>
  <div class="card-body d-flex justify-content-around">
    <!-- django -->
    <div class="rounded-circle official-site-photo">
      <a href="https://docs.djangoproject.com/en/3.1/">
        <img
          src="https://blog.kakaocdn.net/dn/cVaSOX/btqD9jVw36X/jHpIEqn2EAk7xdKMMmpEP0/img.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- vue -->
    <div class="rounded-circle official-site-photo">
      <a href="https://vuejs.org/v2/guide/">
        <img
          src="https://upload.wikimedia.org/wikipedia/commons/f/f1/Vue.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- bootstrap -->
    <div class="rounded-circle official-site-photo">
      <a href="https://getbootstrap.com/docs/4.5/getting-started/introduction/">
        <img
          src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Bootstrap_logo.svg/1024px-Bootstrap_logo.svg.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- spring -->
    <div class="rounded-circle official-site-photo">
      <a
        href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"
      >
        <img
          src="https://dzone.com/storage/temp/12434118-spring-boot-logo.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- python -->
    <div class="rounded-circle official-site-photo">
      <a href="https://docs.python.org/3/">
        <img
          src="https://cdn4.iconfinder.com/data/icons/logos-and-brands/512/267_Python_logo-512.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.official-site-photo {
  width: 56px;
  height: 56px;
  position: relative;
  border-width: 2px !important;
}
</style>
