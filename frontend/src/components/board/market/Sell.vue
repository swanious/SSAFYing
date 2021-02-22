<template>
  <v-container>
    <v-sheet>
      <!--게시글이 들어갈 곳-->
      <!--싸피장터 페이지 안내문구-->
      <div class="market__header">
        <h3><b>팔고 싶은 물건이 있어요!</b></h3>
      </div>

      <!-- 컴포넌트 router -->
      <div class="community__links">
        <div class="btn__group">
          <v-btn value="전체" to="/board/market">전체</v-btn>
          <v-btn value="삽니다" to="/board/buy"> 삽니다 </v-btn>
          <v-btn id="active" value="팝니다" to="/board/sell">팝니다 </v-btn>
          <v-btn value="나눔/공구" to="/board/share">나눔/공구</v-btn>
          <v-btn value="원룸" to="/board/room">원룸</v-btn>
        </div>
      </div>
      <!-- 글쓰기 버튼 -->
      <b-button class="create__btn1" @click="writeArticle">
        <font-awesome-icon icon="pencil-alt" />
        <span class="mx-1" style="font-size:14px">글쓰기</span>
      </b-button>
      <b-button @click="writeArticle" class="create__btn2" size="lg">
        <font-awesome-icon icon="pencil-alt" />
      </b-button>

      <!-- 사이드 바 -->
      <div class="community__sidebar">
        <Sidebar />
      </div>

      <!--싸피장터 삽니다 목록-->
      <div class="community__contents board">
        <!-- 지역별 선택 -->
        <div
          class="btn__group"
          style="text-align:right; margin-right:20px"
          id="locations"
        >
          <div class="filter">
            <label
              ><input
                type="radio"
                v-model="location"
                value="전체"
                class="radio__input"
              />
              전체</label
            >&nbsp;
            <label
              ><input
                type="radio"
                v-model="location"
                value="서울"
                class="radio__input"
              />
              서울</label
            >&nbsp;
            <label
              ><input
                type="radio"
                v-model="location"
                value="광주"
                class="radio__input"
              />
              광주</label
            >&nbsp;
            <label
              ><input
                type="radio"
                v-model="location"
                value="대전"
                class="radio__input"
              />
              대전</label
            >&nbsp;
            <label
              ><input
                type="radio"
                v-model="location"
                value="구미"
                class="radio__input"
              />
              구미</label
            >
          </div>
        </div>
        <div>
          <div v-for="(article, index) in filteredArticles" :key="index">
            <div
              class="card pl-5 pr-5 pt-2 pb-2 mt-3"
              @click="viewArticle(article.articleDto)"
            >
              <v-row>
                <v-col
                  ><!-- 가격 -->
                  <div class="saleCallout">
                    <p>가격: {{ article.articleDto.price }}원</p>
                  </div>

                  <!-- 이미지 -->
                  <div class="">
                    <img
                      style="width:200px"
                      :src="article.articleDto.articleImage || defaultImage"
                      alt=""
                    />
                    <h3></h3>
                  </div>
                </v-col>

                <!-- 카드 content -->
                <v-col>
                  <div class="d-flex justify-content-between mt-4">
                    <!-- 카테고리 분류 - 장소 -->
                    <span class="card__category">
                      {{ category }}-{{ article.articleDto.location }}</span
                    >

                    <!-- 작성자 -->
                    <div>
                      <h4 class="market__author ml-2">
                        <router-link
                          :to="{
                            name: 'Profile',
                            params: { nickname: article.nickname }
                          }"
                          style="color: #636DA6"
                          >{{ article.nickname }}</router-link
                        >
                      </h4>
                    </div>
                  </div>
                  <div class="d-flex justify-content-start mt-5">
                    <div
                      class="card__title "
                      :class="{
                        title: article,
                        trending:
                          article.articleDto.watchCount > 10 ? true : false
                      }"
                    >
                      <font-awesome-icon
                        v-if="article.articleDto.watchCount > 10"
                        class="mx-1"
                        :icon="['fas', 'fire-alt']"
                      />
                    </div>
                    <!-- 제목 -->
                    <div class="market__title__font mt-1">
                      <b> {{ article.articleDto.title | truncate(30) }}</b>
                    </div>
                  </div>

                  <!-- 내용 -->
                  <div class="market__content__font mt-3">
                    <div>
                      <span style="text-overflow:ellipsis">{{
                        article.articleDto.content
                      }}</span>
                    </div>
                  </div>
                </v-col>
              </v-row>
              <v-row>
                <div></div>
              </v-row>

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
        </div>
      </div></v-sheet
    >
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
    const subCategory = "sell";
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
  data: () => ({
    articles: [],
    pageOfItems: [],
    category: "팝니다",
    locations: ["전체", "서울", "광주", "대전", "구미"],
    currentDate: "00:00:00",
    defaultImage: require("@/assets/images/SSAFYING_LOGO.png"),
    location: "전체"
  }),
  components: {
    Sidebar
  },
  computed: {
    ...mapState(["fields"]),
    filteredArticles: function() {
      var vm = this;
      var location = vm.location;
      // console.log(location);
      // console.log(vm.articles);
      if (location === "전체") {
        // console.log(location);
        return vm.articles;
      } else {
        return vm.articles.filter(function(article) {
          // console.log(article);
          return article.articleDto.location === location;
        });
      }
    }
  },
  methods: {
    // onChangePage(pageOfItems) {
    //   // update page of items
    //   this.pageOfItems = pageOfItems;
    // },
    writeArticle() {
      this.$router.push({
        name: "RegisterMarket",
        params: {
          mainCategory: "market",
          subCategory: "sell",
          category: "팝니다"
        }
      });
    },
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
  /* justify-content: left; */
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
.market__header {
  margin: 0 0 4rem 5px;
  color: #2e2e2e;
}
body,
html {
  font-family: "Roboto Slab", serif;
  margin: 0;
  width: 100%;
  height: 100%;
  padding: 0;
}
/* 전역 색 불러오기 */
.card {
  border-radius: 0;
  border-width: 10px;
  border-color: var(--color4);
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

.card__like {
  width: 18px;
}

.card__clock {
  width: 15px;
  vertical-align: middle;
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
  width: 100%;
  opacity: 0;
  top: 0;
}

.card__img--hover {
  transition: 0.2s all ease-out;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  position: absolute;
  height: 235px;
  top: 0;
}
.card {
  margin-right: 20px;
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
.card:hover {
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
}

.card__category {
  font-size: 15px;
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
  /* font-weight: 500; */
  text-decoration: none;
  color: #0abab5;
}

.card:hover .card__img--hover {
  height: 100%;
  opacity: 0.3;
}

.card:hover .card__info {
  background-color: transparent;
  position: relative;
}

.card:hover .card__info-hover {
  opacity: 1;
}
.trending {
  color: #f24ba6;
}
img {
  display: block;
  margin: 0px auto;
}
.market__author {
  color: #0abab5;
}
/* radio-btns  */
.radio__input {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  -o-appearance: none;
  appearance: none;
  position: relative;
  /* top: 13.33333px; */
  /* right: 13; */
  bottom: 0;
  left: 0;
  height: 15px;
  width: 15px;
  transition: all 0.15s ease-out 0s;
  background: #cbd1d8;
  border: none;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  /* margin-right: 0.5rem; */
  outline: none;
  /* position: relative; */
  /* z-index: 1000; */
}
.radio__input:hover {
  background: #9faab7;
}
.radio__input:checked {
  background: #d9b0ca;
}
.radio__input:checked::before {
  height: 10px;
  width: 10px;
  position: absolute;
  content: "✔";
  display: inline-block;
  font-size: 26.66667px;
  text-align: center;
  line-height: 5px;
}
.radio__input:checked::after {
  -webkit-animation: click-wave 0.65s;
  -moz-animation: click-wave 0.65s;
  animation: click-wave 0.65s;
  background: #40e0d0;
  content: "";
  display: block;
  position: relative;
  z-index: 100;
}
.radio__input.radio {
  border-radius: 50%;
}
.radio__input.radio::after {
  border-radius: 50%;
}
.filter {
  display: inline-block;
}
/* card글씨크기 */
.market__title__font {
  font-size: 20px;
}
.market__price__font {
  font-size: 18px;
  display: flex;
  justify-content: flex-end;
}
.market__content__font {
  font-size: 16px;
}

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
  /* h5 {
    font-family: arial;
    font-size: 20px;
    color: #fff;
    margin-top: 0;
  } */
  /* &:before {
    display: inline-block;
    box-sizing: content-box;
    z-index: 1;
    width: 30px;
    height: 30px;
    position: absolute;
    content: "";
    cursor: pointer;
    top: 6px;
    right: -16px;
    border: none;
    border-radius: 1px 1px 4px;
    color: #fff;
    -o-text-overflow: clip;
    text-overflow: clip;
    background: #3396f4;
    box-shadow: 0 6px 0 0 #ffe651;
    text-shadow: none;
    transform: rotateY(1deg) rotateZ(-45deg);
  }
  /* &:after { 
    display: inline-block;
    box-sizing: content-box;
    z-index: 2;
    width: 12px;
    height: 12px;
    position: absolute;
    content: "";
    cursor: pointer;
    top: 16px;
    right: 0;
    border: none;
    border-radius: 10px;
    color: rgba(255, 255, 255, 0.9);
    -o-text-overflow: clip;
    text-overflow: clip;
    background: #fcfcfc;
    box-shadow: 5px 5px 0 0 #ffe651 inset;
    text-shadow: none;
  } */
}
</style>
