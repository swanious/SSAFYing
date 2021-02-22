<template>
  <div>
    <!--게시판 카테고리-->
    <div class="mb-1 card-header">
      정보
    </div>

    <!--게시판 목록-->
    <div class="card-body">
      <v-row no-gutters="no-gutters">
        <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
          <HomeInformationIT :articles="infoItems.it" />
        </v-col>
        <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
          <HomeInformationJob :articles="infoItems.job" />
        </v-col>
        <v-col>
          <HomeInformationLife :articles="infoItems.life" />
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import HomeInformationIT from "@/components/mainpage/home/information/HomeInformationIT";
import HomeInformationJob from "@/components/mainpage/home/information/HomeInformationJob";
import HomeInformationLife from "@/components/mainpage/home/information/HomeInformationLife";
// import HomeCommunityAnonymous from "@/components/mainpage/home/community/HomeCommunityAnonymous";
// import HomeCommunityGrade from "@/components/mainpage/home/community/HomeCommunityGrade";

// import { axiosReadMain } from "@/api/article.js";
export default {
  props: {
    infoItems: {
      type: Object
    }
  },
  created() {
    // console.log(this.infoItems);
  },
  components: {
    HomeInformationIT,
    HomeInformationJob,
    HomeInformationLife
  }
};
</script>

<style></style>
