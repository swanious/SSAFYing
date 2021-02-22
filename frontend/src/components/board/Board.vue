<template>
  <v-app>
    <Header />
    <v-main>
      <v-container id="community__container">
        <div class="community__main">
          <router-view></router-view>
        </div>
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";

import "@/assets/css/style.css";
import "@/assets/css/community.css";

// import Comment from './comment/Comment.vue';
export default {
  name: "board",
  components: {
    Header,
    Footer
  }
};
</script>
