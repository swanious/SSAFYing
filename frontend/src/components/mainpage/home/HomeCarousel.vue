<template>
  <v-carousel height="300" show-arrows-on-hover cycle hide-delimiters>
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
    ></v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          src: require("@/assets/images/homecarousel/싸핑_캐러셀1.png")
        },
        {
          src: "@/assets/images/homecarousel/싸핑1.png"
        },
        {
          src: "@/assets/images/homecarousel/싸핑1.png"
        },
        {
          src: "@/assets/images/homecarousel/싸핑1.png"
        }
      ]
    };
  }
};
</script>

<style scoped>
.homeimg__border {
  border: none;
}
/* .carousel-inner > .item > img {
  display: block;

  max-width: 100%;

  height: 240;
} */
.home__carousel {
  height: 225px;
  max-height: 245px;
}
.img-fluid {
  height: 240px;
}
</style>
