<template>
  <div class="user__notification">
    <v-menu
      :close-on-content-click="false"
      offset-y
      :max-width="450"
      :min-width="300"
      fixed
    >
      <!-- 우측 상단 버튼 -->
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon v-bind="attrs" v-on="on">
          <font-awesome-icon
            id="header__icon"
            class="alert__icon"
            icon="bell"
          />
        </v-btn>
      </template>

      <!-- 알림 드롭다운 -->
      <v-card>
        <v-list class="wrap">
          <v-list-item class="header" style="padding:0">
            <a
              class="notification-btn active"
              @click="changeComponent('AlertNotification')"
            >
              <span>알림</span>
            </a>
            <a class="request-btn" @click="changeComponent('AlertRequest')">
              <span>요청</span>
            </a>
          </v-list-item>
        </v-list>
        <v-list>
          <v-list-item>
            <keep-alive>
              <component
                :enrollAlertList="enrollAlertList"
                :inviteAlertList="inviteAlertList"
                v-bind:is="compName"
              ></component>
            </keep-alive>
          </v-list-item>

          <!-- 컴포넌트 갈아끼기 -->
          <div></div>
        </v-list>
      </v-card>
    </v-menu>
  </div>
</template>

<script>
import AlertNotification from "@/components/common/header/alert/AlertNotification.vue";
import AlertRequest from "@/components/common/header/alert/AlertRequest.vue";

import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { SOCK_URL } from "@/config/index.js";

import { axiosReadEnrollAlert, axiosReadInviteAlert } from "@/api/alert.js";

export default {
  data: () => {
    return {
      alertList: [],
      compName: "AlertNotification",
      isActive: true,
      enrollAlertList: [],
      inviteAlertList: []
    };
  },
  components: {
    AlertNotification,
    AlertRequest
  },
  created() {
    axiosReadEnrollAlert(
      res => {
        this.enrollAlertList = res.data.enrollAlertDtoList;
      },
      err => console.log(err)
    );
    axiosReadInviteAlert(
      res => {
        this.inviteAlertList = res.data.inviteAlertDtoList;
      },
      err => console.log(err)
    );
    this.connect();
  },
  methods: {
    connect() {
      var options = {
        debug: false
      };
      let sock = new SockJS(SOCK_URL);
      this.stompClient = Stomp.over(sock, options);
      this.stompClient.connect(
        {},
        () => {
          // console.log("소켓 연결 성공", frame);
          this.stompClient.subscribe(
            `/sub/enroll/${this.$store.state.userInfo.userId}`,
            frame => {
              this.enrollAlertList.push(JSON.parse(frame.body));
            },
            error => console.log(error)
          );
          this.stompClient.subscribe(
            `/sub/invite/${this.$store.state.userInfo.userId}`,
            frame => {
              this.inviteAlertList.push(JSON.parse(frame.body));
            },
            error => console.log(error)
          );
        },
        error => {
          console.log(error);
        }
      );
    },
    changeComponent: function(componentName) {
      this.compName = componentName;

      const notificationBtn = document.querySelector(".notification-btn");
      const requestBtn = document.querySelector(".request-btn");
      if (componentName === "AlertNotification") {
        requestBtn.classList.remove("active");
        notificationBtn.classList.add("active");
      } else {
        notificationBtn.classList.remove("active");
        requestBtn.classList.add("active");
      }
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&family=Roboto&display=swap");
* {
  font-family: "Noto Sans KR", sans-serif !important;
}

.wrap {
  display: flex;
  justify-content: space-around;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  font-weight: bold;
  padding: 0;
}

.header {
  padding: 0;
}
.header a {
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  justify-content: center;
  align-items: center;
  color: var(--color1);
}

.header a.active {
  border-bottom: 1px solid var(--color7);
}

.header a::after {
  content: "";
  position: absolute;
  left: 50%;
  bottom: -1px;
  width: 0;
  height: 1px;
  background: var(--color7);
}

.header a:hover::after {
  width: 100%;
  transition: all 0.5s;
  left: 0;
}
</style>
