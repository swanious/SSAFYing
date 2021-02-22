<template>
  <v-main>
    <v-container>
      <div>
        <div>
          <h2>대기유저 관리 페이지</h2>
        </div>
        <div v-for="(user, index) in userList" :key="index" class="card">
          <div>
            <span>{{ user.username }}</span>
            <img :src="user.profileImage" style="width:300px" />
            <span>{{ user.nickname }}</span>
            <span>{{ user.grade }}</span>
            <button @click="acceptUser(user.userId)" class="btn btn-primary">
              승인
            </button>
            <button @click="denyUser(user.userId)" class="btn btn-danger">
              거절
            </button>
          </div>
        </div>
      </div>
    </v-container>
  </v-main>
</template>

<script>
import "@/assets/css/admin.css";
import {
  axiosReadUserAll,
  axiosUpdateUserAccept,
  axiosDeleteUserDeny
} from "@/api/admin.js";

export default {
  data() {
    return {
      userList: []
    };
  },
  created() {
    axiosReadUserAll(
      res => {
        this.userList = res.data.userDtoList.filter(ele => {
          return ele.role === null;
        });
      },
      err => console.log(err)
    );
  },
  methods: {
    acceptUser(userId) {
      console.log(userId);
      axiosUpdateUserAccept(
        userId,
        res => {
          console.log(res);
        },
        err => console.log(err)
      );
    },

    denyUser(userId) {
      console.log(userId);
      axiosDeleteUserDeny(
        userId,
        res => {
          console.log(res);
        },
        err => console.log(err)
      );
    }
  }
};
</script>

<style></style>
