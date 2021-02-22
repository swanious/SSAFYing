<template>
  <div class="m-3">
    <!--카테고리-->
    <div class="mb-5">
      <h5>
        <b>싸피장터</b>
      </h5>
    </div>
    <!--싸피장터 목록-->
    <MarketArticle />
  </div>
</template>

<script>
import MarketArticle from "@/components/mainpage/articles/MarketArticle.vue";
export default {
  components: {
    MarketArticle
  }
};
</script>

<style></style>
