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
        <div class="mb-5">
          <h3>싸피장터 새 게시글 작성</h3>
        </div>
        <v-divider></v-divider>
        <div class="d-flex justify-content-start">
          <div><p>제목</p></div>
          <div><b-input type="text" v-model="title" /></div>
        </div>
        <v-divider></v-divider>
        <div class="d-flex justify-content-start">
          <div><p>가격</p></div>
          <div><b-input type="text" v-model="price" /></div>
          <div><p>원</p></div>
          <div>
            <div class="container" id="locations">
              <div class="filter">
                <!-- <label
                  ><input type="radio" v-model="location" value="전체" />
                  전체</label
                > -->
                <label
                  ><input type="radio" v-model="location" value="서울" />
                  서울</label
                >
                <label
                  ><input type="radio" v-model="location" value="광주" />
                  광주</label
                >
                <label
                  ><input type="radio" v-model="location" value="대전" />
                  대전</label
                >
                <label
                  ><input type="radio" v-model="location" value="구미" />
                  구미</label
                >
              </div>
            </div>
          </div>
        </div>

        <div>
          <v-textarea
            background-color="grey lighten-2"
            color="cyan"
            label="Label"
          ></v-textarea>
          <p>내용</p>
          <b-form-textarea v-model="content" @keypress.enter="writeArticle" />
        </div>

        <hr />
        <b-form-file
          v-model="articleImage"
          :state="Boolean(articleImage)"
          placeholder="사진을 업로드 해주세요"
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">
          선택한 사진: {{ articleImage ? articleImage : "" }}
        </div>
        <hr />
        <b-button @click="writeArticle">작성</b-button>
      </div>
    </div>
  </v-container>
</template>

<script>
import { axiosCreateArticle } from "@/api/article.js";

export default {
  name: "RegisterMarket",

  data() {
    return {
      title: "",
      price: "",
      content: "",
      articleImage: null,
      articleImageStr: "",
      mainCategory: this.$route.params.mainCategory,
      subCategory: this.$route.params.subCategory,
      category: "",
      locations: ["전체", "서울", "광주", "대전", "구미"],
      // imageError: false,
      defaultImage: require("@/assets/images/SSAFYING_LOGO.png"),
      selected: "전체",
      location: "서울"
      // selectedLocation: "전체"
    };
  },
  // created() {
  //   console.log(this.subCategory);
  // },

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
          price: this.price,
          defaultImage: this.defaultImage,
          //price: 0,
          location: this.location
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

        this.$router.push({ path: `/board/${this.subCategory}/` });
      } else {
        alert("login required");
      }
    }
  }
};
</script>

<style scoped>
.card {
  border-radius: 0;
}
</style>
