<template>
  <v-app id="inspire">
    <Header />
    <v-main>
      <v-container>
        <div class="main__sidebar mt-3">
          <Sidebar />
        </div>
        <div class="main__contents mt-3">
          <v-sheet>
            <div class="card">
              <div class="d-flex justify-content-center mt-3">
                <h2>
                  <b style="color:var(--color7)"
                    >"{{ this.$route.params.tag }}"</b
                  >
                  에 대한 검색결과
                </h2>
              </div>
              <!-- <b-button>
                <b style="color:#636DA6">정확도순</b>
              </b-button>
              <h5>
                <b style="color:#636DA6">최신순</b>
              </h5> -->
              <div class="d-flex ml-3 mb-3">
                <b-button
                  @click="accuracyActive"
                  pill
                  variant="outline-secondary"
                  class="accuracy-btn ml-3"
                  >정확도순</b-button
                >
                <b-button
                  @click="brandnewActive"
                  pill
                  variant="outline-secondary"
                  class="brandnew-btn ml-3"
                  >최신순</b-button
                >
              </div>
              <div v-show="accuracy">
                <h5 class="ml-5 mt-3">
                  <b style="color:var(--color7)" class="">정확도순 검색결과</b>
                </h5>
                <SearchResultItem2
                  v-for="result in results2"
                  :key="result.articleId"
                  :result="result"
                />
              </div>
              <div v-show="brandnew">
                <h5 class="ml-5 mt-3">
                  <b style="color:var(--color7)">최신순 검색결과</b>
                </h5>
                <SearchResultItem
                  v-for="result in results"
                  :key="result.articleId"
                  :result="result"
                />
              </div>
              <!-- <div v-else>
                <h1>
                  검색결과가 없습니다
                </h1>
              </div> -->
            </div>
          </v-sheet>
        </div>
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Sidebar from "@/components/common/Sidebar.vue";
import Footer from "@/components/common/Footer.vue";
import SearchResultItem from "@/components/common/header/SearchResultItem.vue";
import SearchResultItem2 from "@/components/common/header/SearchResultItem2.vue";

import "@/assets/css/home.css";
import "@/assets/css/style.css";

export default {
  data() {
    return {
      tag: this.$route.params.tag,
      state: false,
      accuracy: true,
      brandnew: false
    };
  },
  components: {
    Footer,
    Sidebar,
    Header,
    SearchResultItem,
    SearchResultItem2
  },
  computed: {
    results() {
      // console.log(this.$store.state.searchResult);
      return this.$store.state.searchResult;
    },
    results2() {
      // console.log(this.$store.state.searchResult);
      return this.$store.state.searchResult2;
    }
  },
  mounted() {
    // console.log(this.results);
    if (this.results.length !== 0) {
      this.state = true;
    }
  },
  methods: {
    // switchResult2() {
    //   this.result2 = !this.result2;
    // }
    accuracyActive() {
      this.accuracy = true;
      this.brandnew = false;
      const accuracyBtn = document.querySelector(".accuracy-btn");
      const brandnewBtn = document.querySelector(".brandnew-btn");
      if (this.accuracy === true) {
        accuracyBtn.style.background = "#373e6b";
        accuracyBtn.style.color = "#ffffff";
        brandnewBtn.style.background = "#ffffff";
        brandnewBtn.style.color = "#373e6b";
      }
    },
    brandnewActive() {
      this.accuracy = false;
      this.brandnew = true;
      const accuracyBtn = document.querySelector(".accuracy-btn");
      const brandnewBtn = document.querySelector(".brandnew-btn");
      if (this.brandnew === true) {
        accuracyBtn.style.background = "#ffffff";
        accuracyBtn.style.color = "#373e6b";
        brandnewBtn.style.background = "#373e6b";
        brandnewBtn.style.color = "#ffffff";
      }
    }
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
