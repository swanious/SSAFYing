<template>
  <v-app id="inspire">
    <Header />
    <v-main>
      <v-container>
        <div class="main__sidebar mt-3">
          <Sidebar :weatherItem="weatherItem" :recommends="recommends" />
        </div>
        <div class="main__contents mt-3">
          <v-sheet>
            <div class="card">
              <img
                src="http://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FdAdMtu%2FbtqD55h0tGZ%2FNJkQ2LKGkmfm0UOH7qa7TK%2Fimg.png"
              />
            </div>

            <div class="card mt-3">
              <HomeCommunity :commuItems="commuItems" />
            </div>
            <!-- <div class="card mt-3">
              <HomeGroup />
            </div> -->
            <div class="card mt-3">
              <div class="card-header">공식문서</div>
              <OfficialDocsSites />
            </div>

            <div class="card mt-3">
              <HomeInformation :infoItems="infoItems" />
              <!-- <HomeInformation :commuItems="commuItems" /> -->
            </div>
            <div class="card mt-3">
              <div class="card-header">기술블로그</div>
              <TechBlogSites />
            </div>
            <div class="card mt-3">
              <KakaoMap />
            </div>
          </v-sheet>
        </div>
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Header from "../components/common/Header.vue";
import Sidebar from "@/components/common/Sidebar.vue";
import Footer from "../components/common/Footer.vue";

import HomeCommunity from "../components/mainpage/home/HomeCommunity.vue";
// import HomeGroup from "../components/mainpage/home/HomeGroup.vue";
import HomeInformation from "../components/mainpage/home/HomeInformation.vue";
import OfficialDocsSites from "../components/mainpage/home/OfficialDocsSites.vue";
import TechBlogSites from "../components/mainpage/home/TechBlogSites.vue";
import KakaoMap from "../components/mainpage/home/KakaoMap.vue";
// import HomeMarket from "../components/mainpage/home/HomeMarket.vue";
// import HomeImage from "../components/mainpage/home/HomeImage.vue";
// import HomeCarousel from "../components/mainpage/home/HomeCarousel.vue";

import axios from "axios";

import { axiosGetMain } from "@/api/redis.js";

export default {
  data: () => ({
    items: [
      { title: "프로필" },
      { title: "그룹 관리" },
      { title: "작성 게시글" },
      { title: "추천 게시글" }
    ],
    commuItems: { anonymous: null, free: null, grade: null },
    infoItems: { it: null, job: null, life: null },
    recommends: {},
    weatherItem: {}
  }),
  components: {
    KakaoMap,
    Footer,
    Sidebar,
    Header,
    OfficialDocsSites,
    TechBlogSites,
    HomeCommunity,
    HomeInformation
  },
  async beforeCreate() {
    await axios
      .get(
        `http://api.openweathermap.org/data/2.5/weather?lat=35.2&lon=126.8&appid=eabf45de56fe348afc37646a9498ec0e&lang=kr`
      )
      .then(res => {
        // console.log(res.data);
        this.weatherItem = res.data;
        // console.log(typeof res.data);
      })
      .catch(err => {
        console.log(err);
      });
  },
  created() {
    axiosGetMain(
      res => {
        // console.log(res.data);
        this.commuItems.anonymous = res.data.anonymous;
        this.commuItems.free = res.data.free;
        this.commuItems.grade = res.data.grade;
        this.infoItems.it = res.data.it;
        this.infoItems.job = res.data.job;
        this.infoItems.life = res.data.life;
        this.recommends = res.data.recommend;
        // console.log(this.recommends);
      },
      err => {
        console.log(err);
      }
    );
  }
};
</script>
<style scoped>
@import "../assets/css/home.css";

.board-card {
  margin: 0 5px 5px 0;
}

.home__category__margin {
  margin: 5px;
}

.board-name {
  margin: 10px;
}

.board-info {
  margin: 10px;
}

.board {
  margin: 0;
}

a {
  text-decoration: none;
}

.home__contents__card {
  margin: 10px;
}

.article__list__title {
  display: flex;
  justify-content: space-between;
  margin-left: 15px;
}
.home__category__title {
  margin: 10px;
}
.home__card__square .card {
  border-radius: 0;
}
</style>
