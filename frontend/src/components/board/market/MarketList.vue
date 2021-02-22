<template>
  <div title="최신 글 목록" class="card ml-2">
    <div class="m-3"><h4>싸피장터 최신 글</h4></div>

    <div v-for="(article, index) in filteredArticles" :key="index" class="m-3">
      <div class="cards" @click="viewArticle(article.articleDto)">
        <!-- <v-col class="marketlist__card"> -->
        <article class="card card2 card--1 card__article">
          <div class="card__info-hover">
            <!-- 작성 시각 -->
            <div class="card__clock-info">
              <span class="card__time">{{
                article.articleDto.createdAt | moment("from", "now")
              }}</span>
            </div>
          </div>

          <!-- 이미지 -->
          <!-- <div class="card__img"></div> -->
          <a href="#" class="card_link">
            <div class="card__img--hover"></div>
            <img
              style="width:200px"
              :src="article.articleDto.articleImage || defaultImage"
              alt=""
            />
          </a>

          <!-- 카드 info -->
          <div class="card__info">
            <!-- 카테고리 -->
            <span class="card__category">
              {{ article.articleDto.subCategory }}-{{
                article.articleDto.location
              }}</span
            >
            <!-- 제목 -->
            <div
              class="card__title"
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
              <h6>
                <b> {{ article.articleDto.title | truncate(30) }}</b>
              </h6>
            </div>

            <!-- 가격 -->
            <p class="card__price">{{ article.articleDto.price }}원</p>
            <!-- 작성자 -->
            <div class=" d-flex justify-content-end">
              <span class="card__by"
                >작성자<a href="#" class="card__author" title="작성자">
                  {{ article.nickname }}</a
                ></span
              >
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<script>
// import Vue from "vue";
// import VueRouter from "vue-router";
import { mapState } from "vuex";
import { axiosReadMain } from "@/api/article.js";
export default {
  created() {
    const mainCategory = "market";
    axiosReadMain(
      mainCategory,
      res => {
        // console.log(res.data);
        this.articles = res.data.slice(0, 4);
      },
      err => {
        console.log(err);
      }
    );
  },
  data: () => ({
    articles: [],
    category: ["전체", "삽니다", "팝니다", "나눔/공구", "원룸"],
    locations: ["전체", "서울", "광주", "대전", "구미"],
    defaultImage: require("@/assets/images/SSAFYING_LOGO.png"),
    location: "전체"
  }),

  computed: {
    ...mapState(["fields"]),
    filteredArticles: function() {
      var vm = this;
      var location = vm.location;
      console.log(location);
      console.log(vm.articles);
      if (location === "전체") {
        console.log(location);
        return vm.articles;
      } else {
        return vm.articles.filter(function(article) {
          console.log(article);
          return article.articleDto.location === location;
        });
      }
    }
  },
  methods: {
    viewArticle(item) {
      this.$router.replace({
        name: "DetailMarket",
        params: {
          articleId: item.articleId
        }
      });
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");

* {
  box-sizing: border-box;
}

body,
html {
  font-family: "Roboto Slab", serif;
  margin: 0;
  width: 100%;
  height: 100%;
  padding: 0;
}
.card {
  border-radius: 0;
}
body {
  background-color: #d2dbdd;
  display: flex;
  display: -webkit-flex;
  -webkit-justify-content: center;
  -webkit-align-items: center;
  justify-content: center;
  align-items: center;
}

.cards {
  width: 100%;
  display: flex;
  display: -webkit-flex;
  justify-content: center;
  -webkit-justify-content: center;
}

/* .card--1 .card__img,
.card--1 .card__img--hover {
  background-image: url("https://www.costco.co.kr/medias/sys_master/ha9/h3a/8805810798622.jpg");
} */

.card__like {
  width: 18px;
}

.card__clock {
  width: 15px;
  /* vertical-align: end; */
  fill: #0abab5;
}
.card__time {
  font-size: 12px;
  color: #0abab5;
  vertical-align: middle;
  margin-left: 5px;
}

.card__clock-info {
  float: right;
}

.card__img {
  visibility: hidden;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 200px;
}

.card__info-hover {
  position: absolute;
  padding: 16px;
  /* width: 100%; */
  opacity: 0;
  top: 0;
}

.card__img--hover {
  transition: 0.2s all ease-out;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  /* width: 100%; */
  position: absolute;
  /* height: 235px; */
  top: 0;
}
.card2 {
  margin-right: 15px;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0, 1);
  background-color: #fff;
  /* width: 50%; */
  position: relative;
  overflow: hidden;
  box-shadow: 0px 13px 10px -7px rgba(0, 0, 0, 0.1);
}
.card__article {
  width: 50%;
}
.card2:hover {
  box-shadow: 0px 30px 18px -8px rgba(0, 0, 0, 0.1);
  transform: scale(1.1, 1.1);
}

.card__info {
  z-index: 2;
  background-color: #fff;
  padding: 10px 15px 15px 15px;
  height: 40%;
  border-top-style: solid;
  border-width: 1px;
  border-color: #f6f1eb;
  /* width: ; */
}

.card__category {
  font-size: 12px;
  letter-spacing: 2px;
  font-weight: 500;
  color: #868686;
}

.card__title {
  margin-top: 5px;
  margin-bottom: 10px;
  font-size: 13px;
}

.card__price {
  margin-top: 5px;
  margin-bottom: 10px;
  font-size: 12px;
}
.card__by {
  font-size: 11px;
  font-family: "Raleway", sans-serif;
  font-weight: 500;
}

.card__author {
  font-weight: 600;
  text-decoration: none;
  color: #0abab5;
}

.card2:hover .card__img--hover {
  height: 100%;
  opacity: 0.3;
}

.card2:hover .card__info {
  background-color: transparent;
  position: relative;
}

.card2:hover .card__info-hover {
  opacity: 1;
}
.market__title__margin {
  margin: 5px 15px 8px;
  padding: 10;
}

.market__title__padding {
  padding: 10px;
}

.main-card-contents {
  margin: 10px;
}

.market__main__section {
  display: flex;
  justify-content: space-between;
}
.marketlist__nickname-time {
  display: flex;
  margin: 10px;
}
.marketlist__userimg__size {
  width: 30px;
  height: 30px;
}
.market__nickname__a {
  display: flex;
  justify-content: left;
}
.marketlist__article__title {
  margin: 10px;
}
.marketlist__article__content {
  margin: 10px;
}
.market__category__location {
  margin: 10px;
}
.market__category__display {
  display: flex;
}
.market__tab__display {
  display: flex;
}
.market__section__outline {
  margin: 10px;
  padding: 10px;
}
.market__author {
  color: #0abab5;
}
.marketlist__card {
  width: 400px;
}
.trending {
  color: #f24ba6;
}
img {
  display: block;
  margin: 0px auto;
}
</style>
