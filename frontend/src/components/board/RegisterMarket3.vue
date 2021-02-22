<template>
  <v-container>
    <div class="">
      <router-link :to="{ path: `/board/${mainCategory}` }">
        <span style="font-size:25px"
          ><font-awesome-icon class="mr-3" icon="chevron-left" />뒤로가기</span
        >
      </router-link>
    </div>
    <div class="card">
      <header class="header">
        <h1 id="title" class="text-center mt-5">싸피장터 글 작성하기</h1>
        <p id="description" class="description text-center">
          싸피인들의 중고장터, 싸피장터
        </p>
      </header>
      <form id="survey-form">
        <div class="form-group">
          <label id="title-label" for="title"><b>제목</b></label>
          <input
            type="text"
            name="title"
            v-model="title"
            id="title"
            class="form-control"
            placeholder="제목을 입력해주세요."
            required
          />
        </div>
        <hr />
        <div class="form-group">
          <label id="price-label" for="price"
            ><b>가격</b><span class="clue">(선택사항)</span></label
          >
          <input
            type="text"
            name="price"
            v-model="price"
            id="price"
            class="form-control"
            placeholder="가격을 입력해주세요.(단위:원)"
          />
        </div>
        <hr />
        <div class="form-group">
          <label id="location-label" for="location"><b>지역선택</b></label>
          <div class="" id="locations">
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
        <hr />
        <div class="form-group">
          <label id="content-label" for="content"><b>내용</b></label>
          <!-- <v-textarea
            background-color="grey lighten-2"
            color="cyan"
            label="Label"
          ></v-textarea>
          <p>내용</p> -->
          <b-form-textarea v-model="content" @keypress.enter="writeArticle" />
        </div>
        <hr />
        <div class="form-group">
          <label id="image-label" for="image"><b>사진</b></label>
          <b-form-file
            v-model="articleImage"
            :state="Boolean(articleImage)"
            placeholder="사진을 업로드 해주세요."
            drop-placeholder="Drop file here..."
          ></b-form-file>
          <div class="mt-3">
            선택한 사진: {{ articleImage ? articleImage : "" }}
          </div>
        </div>

        <div class="form-group">
          <b-button
            type="submit"
            id="submit"
            class="submit-button"
            @click="writeArticle"
            >작성</b-button
          >
        </div>
      </form>
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
@import url("https://fonts.googleapis.com/css?family=Poppins:200i,400&display=swap");

:root {
  --color-white: #f3f3f3;
  --color-darkblue: #1b1b32;
  --color-darkblue-alpha: rgba(27, 27, 50, 0.8);
  --color-green: #37af65;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: "Poppins", sans-serif;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.4;
  color: var(--color-white);
  margin: 0;
}

/* mobile friendly alternative to using background-attachment: fixed */
body::before {
  content: "";
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  z-index: -1;
  background: var(--color-darkblue);
  background-image: linear-gradient(
      115deg,
      rgba(58, 58, 158, 0.8),
      rgba(136, 136, 206, 0.7)
    ),
    url(https://cdn.freecodecamp.org/testable-projects-fcc/images/survey-form-background.jpeg);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}

h1 {
  font-weight: 400;
  line-height: 1.2;
}

p {
  font-size: 1.125rem;
}

h1,
p {
  margin-top: 0;
  margin-bottom: 0.5rem;
}

label {
  display: flex;
  align-items: center;
  font-size: 1.125rem;
  margin-bottom: 0.5rem;
}

input,
button,
select,
textarea {
  margin: 0;
  font-family: inherit;
  font-size: inherit;
  line-height: inherit;
}

button {
  border: none;
}

.container {
  width: 100%;
  margin: 3.125rem auto 0 auto;
}

@media (min-width: 576px) {
  .container {
    max-width: 540px;
  }
}

@media (min-width: 768px) {
  .container {
    max-width: 720px;
  }
}

.header {
  padding: 0 0.625rem;
  margin-bottom: 1.875rem;
}

.description {
  font-style: italic;
  font-weight: 200;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
}

.clue {
  margin-left: 0.25rem;
  font-size: 0.9rem;
  color: #e4e4e4;
}

.text-center {
  text-align: center;
}

/* form */

form {
  background: var(--color-darkblue-alpha);
  padding: 2.5rem 0.625rem;
  border-radius: 0.25rem;
}

@media (min-width: 480px) {
  form {
    padding: 2.5rem;
  }
}

.form-group {
  margin: 0 auto 1.25rem auto;
  padding: 0.25rem;
}

.form-control {
  display: block;
  width: 100%;
  height: 2.375rem;
  padding: 0.375rem 0.75rem;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control:focus {
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.input-radio,
.input-checkbox {
  display: inline-block;
  margin-right: 0.625rem;
  min-height: 1.25rem;
  min-width: 1.25rem;
}

.input-textarea {
  min-height: 120px;
  width: 100%;
  padding: 0.625rem;
  resize: vertical;
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
.filter {
  display: flex;
}
</style>
