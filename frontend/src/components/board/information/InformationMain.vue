<template>
  <v-container>
    <!--게시글이 들어갈 곳-->
    <v-sheet>
      <div class="">
        <!-- 헤더 -->
        <div>
          <h3><b>정보</b></h3>
        </div>

        <!-- 서브 카테고리 선택버튼 -->
        <div>
          <v-chip-group mandatory active-class="primary--text">
            <v-chip
              v-for="(board, index) in boards"
              :key="index"
              @click="changeComponent(`${board[1]}`)"
            >
              {{ board[0] }}
            </v-chip>
          </v-chip-group>
        </div>

        <!-- 컴포넌트 갈아끼우기 -->
        <div>
          <!-- keep-alive 태그는 모든 컴포넌트를 랜더링하도록 유지한다. -->
          <!-- <keep-alive> -->
          <component v-bind:is="compName"></component>
          <!-- </keep-alive> -->
        </div>
      </div>
    </v-sheet>
  </v-container>
</template>

<script>
import { axiosReadMain } from "@/api/article.js";
import "@/assets/css/style.css";

import All from "@/components/board/information/All.vue";
import IT from "@/components/board/information/IT.vue";
import Job from "@/components/board/information/Job.vue";

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
      ["IT 정보", "IT"],
      ["취업 정보", "Job"]
    ]
  }),
  components: {
    All,
    IT,
    Job
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
