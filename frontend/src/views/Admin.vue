<template>
  <v-app>
    <Header />
    <div>
      <router-view></router-view>
    </div>
  </v-app>
</template>

<script>
import Header from "@/components/admin/Header.vue";
import "@/assets/css/admin.css";

export default {
  components: {
    Header
  }
};
</script>
