<template>
  <div>
    <div class="mb-1 card-header">
      게시판
    </div>

    <!--게시판 목록-->
    <div class="card-body">
      <v-row no-gutters="no-gutters">
        <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
          <HomeCommunityFree :articles="commuItems.free" />
        </v-col>
        <v-col style="border-right:2px solid rgba(0,0,0,0.1)">
          <HomeCommunityAnonymous :articles="commuItems.anonymous" />
        </v-col>
        <v-col>
          <HomeCommunityGrade :articles="commuItems.grade" />
        </v-col>
      </v-row>
    </div>
  </div>
  <!--게시판 카테고리-->
</template>

<script>
import HomeCommunityFree from "@/components/mainpage/home/community/HomeCommunityFree";
import HomeCommunityAnonymous from "@/components/mainpage/home/community/HomeCommunityAnonymous";
import HomeCommunityGrade from "@/components/mainpage/home/community/HomeCommunityGrade";

// import { axiosReadMain } from "@/api/article.js";
export default {
  props: {
    commuItems: {
      type: Object
    }
  },
  components: {
    HomeCommunityFree,
    HomeCommunityAnonymous,
    HomeCommunityGrade
  }
};
</script>

<style></style>
