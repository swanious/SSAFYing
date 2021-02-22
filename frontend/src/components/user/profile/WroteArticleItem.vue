<template>
  <div class="card p-3" @click="goToDetailPage()">
    <div class="d-flex justify-content-between">
      <div
        :class="{
          title: article,
          trending: article.watchCount > 10 ? true : false
        }"
      >
        <font-awesome-icon
          v-if="article.watchCount > 10"
          class="mx-1"
          :icon="['fas', 'fire-alt']"
        />
        {{ article.title | truncate(30) }}
      </div>
    </div>

    <div class="content">
      <span style="text-overflow:ellipsis">{{
        article.content | truncate(100)
      }}</span>
    </div>

    <div>
      <ul class="status d-flex justify-content-end">
        <li class="mx-1">
          <span>{{ article.createdAt | moment("from", "now") }}</span>
        </li>
        <!-- <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'thumbs-up']" />{{
            article.articleLike
          }}
        </li>
        <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'comment-dots']" />{{
            article.articleCommentCount
          }} -->
        <!-- </li> -->
        <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'eye']" />
          {{ article.watchCount }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    article: {
      type: Object
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

<style scoped>
.trending {
  color: red;
}
</style>
