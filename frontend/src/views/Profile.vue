<template>
  <v-app id="inspire">
    <Header />
    <!-- section 1 시작 -->
    <v-main>
      <div class="profile__section1">
        <v-container>
          <v-row>
            <v-col cols="1"></v-col>
            <!-- 프로필 헤더 시작 -->
            <v-col class="profile__wrap">
              <v-list class="profile__header">
                <v-list-item class="profile__image">
                  <!-- 내 프로필이면, 이미지 변경 가능 -->
                  <div v-if="isYourself">
                    <a v-b-modal.profile-imageupload>
                      <v-avatar color="#2d81ff" size="100">
                        <img
                          v-if="userInfo.profileImage"
                          :src="userInfo.profileImage"
                        />
                        <font-awesome-icon
                          v-else
                          icon="user-circle"
                          size="5x"
                          style="color:#fff"
                        />
                      </v-avatar>
                    </a>
                    <b-modal
                      id="profile-imageupload"
                      title="프로필 이미지 변경"
                      hide-footer
                    >
                      <b-form-group
                        id="fieldset-9"
                        label="이미지 변경"
                        label-for="input-9"
                      >
                        <b-form-file
                          id="input-9"
                          v-model="newProfileImage"
                          trim
                        ></b-form-file>
                        <span class="text-danger"></span>
                      </b-form-group>
                      <v-divider></v-divider>

                      <button
                        @click="imageUpdate"
                        style="text-align:center"
                        class="btn btn-primary"
                      >
                        변경
                      </button>
                    </b-modal>
                  </div>

                  <!-- 상대이면 이미지 변경 불가능 -->
                  <v-avatar v-else color="#2d81ff" size="100">
                    <v-icon dark>
                      mdi-account-circle
                    </v-icon>
                  </v-avatar>
                </v-list-item>
                <v-list-item class="profile__username">
                  <h2>
                    {{ userInfo.nickname }}
                  </h2>
                  <b-badge class="ml-1" variant="primary"
                    >{{ userInfo.grade }} 기
                  </b-badge>
                </v-list-item>
                <v-list-item class="profile__intro">
                  <span>
                    {{ userInfo.email }}
                  </span>
                </v-list-item>
              </v-list>

              <div class="d-flex justify-content-center">
                <div class="mx-2">
                  게시물 <b>{{ articles.length }}</b>
                </div>
                <div class="mx-2">
                  팔로워 <b>{{ followerCount }}</b>
                </div>
                <div class="mx-2">
                  팔로잉 <b>{{ followingCount }}</b>
                </div>
              </div>
              <!-- 프로필 헤더 끝 -->

              <!-- follow button start-->
              <Follow v-if="!isYourself" />
              <!-- follow button end-->
            </v-col>
            <!-- 프로필 끝 -->
            <v-col cols="1"></v-col>
          </v-row>
        </v-container>
      </div>
      <!-- section1 끝 -->

      <!-- section 2 시작 -->
      <div class="profile__section2">
        <v-container>
          <v-row>
            <v-col cols="1"></v-col>
            <v-col class="contents--padding">
              <!-- 해당 유저 컨텐츠 링크 -->
              <div class="profile__contents">
                <ul class="contents1">
                  <li>
                    <v-btn
                      elevation=""
                      class="white"
                      @click="
                        {
                          currentComponent = 'WroteArticle';
                        }
                      "
                    >
                      게시물
                    </v-btn>
                  </li>
                  <li>
                    <v-btn
                      elevation=""
                      class="white"
                      @click="
                        {
                          currentComponent = 'WroteComment';
                        }
                      "
                    >
                      댓글
                    </v-btn>
                  </li>
                  <li>
                    <v-btn
                      elevation=""
                      class="white"
                      @click="
                        {
                          currentComponent = 'FollowerDetail';
                        }
                      "
                    >
                      나의 팔로워
                    </v-btn>
                  </li>
                  <li>
                    <v-btn
                      elevation=""
                      class="white"
                      @click="
                        {
                          currentComponent = 'FollowingDetail';
                        }
                      "
                    >
                      내가 팔로잉
                    </v-btn>
                  </li>
                </ul>
                <ul class="contents2">
                  <li v-if="isYourself">
                    <router-link
                      :to="{
                        name: 'ChangeUserInfo',
                        params: this.$route.params.nickname
                      }"
                    >
                      <v-btn elevation="">
                        <font-awesome-icon icon="edit" />프로필 수정
                      </v-btn>
                    </router-link>
                  </li>
                </ul>
              </div>
            </v-col>
            <v-col cols="1"></v-col>
          </v-row>
        </v-container>
      </div>
      <!-- section2 끝 -->

      <!-- section3은 컨텐츠별로 components를 따로 파서할 예정 -->
      <keep-alive>
        <component
          :is="currentComponent"
          v-model="articles"
          :followingUserDtoList="followingUserDtoList"
          :followerUserDtoList="followerUserDtoList"
        />
      </keep-alive>
      <!-- <WroteArticle v-model="articles" />
      <WroteComment /> -->
      <!-- section3 끝 -->
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import WroteArticle from "@/components/user/profile/WroteArticle.vue";
import WroteComment from "@/components/user/profile/WroteComment.vue";
import FollowerDetail from "@/components/user/profile/FollowerDetail";
import FollowingDetail from "@/components/user/profile/FollowingDetail";
import Follow from "@/components/user/profile/Follow";
import { axiosGetUserInfo, axiosPutUserInfo } from "@/api/user.js";

import swal from "sweetalert";

// import Friend from "@/components/user/profile/Friend.vue";
// import Storage from "@/components/user/profile/Storage.vue";

export default {
  components: {
    Header,
    WroteArticle,
    WroteComment,
    // Friend,
    // Storage,
    Footer,
    Follow,
    FollowingDetail,
    FollowerDetail
  },
  watch: {
    newProfileImage() {
      // console.log(newVal, oldVal);
      const reader = new FileReader();
      reader.onload = e => {
        this.userInfo.profileImage = e.target.result;
      };
      reader.readAsDataURL(this.newProfileImage);
    }
  },
  data: () => {
    return {
      model: 0,
      colors: ["primary", "secondary", "yellow darken-2", "red", "orange"],
      userInfo: {},
      isYourself: false,
      isFollow: false,
      followerCount: 0,
      followingCount: 0,
      articles: [],
      newProfileImage: null,
      followingUserDtoList: [],
      followerUserDtoList: [],
      currentComponent: "WroteArticle"
    };
  },
  created() {
    axiosGetUserInfo(
      this.$route.params.nickname,
      response => {
        this.followerCount = response.data.followerCount;
        this.followingCount = response.data.followingCount;
        this.userInfo = response.data.userDto;
        this.followingUserDtoList = response.data.followingUserDtoList;
        this.followerUserDtoList = response.data.followerUserDtoList;
      },
      error => {
        console.log(error);
      }
    );

    if (this.$route.params.nickname === this.$store.state.userInfo.nickname) {
      this.isYourself = true;
    }
  },
  methods: {
    imageUpdate() {
      axiosPutUserInfo(
        this.userInfo,
        () => {
          this.$router.push(`/profile/${this.userInfo.nickname}`);
          swal({
            title: "변경 완료",
            text: "사진 변경에 성공했어요 !",
            icon: "success"
          });
        },
        () => {
          swal({
            title: "변경 실패",
            text: "사진 변경에 실패했어요 !",
            icon: "error"
          });
        }
      );
    }
  }
};
</script>
<style scoped>
@import "../assets/css/profile.css";
</style>
