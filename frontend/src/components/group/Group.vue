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
// import Sidebar from "@/components/common/Sidebar.vue";

import "@/assets/css/style.css";
import "@/assets/css/group.css";

export default {
  name: "board",
  components: {
    Header,
    Footer
    // CreateRoom
    // Sidebar
  }
};
</script>
