<template>
  <v-row>
    <v-col></v-col>
    <v-col cols="8">
      <!--게시글이 들어갈 곳-->
      <v-sheet>
        <div class="market__title__margin market__title__padding">
          <!--싸피장터 페이지 안내문구-->
          <div class="main-section">
            <h3><b>싸피장터</b></h3>
          </div>

          <!-- 컴포넌트 router -->
          <div class="d-flex justify-content-between">
            <div>
              <v-btn-toggle v-model="category" tile color="blue darken-3" group>
                <v-btn value="전체" to="/board/market">전체</v-btn>
                <v-btn value="자유게시판" to="/board/buy"> 삽니다 </v-btn>
                <v-btn value="익명게시판" to="/board/sell">팝니다 </v-btn>
                <v-btn to="/board/share">나눔/공구</v-btn>
                <v-btn to="/board/room">원룸</v-btn>
              </v-btn-toggle>
            </div>
            <!-- 첫번째 줄 : 카테고리 -->
            <div class="market__category__display">
              <!-- 1. 서브 카테고리 -->
              <!-- <div>
                <v-chip-group mandatory active-class="primary--text">
                  <v-chip
                    small
                    tile
                    v-for="(market, index) in markets"
                    :key="index"
                    @click="changeComponent(`${market[1]}`)"
                  >
                    {{ market[0] }}
                  </v-chip>
                </v-chip-group>
              </div> -->

              <!-- 2. 지역 카테고리-->
              <div>
                <b-dropdown
                  id="dropdown-1"
                  text="지역 선택"
                  variant="info"
                  class="m-md-2"
                  size="sm"
                >
                  <div v-for="location in locations" :key="location.id">
                    <b-dropdown-item>{{ location }}</b-dropdown-item>
                  </div>
                </b-dropdown>
              </div>

              <!-- 글쓰기 버튼 -->
              <div>
                <v-btn tile class="m-md-2" small color="info">글쓰기</v-btn>
              </div>
            </div>
          </div>
          <!--싸피장터 목록-->
          <div class="market__title__padding">
            <!-- <keep-alive> -->
            <component v-bind:is="comp"></component>
            <!-- </keep-alive> -->
          </div>
        </div>
      </v-sheet>
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script>
// import { mapState } from 'vuex'
// import { axiosReadMain } from "@/api/market.js"
import All from "@/components/board/market/All.vue";
import Buy from "@/components/board/market/Buy.vue";
import Sell from "@/components/board/market/Sell.vue";
import Room from "@/components/board/market/Room.vue";
import Share from "@/components/board/market/Share.vue";

export default {
  components: {
    All,
    Buy,
    Sell,
    Room,
    Share
  },
  data: () => ({
    comp: "",
    articles: [],
    markets: [
      ["전체", "All"],
      ["삽니다", "Buy"],
      ["팝니다", "Sell"],
      ["나눔/공구", "Share"],
      ["원룸", "Room"]
    ],
    titles: ["전체", "삽니다", "팝니다", "나눔/공구", "원룸"],
    locations: ["전체", "서울", "광주", "대전", "구미"],
    links: [
      "/board/market",
      "/board/buy",
      "/board//sell",
      "/board/share",
      "/board/room"
    ],
    sections: ["삽니다!", "팝니다!", "나눔/공구해요!", "원룸"],
    // icons: ["cart-plus","cart-dash","basket","shop"],
    fields: ["분류", "장소", "글제목", "사진", "작성자", "작성일"],
    items: [
      {
        분류: "삽니다",
        글제목: "매트리스 구해요",
        user_img: "",
        장소: "서울",
        작성자: "LynnLee",
        작성시간: "1시간 전"
      },
      {
        분류: "팝니다",
        글제목: "매트리스 팔아요",
        user_img: "",
        장소: "광주",
        작성자: "JayLee",
        작성시간: "1시간 전"
      }
    ]
  }),
  methods: {
    changeComponent: function(componentName) {
      this.comp = componentName;
    }
  }
};
</script>

<style>
.market__title__margin {
  margin: 5px 15px 8px;
  padding: 10;
}

.market__title__padding {
  padding: 10px;
}

.main-card-contents {
  margin: 10px;
  /* border-radius: 20px; */
}

/* .main-article-title {
  display: flex;
  justify-content: space-between;
  margin: 10px;
} */

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
</style>
