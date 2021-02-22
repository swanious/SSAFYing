<template>
  <!--1. 자유게시판 -->
  <div class="">
    <div class="home__contents__card">
      <h6>
        알고리즘 모임
      </h6>
      <v-divider></v-divider>
      <GroupArticle
        v-for="(article, idx) in hackathonArticles"
        :key="idx"
        :article="article"
      />
      <!-- <CommunityArticle />
          <CommunityArticle />
          <CommunityArticle /> -->
    </div>
  </div>
</template>

<script>
import { axiosReadSub } from "@/api/article.js";
import GroupArticle from "@/components/mainpage/articles/GroupArticle.vue";

export default {
  data() {
    return {
      hackathonArticles: []
    };
  },
  components: {
    GroupArticle
  },
  created() {
    axiosReadSub(
      "Anonymous",
      res => {
        // console.log(res);
        this.hackathonArticles = res.data.slice(0, 4);
      },
      err => {
        console.log(err);
      }
    );
  }
};
</script>

<style></style>
