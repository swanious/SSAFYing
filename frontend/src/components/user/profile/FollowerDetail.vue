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
                  <!-- {{ followingUserDtoList }} -->
                  <FollowerDetailItem
                    v-for="(user, idx) in pageOfItems"
                    :key="idx"
                    :user="user"
                  />
                </div>
                <div class="d-flex justify-content-center">
                  <div class="card-footer pb-0 pt-3">
                    <jw-pagination
                      :pageSize="pageSize"
                      :items="followerUserDtoList"
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
import FollowerDetailItem from "@/components/user/profile/FollowerDetailItem";
export default {
  props: {
    followerUserDtoList: {
      type: Array
    }
  },
  components: {
    FollowerDetailItem
  },
  data() {
    return {
      pageSize: 5,
      pageOfItems: []
    };
  },
  methods: {
    onChangePage(pageOfItems) {
      // update page of items
      this.pageOfItems = pageOfItems;
    }
  }
};
</script>

<style></style>
