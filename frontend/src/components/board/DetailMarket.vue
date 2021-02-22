<template>
  <v-container>
    <!-- 뒤로가기 -->
    <div class="mb-2">
      <router-link :to="{ path: `/board/${article.subCategory}` }">
        <span style="font-size:25px"
          ><font-awesome-icon class="mr-3" icon="chevron-left" />뒤로가기</span
        >
      </router-link>
    </div>
    <div class="card card2 m-5 ">
      <div class="m-3" v-bind="article">
        <div id="editor">
          <div id="title" class="d-flex justify-content-between">
            <div class="d-flex">
              <!-- 가격 -->
              <div class="saleCallout ml-3">
                <p>가격: {{ article.price }}원</p>
              </div>
              <!-- 제목 -->
              <div class="market__title ml-15">
                <b
                  ><p>{{ article.title }}</p></b
                >
              </div>
            </div>
            <!-- 작성자 -->
            <div class="market__author">
              <router-link
                :to="{
                  name: 'Profile',
                  params: { nickname: nickname }
                }"
                style="color: #636DA6"
                ><h4>{{ nickname }}</h4>
              </router-link>
            </div>
          </div>

          <v-divider></v-divider>
          <!-- 내용 -->
          <div id="content" class="d-flex justify-content-start">
            <v-row>
              <v-col cols="6">
                <div>
                  <img
                    v-if="article.articleImage"
                    style="height:250px"
                    :src="article.articleImage || defaultImage"
                    alt=""
                    class="ml-10"
                  />
                  <div class="d-flex flex-direction-row"></div>
                </div>
              </v-col>
              <v-col>
                <p style="white-space:normal">{{ article.content }}</p>
              </v-col>
            </v-row>
          </div>
        </div>

        <div class="d-flex justify-content-between">
          <!-- 좋아요/댓글 수 -->
          <ul class="status d-flex justify-content-end ml-10 mt-10">
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
          <!--수정/삭제 버튼-->
          <div
            id="buttons"
            class=" d-flex justify-content-end mb-3"
            v-if="isYourself"
          >
            <b-button
              class="btn update__btn"
              id="updateBtn"
              @click="updateArticle"
              >수정</b-button
            >
            <b-button class="btn delete__btn" @click="deleteArticle"
              >삭제</b-button
            >
            <!-- 작성자는 완료 버튼도 볼 수 있게 한다 -->
          </div>
        </div>

        <v-divider></v-divider>
        <div class="ml-1 market__comment">
          <p>댓글</p>
        </div>

        <div>
          <CommentWrite
            :articleId="article.articleId"
            @commentCreate="setComments"
          />
        </div>
        <div>
          <Comment
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
import CommentWrite from "@/components/board/comment/CommentWrite";
import Comment from "@/components/board/comment/Comment";
import swal from "sweetalert";

export default {
  name: "DetailMarket",
  data() {
    return {
      article: {},
      comments: [],
      articleLikeCount: 0,
      commentCount: 0,
      isLike: 0,
      nickname: "",
      defaultImage: require("@/assets/images/SSAFYING_LOGO.png")
    };
  },
  created() {
    axiosReadOneArticle(
      this.$route.params.articleId,
      res => {
        this.nickname = res.data.nickname;
        this.isLike = res.data.isLike;
        this.articleLikeCount = res.data.articleLikeCount;
        this.commentCount = res.data.commentCount;
        this.article = res.data.data;
        this.image = res.data.image;
      },
      err => {
        console.log(err);
      }
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
    CommentWrite,
    Comment
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
      console.log("unlike btn clicked");
      const articleInfo = {
        articleId: this.article.articleId,
        userId: this.$store.state.userInfo.userId
      };
      // console.log(articleInfo);
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
    },
    // updateArticle() {
    //   this.$router.replace({
    //     name: "UpdateMarket",
    //     params: { articleId: this.article.articleId }
    //   });
    // }

    updateArticle() {
      const btns = document.querySelectorAll("button");
      const updateBtn = document.getElementById("updateBtn");
      const editables = document.querySelectorAll("#title, #content");

      if (!editables[0].isContentEditable) {
        btns.forEach(btn => {
          if (btn.id !== "updateBtn") btn.style.display = "none";
        });
        editables[0].contentEditable = "true";
        editables[1].contentEditable = "true";
        updateBtn.innerHTML = "저장";
      } else {
        // 저장 버튼을 누르기 전 변경된 내용을 저장한다.
        this.article.title = editables[0].innerText;
        this.article.content = editables[1].innerText;

        // 이 때 axios요청을 보내 글을 수정한다.
        axiosUpdateArticle(
          this.article,
          () => {},
          err => {
            console.log(err);
          }
        );

        // display:none이었던 버튼 돌려주기
        btns.forEach(btn => {
          if (btn.style.display === "none") btn.style.display = "inline-block";
        });
        editables[0].contentEditable = "false";
        editables[1].contentEditable = "false";
        updateBtn.innerHTML = "수정";
      }
    }
  }
};
</script>

<style scoped>
.isLike {
  color: red;
}
.card__author {
  font-weight: 600;
  text-decoration: none;
  color: #0abab5;
}
.market__author {
  color: #0abab5;
}
.card2 {
  border-radius: 0;
  border-width: 10px;
  border-color: var(--color4);
}
/* .card {
  border: none;
} */
.saleCallout {
  display: inline-block;
  box-sizing: content-box;
  height: 22px;
  position: relative;
  content: "";
  cursor: pointer;
  margin: 0 13px 0 0;
  padding: 10px 20px 10px 8px;
  border: none;
  border-radius: 4px 1px 1px 4px;
  color: rgba(255, 255, 255, 1);
  text-align: center;
  text-transform: uppercase;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  background: var(--color5);
  box-shadow: 0 5px 0 0 var(--color1), 5px 5px 0 0 var(--color1);
  transform: rotate(-20deg);
  top: 20px;
}
/* btns */

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

.market__title {
  font-size: 24px;
}
.market__comment {
  font-size: 22px;
}
</style>
