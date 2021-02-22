<template>
  <v-container>
    <div class="mb-3">
      <router-link :to="{ path: `/board/${mainCategory}` }">
        <span style="font-size:25px"
          ><font-awesome-icon class="mr-3" icon="chevron-left" />뒤로가기</span
        >
      </router-link>
    </div>

    <div class="card">
      <div class="m-3">
        <h3>글작성</h3>
        <hr />
        <b-input type="text" v-model="title" />
        <hr />
        <b-form-textarea v-model="content" />
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
        <div class="form-group">
          <b-button
            type="submit"
            id="submit"
            class="submit-button"
            @click="writeArticle"
            >작성</b-button
          >
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import { axiosCreateArticle } from "@/api/article.js";

export default {
  name: "Register",

  data() {
    return {
      title: "",
      content: "",
      articleImage: null,
      articleImageStr: "",
      mainCategory: this.$route.params.mainCategory,
      subCategory: this.$route.params.subCategory
    };
  },
  watch: {
    articleImage(newVal) {
      console.log(newVal);
      const reader = new FileReader();
      reader.onload = e => {
        this.articleImageStr = e.target.result;
      };
      reader.readAsDataURL(this.articleImage);
      console.log(reader);
    }
  },
  methods: {
    writeArticle() {
      // console.log(this.subCategory);
      // console.log(this.$store.getters.getUserId);
      console.log(this.$store.state.userInfo);
      if (this.$store.state.isLogin) {
        const articleItem = {
          userId: this.$store.state.userInfo.userId,
          title: this.title,
          content: this.content,
          mainCategory: this.mainCategory,
          subCategory: this.subCategory,
          articleImage: this.articleImageStr,
          price: 0,
          location: "Seoul"
        };
        // console.log(articleItem);
        axiosCreateArticle(
          articleItem,
          response => {
            console.log(response);
          },
          error => {
            console.log(error);
          }
        );
        // this.$store.dispatch("writeArticle", articleItem);
        // 동기적인 처리를 구현해야 할 것 같다.
        // this.$router.push({ name: "CommunityMain" });

        this.$router.push({ path: `/board/${this.subCategory}/` });
      } else {
        alert("login required");
      }
    }
  }
};
</script>

<style scoped>
.form-group {
  margin: 0 auto 1.25rem auto;
  padding: 0.25rem;
}
.submit-button {
  display: block;
  width: 100%;
  padding: 0.75rem;
  background: var(--color1);
  color: inherit;
  border-radius: 2px;
  cursor: pointer;
}
</style>
