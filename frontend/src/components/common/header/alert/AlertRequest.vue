<template>
  <div>
    <div v-for="(request, index) in alertRequestList" :key="index">
      <span style="font-size:12px" class="mr-1">
        {{ request.nickname }}님이 {{ request.name }}방에 가입요청을 하셨습니다.
      </span>
      <button
        style="background:var(--color7); color:#fff; font-size:14px; height:30px; width:40px; margin-right:1px"
        @click="deleteRequest(request, 1, index)"
      >
        승인
      </button>
      <button
        style="background:crimson; color:#fff; font-size:14px; height:30px; width:40px;"
        @click="deleteRequest(request, 0, index)"
      >
        거절
      </button>
    </div>
  </div>
</template>

<script>
import { axiosUpdateEnrollAlert, axiosDeleteEnrollAlert } from "@/api/alert.js";

export default {
  data() {
    return {
      alertRequestList: []
    };
  },
  props: {
    enrollAlertList: Array
  },
  created() {
    this.alertRequestList = this.enrollAlertList;
    this.alertRequestList.forEach(ele => {
      if (ele.isRead === 0) {
        this.updateRequest(ele);
      }
    });
  },
  methods: {
    updateRequest(request) {
      axiosUpdateEnrollAlert(
        request,
        () => {},
        err => console.log(err)
      );
    },
    deleteRequest(request, isAccept, index) {
      request["isAccept"] = isAccept;
      axiosDeleteEnrollAlert(
        request,
        () => {},
        err => console.log(err)
      );
      this.alertRequestList.splice(index, 1);
    }
  }
};
</script>

<style></style>
