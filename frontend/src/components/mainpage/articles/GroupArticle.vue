<template>
  <a href="#">
    <div class="article__list__title">
      <p @click="goToArticleDetailPage">{{ this.article.title }}</p>
    </div>
  </a>
</template>

<script>
export default {
  props: {
    article: {
      type: Object
    }
  },
  methods: {
    goToArticleDetailPage() {
      // console.log(this.article.articleId);
      this.$router.push({
        name: "Detail",
        params: { articleId: this.article.articleId }
      });
    }
  }
};
</script>

<style></style>
