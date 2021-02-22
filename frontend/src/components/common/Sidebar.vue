<template>
  <div>
    <Search />
    <div class="sidebar__image">
      <HomeImage />
    </div>

    <!-- section 1 -->
    <HotArticles />

    <!-- section 2 -->
    <!-- <div class="card">
      <div class="board">
        <div class="board__header">
          <p>코드 공유 게시판</p>
        </div>

        <v-divider></v-divider>

        <div class="board__contents">
          <a>
            <p>백준 1204번 문제 풀이 공유</p>
            <div class="like-message">
              <ul class="d-flex justify-content-end">
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'thumbs-up']"
                  />99
                </li>
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'comment-dots']"
                  />99
                </li>
              </ul>
            </div>
          </a>
        </div>
        <v-divider></v-divider>
        <div class="board__contents">
          <a>
            <p>SWEA 1243 문제 좀 도와주세요 ㅠㅠ</p>
            <div class="like-message">
              <ul class="d-flex justify-content-end">
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'thumbs-up']"
                  />99
                </li>
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'comment-dots']"
                  />99
                </li>
              </ul>
            </div>
          </a>
        </div>
      </div>
    </div> -->

    <!-- section 3 -->

    <Weather :weatherItem="weatherItem" />
    <!-- <div class="card">
      <div class="board">
        <div class="board__header">
          <p>HOT 게시물</p>
        </div>
        <v-divider></v-divider>
        <div class="board__contents">
          <a>
            <p>프론트/백 엔드 로드맵 따라잡기</p>
            <div class="like-message">
              <ul class="d-flex justify-content-end">
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'thumbs-up']"
                  />99
                </li>
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'comment-dots']"
                  />99
                </li>
              </ul>
            </div>
          </a>
        </div>
        <v-divider></v-divider>
        <div class="board__contents">
          <a>
            <p>알고리즘 스터디원 모집합니다. 빡세게 구르고 싶은신분 오세요~~</p>
            <div class="like-message">
              <ul class="d-flex justify-content-end">
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'thumbs-up']"
                  />99
                </li>
                <li class="mx-1">
                  <font-awesome-icon
                    class="mx-1"
                    :icon="['far', 'comment-dots']"
                  />99
                </li>
              </ul>
            </div>
          </a>
        </div>
      </div>
    </div> -->

    <!-- section 4 - 링크들 -->
    <div class="link">
      <b-button class="link__button">
        <a href="https://edu.ssafy.com/">
          <font color="white">EDU SSAFY 바로가기</font>
        </a>
      </b-button>
    </div>

    <div class="link">
      <b-button class="link__button">
        <a href="https://lab.ssafy.com/">
          <font color="white">LAB SSAFY 바로가기</font>
        </a>
      </b-button>
    </div>

    <div class="link d-flex justify-content-around my-5">
      <button>
        <a href="https://www.google.com/">
          <img src="@/assets/images/google_logo.png" alt="" />
        </a>
      </button>
      <button>
        <a href="https://github.com/">
          <img src="@/assets/images/GitHub-Mark.png" alt="" />
        </a>
      </button>
    </div>
  </div>
</template>

<script>
import Search from "@/components/common/Search.vue";
import HomeImage from "@/components/mainpage/home/HomeImage.vue";
import HotArticles from "@/components/mainpage/home/HotArticles.vue";
// import Weather from "@/components/mainpage/home/Weather";
export default {
  components: {
    Search,
    HomeImage,
    HotArticles,
    // Weather,
    Weather: () => import("@/components/mainpage/home/Weather")
  },
  data() {
    return {
      weatherItem: this.$store.state.sideBarItmes.weatherItem
    };
  }
};
</script>
<style></style>
