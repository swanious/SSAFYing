<template>
  <a class="board-list" href="#">
    <div class="main-article-title">
      <p>자유 게시판 글 제목 1</p>
      <p>작성자</p>
      <p>시간</p>
    </div>
    <!-- <div>
      <b-table outlined bordered head-variant:light hover :articles="articles" :fields="fields"></b-table>
    </div> -->
  </a>

  <!-- <v-divider></v-divider> -->
</template>

<script>
export default {
  data: () => {
    return {
      fields: ["분류", "제목", "작성자", "작성시간"],
      articles: [
        {
          대분류: "게시판",
          분류: "자유게시판",
          제목: "오늘은 몇시에 잘 수 있을까?",
          작성자: "LynnLee",
          작성시간: "1시간 전"
        }
      ]
    };
  }
};
</script>

<style></style>
