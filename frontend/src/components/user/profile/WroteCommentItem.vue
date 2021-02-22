<template>
  <div class="card p-3" @click="goToDetailPage()">
    <div class="d-flex justify-content-between">
      <div class="content">
        <h5>
          <font-awesome-icon class="mx-1" :icon="['far', 'comment-dots']" />
          {{ comment.content }}
        </h5>
      </div>
      <div>
        <span>{{ comment.createdAt | moment("from", "now") }}</span>
      </div>
    </div>
    <footer class="blockquote-footer" v-if="article">
      <cite title="Source Title"> {{ articleWriter }}</cite> /
      <!-- {{ article.subCategory }} -->
      {{ article.title }}
    </footer>
    <div>
      <ul class="status d-flex justify-content-end">
        <li class="mx-1"></li>
      </ul>
    </div>
  </div>
</template>

<script>
import { axiosReadOneArticle } from "@/api/article.js";
export default {
  props: {
    comment: {
      type: Object
    }
  },
  data() {
    return {
      article: {},
      articleWriter: ""
    };
  },
  watch: {
    comment() {
      //   console.log(this.newValue);
      axiosReadOneArticle(
        this.comment.articleId,
        res => {
          //   console.log(res.data);
          this.articleWriter = res.data.nickname;
          // console.log(this.nickname);
          // this.isLike = res.data.isLike;
          // this.articleLikeCount = res.data.articleLikeCount;
          // this.commentCount = res.data.commentCount;
          this.article = res.data.data;
          //   console.log(this.article.title);
        },
        err => {
          console.log(err);
        }
      );
    }
  },
  created() {
    // console.log(this.comment);
    axiosReadOneArticle(
      this.comment.articleId,
      res => {
        // console.log(res.data);
        this.articleWriter = res.data.nickname;
        // console.log(this.nickname);
        // this.isLike = res.data.isLike;
        // this.articleLikeCount = res.data.articleLikeCount;
        // this.commentCount = res.data.commentCount;
        this.article = res.data.data;
        // console.log(this.article.title);
      },
      err => {
        console.log(err);
      }
    );
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
