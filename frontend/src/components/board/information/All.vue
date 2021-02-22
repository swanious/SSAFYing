<template>
  <v-container>
    <!-- 헤더 -->
    <div class="community__header">
      <h3><b>싸피인들간에 정보를 공유하는 게시판입니다</b></h3>
    </div>

    <!-- 컴포넌트 router -->
    <div class="community__links">
      <div class="btn__group">
        <v-btn id="active" to="/board/information">전체</v-btn>
        <v-btn to="/board/it">IT</v-btn>
        <v-btn to="/board/job">취업</v-btn>
        <v-btn to="/board/life">생활</v-btn>
      </div>
    </div>

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
              <b> {{ article.articleDto.title | truncate(30) }}</b>
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

          <div class="status">
            <div class="status__icons">
              <ul class="d-flex justify-content-end">
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'thumbs-up']"
                  />{{ article.articleLike }}
                </li>
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'comment-dots']"
                  />{{ article.articleCommentCount }}
                </li>
                <li class="mx-1">
                  <font-awesome-icon class="mx-1" :icon="['far', 'eye']" />
                  {{ article.articleDto.watchCount }}
                </li>
              </ul>
            </div>
            <div class="status__time d-flex justify-content-end mt-1">
              <span
                >{{ article.articleDto.createdAt | moment("from", "now") }}
              </span>
            </div>
          </div>
        </div>
      </div>
      <!-- 페이지네이션 -->
      <div id="pagination" class="d-flex justify-content-center">
        <div class="my-5">
          <jw-pagination
            :pageSize="pageSize"
            :items="articles"
            @changePage="onChangePage"
          ></jw-pagination>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import Vue from "vue";
import VueRouter from "vue-router";
import { mapState } from "vuex";
import { axiosReadMain } from "@/api/article.js";

import Sidebar from "@/components/common/Sidebar.vue";

Vue.use(VueRouter);

export default {
  created() {
    const mainCategory = "information";
    axiosReadMain(
      mainCategory,
      res => {
        this.articles = res.data;
        // console.log(this.articles);
      },
      err => {
        console.log(err);
      }
    );
  },
  data() {
    return {
      articles: [],
      pageSize: 5,
      pageOfItems: [],
      category: "전체",
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
        name: "Detail",
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
