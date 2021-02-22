<template>
  <v-container class="header__contents py-0 fill-height">
    <!-- 메뉴 - 768px < width  -->
    <ul>
      <li v-for="(icon, i) in icons" :key="i">
        <router-link :to="links[i]" :title="titles[i]" role="link"
          ><font-awesome-icon id="header__icon" :icon="icon" size="lg" />
          <span id="header__text">
            {{ titles[i] }}
          </span>
        </router-link>
      </li>
    </ul>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      icons: ["chalkboard", "users", "bullhorn", "shopping-cart"],
      links: [
        "/board/community",
        "/group/all",
        "/board/information",
        "/board/market"
      ],
      titles: ["게시판", "모임", "정보", "싸피장터"]
    };
  }
};
</script>

<style></style>
