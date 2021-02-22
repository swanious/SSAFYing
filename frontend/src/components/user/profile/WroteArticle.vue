<template>
  <div class="profile__section3">
    <v-container>
      <v-row>
        <v-col cols="1"></v-col>
        <v-col>
          <v-row>
            <!-- <v-col cols="3" class="mt-3 elevation-2"> hi</v-col> -->
            <v-col class="mt-3 elevation-2">
              <v-sheet rounded="" class="grey lighten-4" height="">
                <div class="board">
                  <WroteArticleItem
                    v-for="(article, idx) in pageOfItems"
                    :key="idx"
                    :article="article"
                  />
                </div>
                <div class="d-flex justify-content-center">
                  <div class="card-footer pb-0 pt-3">
                    <jw-pagination
                      :pageSize="pageSize"
                      :items="articles"
                      @changePage="onChangePage"
                    ></jw-pagination>
                  </div>
                </div>
              </v-sheet>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { axiosGetWroteArticles } from "@/api/article.js";
import WroteArticleItem from "@/components/user/profile/WroteArticleItem";
import "@/assets/css/style.css";
import "@/assets/css/profile.css";

export default {
  components: {
    WroteArticleItem
  },
  watch: {
    articles(newValue) {
      this.$emit("input", newValue);
    }
  },
  data() {
    return {
      articles: [],
      pageSize: 5,
      pageOfItems: []
    };
  },
  methods: {
    onChangePage(pageOfItems) {
      // update page of items
      this.pageOfItems = pageOfItems;
    }
  },
  created() {
    axiosGetWroteArticles(
      this.$route.params.nickname,
      res => {
        // console.log(res.data);
        this.articles = res.data;
        // console.log(this.articles);
      },
      err => {
        console.log(err);
      }
    );
  }
};
</script>

<style></style>
