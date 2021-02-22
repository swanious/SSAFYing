<template>
  <v-container>
    <v-sheet>
      <!-- 헤더 -->
      <div class="community__header">
        <h3><b>익명으로 나누는 넋두리,고민,시시콜콜한 이야기</b></h3>
      </div>

      <div class="community__links">
        <!-- 컴포넌트 router -->
        <div class="btn__group">
          <v-btn to="/board/community">전체</v-btn>
          <v-btn to="/board/free"> 자유게시판 </v-btn>
          <v-btn id="active" to="/board/anonymous">익명게시판 </v-btn>
          <v-btn to="/board/grade">기수게시판</v-btn>
        </div>

        <!-- 글쓰기 -->
        <div class="btn__write">
          <button class="btn btn__write" @click="writeArticle">
            <font-awesome-icon icon="pencil-alt" />
          </button>
        </div>
      </div>

      <b-button @click="writeArticle" class="create__btn2" size="lg">
        <font-awesome-icon icon="pencil-alt" />
      </b-button>

      <div class="community__sidebar">
        <Sidebar />
      </div>

      <!-- 게시글 -->
      <div class="community__contents">
        <div v-for="(article, index) in pageOfItems" :key="index">
          <div class="card p-3" @click="viewArticle(article.articleDto)">
            <div class="d-flex justify-content-between">
              <div
                :class="{
                  title: article,
                  trending: article.articleDto.watchCount > 10 ? true : false
                }"
              >
                <font-awesome-icon
                  v-if="article.articleDto.watchCount > 10"
                  class="mx-1"
                  :icon="['fas', 'fire-alt']"
                />
                <b>{{ article.articleDto.title | truncate(30) }}</b>
              </div>
              <div class="d-flex">
                <font-awesome-icon
                  class="profile__icon"
                  icon="user-circle"
                  :style="{ color: '#3e3e3e' }"
                />
                <div class="ml-2">
                  <router-link
                    :to="{
                      name: 'Profile',
                      params: { nickname: article.nickname }
                    }"
                    style="color: #636DA6"
                    >{{ article.nickname }}</router-link
                  >
                </div>
              </div>
            </div>

            <div class="content">
              <span style="text-overflow:ellipsis">{{
                article.articleDto.content | truncate(100)
              }}</span>
            </div>

            <!-- 좋아요/댓글/조회수/시간 -->
            <div class="status">
              <div class="status__icons">
                <ul class="d-flex justify-content-end">
                  <li>
                    <font-awesome-icon :icon="['far', 'thumbs-up']" />{{
                      article.articleLike
                    }}
                  </li>
                  <li>
                    <font-awesome-icon
                      class="mx-1"
                      :icon="['far', 'comment-dots']"
                    />{{ article.articleCommentCount }}
                  </li>
                  <li>
                    <font-awesome-icon class="mx-1" :icon="['far', 'eye']" />
                    {{ article.articleDto.watchCount }}
                  </li>
                </ul>
              </div>
              <div class="status__time d-flex justify-content-end mt-1">
                <span>{{
                  article.articleDto.createdAt | moment("from", "now")
                }}</span>
              </div>
            </div>
          </div>
        </div>
        <!-- 페이지네이션 -->
        <div id="pagination" class="d-flex justify-content-center">
          <div class="mt-5">
            <jw-pagination
              :pageSize="pageSize"
              :items="articles"
              @changePage="onChangePage"
            ></jw-pagination>
          </div>
        </div>
      </div>
    </v-sheet>
  </v-container>
</template>

<script>
import Vue from "vue";
import VueRouter from "vue-router";
import { mapState } from "vuex";
import { axiosReadSub } from "@/api/article.js";

import Sidebar from "@/components/common/Sidebar.vue";

Vue.use(VueRouter);

export default {
  beforeCreate() {
    const subCategory = "anonymous";
    this.articles = axiosReadSub(
      subCategory,
      res => {
        this.articles = res.data;
        // console.log(res.data);
      },
      err => {
        console.log(err);
      }
    );
  },
  data() {
    return {
      pageSize: 5,
      pageOfItems: [],
      articles: [],
      category: "익명게시판",
      currentDate: "00:00:00"
    };
  },
  components: {
    Sidebar
  },
  computed: {
    ...mapState(["fields"])
  },
  methods: {
    onChangePage(pageOfItems) {
      // update page of items
      this.pageOfItems = pageOfItems;
    },
    writeArticle() {
      this.$router.push({
        name: "Register",
        params: { mainCategory: "community", subCategory: "anonymous" }
      });
    },
    viewArticle(item) {
      this.$router.replace({
        name: "DetailAnonymous",
        params: {
          articleId: item.articleId
        }
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
