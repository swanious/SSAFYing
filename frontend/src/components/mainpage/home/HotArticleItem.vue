<template>
  <div class="board__contents">
    <div class="d-flex p-1" @click="goToDetailPage">
      <div>{{ rank + 1 }}.</div>
      <div class="ml-3">
        {{ article.title | truncate("25") }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    article: {
      type: Object
    },
    rank: {
      type: Number
    }
  },
  created() {
    // console.log(this.article);
  },
  methods: {
    goToDetailPage() {
      this.$router.push({
        name: "Detail",
        params: { articleId: this.article.articleId }
      });
    }
  }
};
</script>

<style></style>
