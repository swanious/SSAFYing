<template>
  <div class="user__profile">
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon v-bind="attrs" v-on="on">
          <font-awesome-icon
            id="header__icon"
            class="profile__icon"
            icon="user-circle"
          />
        </v-btn>
      </template>

      <v-card class="">
        <v-list>
          <v-list-item>
            <v-row class="card__header">
              <v-col
                cols="3"
                style="margin:0; padding:0"
                class="card__profileimage"
              >
                <v-avatar color="#2d81ff" size="40">
                  <img
                    v-if="userInfo.profileImage"
                    :src="userInfo.profileImage"
                  />
                  <font-awesome-icon
                    v-else
                    id="header__icon"
                    class="profile__icon"
                    icon="user-circle"
                  />
                </v-avatar>
              </v-col>
              <v-col cols="9" class="card__profilename">
                <!-- <router-link
                  :to="{
                    name: 'Profile',
                    params: { nickname: userInfo.nickname }
                  }"
                  style="color:#343a40"
                > -->
                <v-list-item-content @click="goToProfilePage">
                  <v-list-item-title>{{ userInfo.nickname }}</v-list-item-title>
                  <v-list-item-subtitle>{{
                    userInfo.email
                  }}</v-list-item-subtitle>
                </v-list-item-content>
                <!-- </router-link> -->
              </v-col>
            </v-row>
          </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list>
          <v-list-item class="d-flex">
            <v-btn color="indigo" text>
              <v-list-item-title>
                <b-badge> {{ userInfo.grade }}기 </b-badge>
                {{ userInfo.username }}님 환영합니다.
              </v-list-item-title>
            </v-btn>
          </v-list-item>
          <!-- <v-list-item>
            <v-btn color="indigo" text>
              <v-list-item-title>
                내가 작성한 게시물
              </v-list-item-title>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn color="indigo" text>
              <v-list-item-title>
                환경 설정
              </v-list-item-title>
            </v-btn>
          </v-list-item> -->

          <!-- 테마 변경 -->
          <!-- <v-list-item class="mx-3">
            <v-list-item-action>
              <v-switch color="purple"></v-switch>
            </v-list-item-action>
            <v-list-item-title>다크모드</v-list-item-title>
          </v-list-item> -->

          <v-divider></v-divider>

          <v-list-item class="d-flex justify-content-end">
            <v-btn color="red lighten-1" text @click="onClickLogout">
              <v-list-item-title>
                로그아웃
              </v-list-item-title>
            </v-btn>
          </v-list-item>
        </v-list>
      </v-card>
    </v-menu>
  </div>
</template>

<script>
export default {
  name: "App",
  data: () => {
    return { userInfo: {} };
  },
  created() {
    // console.log(this.$store.state.userInfo);
    this.userInfo = this.$store.state.userInfo;
    // console.log(this.userInfo);
  },
  // computed: {
  //   userInfo() {
  //     // console.log(this.$store.state.userInfo);
  //     return this.$store.state.userInfo;
  //   }
  // },
  methods: {
    goToProfilePage() {
      this.$router.go(
        this.$router.push({
          name: "Profile",
          params: { nickname: this.userInfo.nickname }
        })
      );
    },
    onClickLogout() {
      this.$store
        .dispatch("logout")
        .then(() => this.$router.push({ name: "Login" }));
    }
  }
};
</script>

<style></style>
