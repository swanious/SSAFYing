<template>
  <div class=" board-outline-m main-outline">
    <!--게시판 카테고리-->
    <!-- <div class="main-section">
      <h5>
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-clipboard" viewBox="0 0 16 16">
          <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1v-1z"/>
          <path d="M9.5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3z"/>
        </svg>
        <b>게시판</b>
      </h5>
  </div> -->
    <!--새로운 요소 -->
    <v-row no-gutters="no-gutters">
      <!--1. 달력 -->
      <v-col cols="12" lg="6">
        <div class="card main-outline">
          <div class="main-card-contents">
            <h6 class="board-title"><v-icon>mdi-folder</v-icon> 달력</h6>
            <v-divider></v-divider>

            <v-divider></v-divider>
          </div>
        </div>
      </v-col>
      <!--2.시간-->
      <v-col cols="12" lg="6">
        <div class="card main-outline">
          <div class="main-card-contents">
            <h6 class="board-title"><v-icon>mdi-folder</v-icon> 시간</h6>
            <v-divider></v-divider>

            <v-divider></v-divider>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  components: {}
};
</script>

<style></style>
