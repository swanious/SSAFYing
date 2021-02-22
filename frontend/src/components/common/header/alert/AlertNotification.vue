<template>
  <div>
    <div v-for="(invite, index) in alertInviteList" :key="index">
      <span class="mr-1" style="font-size:12px">
        {{ invite.nickname }}님이 {{ invite.name }}방에 초대하셨습니다.
      </span>
      <button
        style="background:var(--color7); color:#fff; font-size:14px; height:30px; width:40px; margin-right:3px"
        @click="deleteRequest(invite, 1, index)"
      >
        승인
      </button>
      <button
        style="background:crimson; color:#fff; font-size:14px; height:30px; width:40px;"
        @click="deleteRequest(invite, 0, index)"
      >
        거절
      </button>
    </div>
  </div>
</template>

<script>
import { axiosUpdateInviteAlert, axiosDeleteInviteAlert } from "@/api/alert.js";

export default {
  data() {
    return {
      alertInviteList: []
    };
  },
  props: {
    inviteAlertList: Array
  },
  created() {
    this.alertInviteList = this.inviteAlertList;
    this.alertInviteList.forEach(ele => {
      if (ele.isRead === 0) {
        this.updateInvite(ele);
        ele.isRead = 1;
      }
    });
  },
  methods: {
    updateInvite(invite) {
      axiosUpdateInviteAlert(
        invite,
        () => {},
        err => console.log(err)
      );
    },
    deleteRequest(invite, isAccept, index) {
      invite["isAccept"] = isAccept;
      axiosDeleteInviteAlert(
        invite,
        () => {},
        err => console.log(err)
      );
      this.alertInviteList.splice(index, 1);
    }
  }
};
</script>

<style></style>
