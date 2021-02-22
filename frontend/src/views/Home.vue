<template>
  <v-app id="inspire">
    <Header />
    <v-main>
      <v-container>
        <div class="main__sidebar">
          <Sidebar />
        </div>
        <div class="main__contents">
          <div>
            <HomeCarousel />
          </div>
          <!-- <div class="home__image">
            <HomeImage />
          </div> -->
          <v-sheet>
            <div class="card mt-3">
              <HomeCommunity />
            </div>
            <div class="card mt-3">
              <HomeGroup />
            </div>

            <div class="card mt-3">
              <HomeInformation />
            </div>

            <div class="card mt-3">
              <HomeMarket />
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
import HomeGroup from "../components/mainpage/home/HomeGroup.vue";
import HomeInformation from "../components/mainpage/home/HomeInformation.vue";
import HomeMarket from "../components/mainpage/home/HomeMarket.vue";
// import HomeImage from "../components/mainpage/home/HomeImage.vue";
import HomeCarousel from "../components/mainpage/home/HomeCarousel.vue";

import "@/assets/css/home.css";
import "@/assets/css/style.css";

export default {
  data: () => ({
    items: [
      { title: "프로필" },
      { title: "그룹 관리" },
      { title: "작성 게시글" },
      { title: "추천 게시글" }
    ]
  }),
  components: {
    Footer,
    Sidebar,
    Header,
    // Articles,
    HomeCommunity,
    HomeGroup,
    HomeInformation,
    HomeMarket,
    // Elements
    // HomeImage,
    HomeCarousel
  }
};
</script>
<style scoped>
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
