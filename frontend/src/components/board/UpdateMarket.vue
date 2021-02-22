<template>
  <v-container>
    <!-- 뒤로가기 -->
    <div class="mb-3">
      <router-link :to="{ path: `/board/${article.subCategory}` }">
        <span style="font-size:20px"
          ><font-awesome-icon class="mr-3" icon="chevron-left" />뒤로가기</span
        >
      </router-link>
    </div>

    <div class="card">
      <div class="m-3">
        <label for="title">제목</label>
        <b-input class="mb-5" name="title" v-model="article.title">{{
          article.title
        }}</b-input>
        <hr />
        <div class="mb-5">
          <img style="width:500px" :src="article.articleImage" alt="" />
        </div>
        <v-divider></v-divider>
        <label for="price">가격</label>

        <b-form-textarea name="price" v-model="article.price">{{
          article.price
        }}</b-form-textarea>
        <hr />
        <label for="content">내용</label>

        <b-form-textarea name="content" v-model="article.content">{{
          article.content
        }}</b-form-textarea>
        <hr />
        <b-form-file
          v-model="articleImage"
          :state="Boolean(articleImage)"
          placeholder="이미지를 추가할 수 있습니다."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">
          선택한 사진: {{ articleImage ? articleImage : "" }}
        </div>
        <hr />
        <div class="d-flex justify-content-end">
          <b-button
            type="submit"
            id="submit"
            @click="updateArticle"
            style="background:var(--color7)"
            >변경</b-button
          >
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import { axiosReadOneArticle, axiosUpdateArticle } from "@/api/article.js";

export default {
  name: "DetailMarket",
  created() {
    axiosReadOneArticle(
      this.$route.params.articleId,
      res => {
        this.article = res.data;
      },
      err => {
        console.log(err);
      }
    );
  },
  data() {
    return {
      article: {}
    };
  },
  methods: {
    updateArticle() {
      axiosUpdateArticle(
        this.article,
        res => {
          this.article = res.data;
        },
        err => {
          console.log(err);
        }
      );
      this.back();
    },
    back() {
      this.$router.replace({
        name: "DetailMarket",
        params: { title: this.article.title }
      });
    }
  }
};
</script>

<style></style>
