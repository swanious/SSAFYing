<template>
  <a>
    <div class="article__list__title mb-1">
      <div
        @click="goToArticleDetailPage"
        class="d-flex justify-content-between px-1"
        style="color:black;"
      >
        <div>
          {{ article.title | truncate("15") }}
        </div>
        <div>
          <font-awesome-icon class="mx-1" :icon="['far', 'eye']" />{{
            article.watchCount
          }}
        </div>
      </div>
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
