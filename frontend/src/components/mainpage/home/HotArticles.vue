<template>
  <div class="card">
    <div class="card-header">
      실시간 인기 글 🔥
    </div>

    <div class="ml-2 mt-1 mb-1 hotarticles__font">
      <HotArticleItem
        v-for="(article, idx) in recommends"
        :key="article.articleId"
        :article="article"
        :rank="idx"
      />
    </div>
  </div>
</template>

<script>
import HotArticleItem from "@/components/mainpage/home/HotArticleItem";
export default {
  components: {
    HotArticleItem
  },

  computed: {
    recommends() {
      return this.lodash.sortBy(
        this.$store.state.sideBarItmes.recommends,
        recommend => {
          return -recommend.score;
        }
      );
    }
  }
};
</script>

<style scoped>
.hotarticles__font {
  font-size: 14px;
}
</style>
