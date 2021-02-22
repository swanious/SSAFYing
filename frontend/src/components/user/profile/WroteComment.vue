<template>
  <div class="profile__section3">
    <v-container>
      <v-row>
        <v-col cols="1"></v-col>
        <v-col>
          <v-row>
            <v-col class="mt-3 elevation-2">
              <v-sheet rounded="" class="grey lighten-4" height="">
                <div class="board">
                  <WroteCommentItem
                    v-for="(comment, idx) in pageOfItems"
                    :key="idx"
                    :comment="comment"
                  />
                </div>
                <div class="d-flex justify-content-center">
                  <div class="card-footer pb-0 pt-3">
                    <jw-pagination
                      :pageSize="pageSize"
                      :items="comments"
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
import { axiosGetWroteComments } from "@/api/comment.js";
import WroteCommentItem from "@/components/user/profile/WroteCommentItem";
import "@/assets/css/style.css";
import "@/assets/css/profile.css";

export default {
  components: {
    WroteCommentItem
  },

  data() {
    return {
      comments: [],
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
    axiosGetWroteComments(
      this.$route.params.nickname,
      res => {
        // console.log(res.data);
        this.comments = res.data;
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
