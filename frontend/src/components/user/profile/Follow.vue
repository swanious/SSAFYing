<template>
  <div class="d-flex justify-content-center mt-3">
    <b-button variant="outline-primary" v-show="!isFollow" @click="userFollow"
      >follow</b-button
    >
    <b-button v-show="isFollow" @click="userUnfollow">unfollow</b-button>
  </div>
</template>

<script>
import { axiosGetIsFollowInfo } from "@/api/user.js";
import { axiosPostFollow } from "@/api/user.js";
import { axiosDeleteFollow } from "@/api/user.js";

export default {
  created() {
    // console.log(this.$route.params.nickname);
    axiosGetIsFollowInfo(
      this.$route.params.nickname,
      res => {
        // console.log(res.data.IsFollow);
        if (res.data.IsFollow === 1) {
          this.isFollow = true;
        }
      },
      err => {
        console.log(err);
      }
    );
  },
  data() {
    return {
      isFollow: false
    };
  },
  methods: {
    userFollow() {
      axiosPostFollow(
        this.$route.params.nickname,
        res => {
          console.log(res);
          this.$emit;
          this.isFollow = true;
        },
        err => {
          console.log(err);
        }
      );
    },
    userUnfollow() {
      axiosDeleteFollow(
        this.$route.params.nickname,
        res => {
          console.log(res);
          this.isFollow = false;
        },
        err => {
          console.log(err);
        }
      );
    }
  }
};
</script>

<style></style>
