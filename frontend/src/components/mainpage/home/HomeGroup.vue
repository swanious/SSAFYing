<template>
  <div class="m-3">
    <!--카테고리-->
    <div class="mb-5">
      <h5>
        <b>모임</b>
      </h5>
    </div>
    <!--모임 목록-->
    <v-row no-gutters="no-gutters">
      <!--1. 스터디모임 -->
      <!-- <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
        <HomeGroupStudy />
      </v-col>
      <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
        <HomeGroupAlgorithm />
      </v-col>
      <v-col>
        <HomeGroupHackathon />
      </v-col> -->
    </v-row>
  </div>
</template>

<script>
// import HomeGroupStudy from "@/components/mainpage/home/group/HomeGroupStudy";
// import HomeGroupAlgorithm from "@/components/mainpage/home/group/HomeGroupAlgorithm";
// import HomeGroupHackathon from "@/components/mainpage/home/group/HomeGroupHackathon";

// import { axiosReadMain } from "@/api/article.js";
export default {
  components: {
    // HomeGroupStudy,
    // HomeGroupAlgorithm,
    // HomeGroupHackathon
  },
  created() {
    // axiosReadMain(
    //   "Community",
    //   res => {
    //     console.log(res);
    //   },
    //   err => {
    //     console.log(err);
    //   }
    // );
  }
};
</script>

<style></style>
