<template>
  <v-container>
    <v-sheet>
      <!--게시글이 들어갈 곳-->
      <div class="">
        <!-- 헤더 -->
        <div>
          <h3><b>게시판</b></h3>
        </div>
      </div>
    </v-sheet>
  </v-container>
</template>
<!-- 서브 카테고리 선택버튼 -->
<!-- <div>
          <v-chip-group mandatory active-class="primary--text">
            <v-chip
              v-for="(board, index) in boards"
              :key="index"
              @click="changeComponent(`${board[1]}`)"
            >
              {{ board[0] }}
            </v-chip>
          </v-chip-group>
        </div> -->

<!-- 컴포넌트 갈아끼우기 -->
<!-- <div>
          <keep-alive>
          <component v-bind:is="compName"></component>
          </keep-alive>
        </div> -->

<script>
import { axiosReadMain } from "@/api/article.js";
import "@/assets/css/style.css";

// 라우터 경로로 변환한다면 더이상 쓸필요 X
// import All from "@/components/board/community/All.vue";
// import Free from "@/components/board/community/Free.vue";
// import Anonymous from "@/components/board/community/Anonymous.vue";
// import Promotion from "@/components/board/community/Promotion.vue";

export default {
  created() {
    const mainCategory = "community";
    this.articles = axiosReadMain(
      mainCategory,
      res => {
        this.articles = res.data;
      },
      err => {
        console.log(err);
      }
    );
  },
  data: () => ({
    articles: [],
    compName: "All",
    boards: [
      ["전체", "All"],
      ["자유게시판", "Free"],
      ["익명게시판", "Anonymous"],
      ["홍보게시판", "Promotion"]
    ]
  }),
  components: {
    // All,
    // Free,
    // Anonymous,
    // Promotion
  },
  computed: {},
  methods: {
    changeComponent: function(componentName) {
      this.compName = componentName;
    }
  }
};
</script>

<style></style>
