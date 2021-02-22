<template>
  <!--1. 자유게시판 -->
  <div class="">
    <div class="home__contents__card ml-2 mr-2">
      <div class="ml-1">
        <h6>
          생활
        </h6>
      </div>
      <v-divider></v-divider>
      <CommunityArticle
        v-for="(article, idx) in articles"
        :key="idx"
        :article="article"
      />
    </div>
  </div>
</template>

<script>
import CommunityArticle from "@/components/mainpage/articles/CommunityArticle.vue";

export default {
  props: {
    articles: {
      type: Object
    }
  },

  components: {
    CommunityArticle
  }
};
</script>

<style></style>
