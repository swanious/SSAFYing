<template>
  <div>
    <b-input
      placeholder="댓글을 달아주세요."
      v-model="content"
      @keypress.enter="createComment"
    ></b-input>
  </div>
</template>

<script>
import { axiosCreateComment } from "@/api/comment.js";

export default {
  name: "CommentWrite",
  props: {
    articleId: Number
    // articleId: {
    //   type: Number,
    //   default: Object
    // }
  },
  data() {
    return {
      content: ""
    };
  },
  methods: {
    createComment() {
      const commentItem = {
        articleId: this.articleId,
        userId: this.$store.state.userInfo.userId,
        content: this.content
      };

      this.content = "";

      axiosCreateComment(
        commentItem,
        res => {
          this.$emit("commentCreate", res.data);
          // console.log(res.data);
        },
        err => {
          console.log(err);
        }
      );
      // this.send();
      // this.$store.dispatch("createComment", commentItem);
    }
  }
};
</script>

<style></style>
