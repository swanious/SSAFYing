<template>
  <div class="card my-3">
    <div
      v-if="isShow"
      class="d-flex justify-content-between align-items-center m-2"
    >
      <div class="d-flex">
        <!-- <router-link
          :to="{
            name: 'Profile',
            params: { nickname: comment.nickname }
          }"
          style="color: #636DA6"
          >{{ comment.nickname }}</router-link
        > -->
        <div class="ml-1" style="color: #636DA6">
          <b>익명</b>
        </div>
        <div class="ml-5">{{ comment.content }}</div>
      </div>
      <div v-if="isYourself">
        <button class="btn update__btn" @click="commentUpdateView">수정</button>
        <button class="btn btn-danger ml-1" @click="commentDelete">삭제</button>
      </div>
    </div>
    <div v-else class="d-flex m-3">
      <b-input type="text" v-model="content" />
      <button class="btn btn-primary ml-3" @click="commentUpdate">수정</button>
    </div>
    <!-- <b-button @click="commentUpdateView">update</b-button> -->
    <!-- <b-button @click="commentDelete">delete</b-button> -->
  </div>
</template>

<script>
import { axiosUpdateComment, axiosDeleteComment } from "@/api/comment.js";

export default {
  name: "CommentAnonymous",
  data() {
    return {
      isShow: true,
      // commentContent: this.comment.content,
      content: this.comment.content
    };
  },
  computed: {
    isYourself() {
      return this.comment.userId === this.$store.state.userInfo.userId;
    }
  },
  props: {
    articleId: Number,
    comment: Object,
    comments: Array
  },
  methods: {
    // 댓글 삭제
    commentDelete() {
      const commentItem = this.comment;
      const idx = this.comments.indexOf(commentItem);
      this.comments.splice(idx, 1);
      axiosDeleteComment(
        commentItem,
        () =>
          console.log(
            `${idx + 1} 번째 댓글 "${commentItem.content}"이 삭제됐습니다.`
          ),
        err => console.log(err)
      );
    },

    // 댓글 수정
    commentUpdateView() {
      this.isShow = false;
    },
    commentUpdate() {
      const commentItem = this.comment;
      commentItem.content = this.content;

      console.log(commentItem);

      axiosUpdateComment(
        commentItem,
        () => {},
        err => console.log(err)
      );
      // this.comments = this.comments.map(comment => {
      // if (comment.articleid === commentItem.articleid) {
      //   return commentItem;
      // }
      // return comment;
      // });

      this.isShow = true;
    }
  }
};
</script>

<style scoped>
.update__btn {
  background-color: var(--color1) !important;
  color: #ffffff !important;
}
</style>
