<template>
  <div class="searchbox mb-1">
    <div class="container-1">
      <span class="icon"><font-awesome-icon class="mr-2" icon="search"/></span>
      <input
        type="search"
        id="search"
        placeholder="관심있는 키워드를 검색하세요!"
        v-model="userInput"
        @keypress.enter="searchInputData"
      />
    </div>
  </div>
</template>

<script>
import { axiosGetSearch } from "@/api/article.js";
import { axiosGetSearch2 } from "@/api/article.js";
import "@/assets/css/search.css";
export default {
  data() {
    return {
      userInput: ""
    };
  },
  methods: {
    async searchInputData() {
      // console.log(this.userInput);
      if (this.userInput.trim() !== "") {
        await axiosGetSearch(
          this.userInput,
          res => {
            // console.log(res.data);
            this.$store.state.searchResult = null;
            this.$store.state.searchResult = res.data;
          },
          err => {
            console.log(err);
          }
        );
        await axiosGetSearch2(
          this.userInput,
          res => {
            // console.log(res.data);
            this.$store.state.searchResult2 = null;
            this.$store.state.searchResult2 = res.data;
          },
          err => {
            console.log(err);
          }
        );
        // this.$router.push({
        //   name: "SearchResult",
        //   params: { tag: this.userInput }
        // });
        this.$router.push(`/search/${this.userInput}`);
        // this.$router.go(
        //   this.$router.push({
        //     name: "SearchResult",
        //     params: { tag: this.userInput }
        //   })
        // );
        this.userInput = "";
      } else {
        alert("검색어를 입력해주세요");
      }
    }
  }
};
</script>

<style></style>
