<template>
  <v-container>
    <!-- 뒤로가기 -->
    <div class="mb-3">
      <router-link :to="{ path: `/board/${article.subCategory}` }">
        <span style="font-size:25px"
          ><font-awesome-icon class="mr-3" icon="chevron-left" />뒤로가기</span
        >
      </router-link>
    </div>
    <div class="card">
      <div class="m-3" v-bind="article">
        <div id="editor">
          <div id="title">
            <h3>{{ article.title }}</h3>
            <a>
              <h5>익명</h5>
            </a>
            <!-- <router-link
              :to="{
                name: 'Profile',
                params: { nickname: nickname }
              }"
              style="color: #636DA6"
              ><h4>{{ nickname }}</h4></router-link
            > -->
          </div>

          <ul class="status d-flex justify-content-end">
            <li class="mx-1">
              <font-awesome-icon
                @click="isLike ? cancleLikeBtn() : likeArticleBtn()"
                :class="{ 'mx-1': article, isLike }"
                :icon="['far', 'thumbs-up']"
              />{{ articleLikeCount }}
            </li>
            <li class="mx-1">
              <font-awesome-icon
                class="mx-1"
                :icon="['far', 'comment-dots']"
              />{{ commentCount }}
            </li>
          </ul>
          <v-divider></v-divider>

          <!-- <div id="content"> -->
          <div style="white-space:pre;">
            <img
              v-if="article.articleImage"
              style="width:300px"
              :src="article.articleImage"
              alt=""
            />
            <!-- <p>{{ article.content }}</p> -->
            <p style="white-space:normal;">
              {{ article.content }}
            </p>
          </div>
        </div>

        <div
          class="button-group d-flex justify-content-end mb-3"
          v-if="isYourself"
        >
          <b-button class="btn delete__btn" @click="deleteArticle"
            >삭제</b-button
          >
        </div>

        <v-divider></v-divider>
        <b><h5>댓글</h5></b>
        <div>
          <CommentWriteAnonymous
            :articleId="article.articleId"
            @commentCreate="setComments"
          />
        </div>
        <div>
          <CommentAnonymous
            v-for="(comment, idx) in comments"
            :key="idx"
            :articleId="article.articleId"
            :comment="comment"
            :comments="comments"
          />
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import {
  axiosReadOneArticle,
  axiosUpdateArticle,
  axiosDeleteArticle
} from "@/api/article.js";
import { axiosPostArticleLike } from "@/api/article.js";
import { axiosDeleteArticleLike } from "@/api/article.js";
import { axiosReadComment } from "@/api/comment.js";
import { axiosGetUserInfo } from "@/api/user.js";
import CommentWriteAnonymous from "@/components/board/comment/CommentWriteAnonymous";
import CommentAnonymous from "@/components/board/comment/CommentAnonymous";
import swal from "sweetalert";

export default {
  name: "DetailAnonymous",
  data() {
    return {
      article: {},
      comments: [],
      articleLikeCount: 0,
      commentCount: 0,
      isLike: 0,
      nickname: ""
    };
  },
  created() {
    axiosReadOneArticle(
      this.$route.params.articleId,
      res => {
        // this.nickname = res.data.nickname;
        this.isLike = res.data.isLike;
        this.articleLikeCount = res.data.articleLikeCount;
        this.commentCount = res.data.commentCount;
        this.article = res.data.data;
      },
      err => {
        console.log(err);
      }
    );
    axiosGetUserInfo(
      this.nickname,
      () => {},
      err => console.log(err)
    );
    axiosReadComment(
      this.$route.params.articleId,
      res => {
        this.comments = res.data;
      },
      err => {
        console.log(err);
      }
    );
  },
  beforeUpdate() {
    this.article.watchCount += 1;
    axiosUpdateArticle(
      this.article,
      res => {
        res.data;
      },
      err => {
        console.log(err);
      }
    );
  },
  computed: {
    isYourself() {
      return this.article.userId === this.$store.state.userInfo.userId;
    }
  },
  components: {
    CommentWriteAnonymous,
    CommentAnonymous
  },
  methods: {
    likeArticleBtn() {
      const articleInfo = {
        articleId: this.article.articleId,
        userId: this.$store.state.userInfo.userId
      };
      axiosPostArticleLike(
        articleInfo,
        res => {
          if (res.status === 200) {
            this.isLike = 1;
            this.articleLikeCount += 1;
          }
        },
        err => {
          console.log(err);
        }
      );
    },
    cancleLikeBtn() {
      const articleInfo = {
        articleId: this.article.articleId,
        userId: this.$store.state.userInfo.userId
      };
      axiosDeleteArticleLike(
        articleInfo,
        res => {
          if (res.status === 200) {
            this.isLike = 0;
            this.articleLikeCount -= 1;
          }
        },
        err => {
          console.log(err);
        }
      );
    },
    setComments() {
      axiosReadComment(
        this.$route.params.articleId,
        res => {
          this.comments = res.data;
        },
        err => {
          console.log(err);
        }
      );
      this.commentCount = this.comments.length;
    },
    async deleteArticle() {
      await axiosDeleteArticle(
        this.article.articleId,
        () => {
          swal({
            title: "삭제 완료",
            text: "게시물을 삭제했어요 !",
            icon: "success"
          });
        },
        err => {
          console.log(err);
        }
      );
      this.$router.push({ path: `/board/${this.article.subCategory}` });
    }
  }
};
</script>

<style scoped>
.isLike {
  color: red;
}
.update__btn {
  border: none;
  background-color: var(--color6);
  box-shadow: 0px 5px 0px 0px var(--color5);
}

.update__btn:hover {
  background-color: var(--color4);
}
.delete__btn {
  border: none;
  background-color: #e74c3c;
  box-shadow: 0px 5px 0px 0px #ce3323;
}

.delete__btn:hover {
  background-color: #ff6656;
}
/* button div */
#buttons {
  /* padding-top: 50px; */
  text-align: center;
}

/* start da css for da buttons */
.btn {
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
  text-decoration: none;
  margin: 3px;
  color: #fff;
  position: relative;
  display: inline-block;
}

.btn:active {
  transform: translate(0px, 5px);
  -webkit-transform: translate(0px, 5px);
  box-shadow: 0px 1px 0px 0px;
}
</style>
