<template>
  <v-container>
    <!-- 헤더 -->
    <div v-if="roomType === 'Anonymous'">
      <div class="container d-flex justify-content-between align-items-end">
        <div>
          {{ roomType }}
          <h3 style="font-weight:bold">{{ chat.name }}</h3>
        </div>
        <div>
          <router-link :to="{ path: `/group/all` }">
            <span style="font-size:15px"
              ><font-awesome-icon
                class="mr-3"
                icon="chevron-left"
              />뒤로가기</span
            >
          </router-link>
        </div>
      </div>
    </div>
    <div v-else>
      <v-row no-gutters>
        <v-col cols="12" md="9">
          <v-row no-gutters>
            <div
              class="container d-flex justify-content-between align-items-end"
            >
              <div>
                {{ roomType }}
                <h3 style="font-weight:bold">{{ chat.name }}</h3>
              </div>
              <div>
                <router-link :to="{ path: `/group/all` }">
                  <span style="font-size:15px"
                    ><font-awesome-icon
                      class="mr-3"
                      icon="chevron-left"
                    />뒤로가기</span
                  >
                </router-link>
              </div>
            </div>
          </v-row>
        </v-col>

        <v-col cols="3"> </v-col>
      </v-row>
    </div>

    <!-- 채팅 컨테이너 -->
    <!-- 익명방 -->
    <div v-if="roomType === 'Anonymous'" style="width:100%">
      <v-row>
        <v-col>
          <!-- 채팅 -->
          <ul class="messages" v-chat-scroll>
            <li v-for="(item, i) in chatList" :key="i">
              <div v-if="item.nickname === chat.nickname" class="message-box">
                <div class="d-flex justify-content-end align-items-end">
                  <span style="font-size:12px; margin-right:5px">
                    {{ item.createdAt | moment("from", "now") }}
                  </span>
                  <div class="message">
                    {{ item.content }}
                  </div>
                </div>
              </div>
              <div class="message-box" v-else>
                <div style="font-size:12px">
                  {{ item.nickname }}
                </div>
                <div class="d-flex align-items-end">
                  <div class="message">
                    {{ item.content }}
                  </div>
                  <span style="font-size:12px; margin-left:5px">
                    {{ item.createdAt | moment("from", "now") }}
                  </span>
                </div>
              </div>
            </li>
          </ul>
          <!-- 입력 -->
          <div class="input-group mt-3">
            <input
              type="text"
              class="form-control "
              v-model="chat.content"
              v-on:keypress.enter="chatSend"
            />
            <div class="input-group-append">
              <button
                class="btn"
                style="background:var(--color7); color:#fff"
                type="button"
                @click="chatSend"
              >
                보내기
              </button>
            </div>
          </div>
        </v-col>
      </v-row>
    </div>
    <!-- 자유/스터디 방 -->
    <div v-else style="width:100%">
      <v-row>
        <v-col cols="12" md="9">
          <!-- 채팅 -->
          <ul
            @scroll="scrollCheck"
            class="messages"
            id="scrollEvent"
            v-chat-scroll
          >
            <li v-for="(item, i) in chatList" :key="i">
              <div v-if="item.nickname === chat.nickname" class="message-box">
                <div class="d-flex justify-content-end align-items-end">
                  <span style="font-size:12px; margin-right:5px">
                    {{ item.createdAt | moment("from", "now") }}
                  </span>
                  <div class="message">
                    {{ item.content }}
                  </div>
                </div>
              </div>
              <div class="message-box" v-else>
                <div style="font-size:12px">
                  {{ item.nickname }}
                </div>
                <div class="d-flex align-items-end">
                  <div class="message">
                    {{ item.content }}
                  </div>
                  <span style="font-size:12px; margin-left:5px">
                    {{ item.createdAt | moment("from", "now") }}
                  </span>
                </div>
              </div>
            </li>
          </ul>
          <!-- 입력 -->
          <div class="input-group mt-3">
            <input
              type="text"
              class="form-control "
              v-model="chat.content"
              v-on:keypress.enter="chatSend"
            />
            <div class="input-group-append">
              <button
                class="btn"
                style="background:var(--color7); color:#fff"
                type="button"
                @click="chatSend"
              >
                보내기
              </button>
            </div>
          </div>
        </v-col>
        <v-col cols="3">
          <!-- 친구 목록 시작 -->
          <div class="friend-list">
            <div>
              <h5>내 친구</h5>
            </div>

            <v-divider></v-divider>

            <div
              class="d-flex justify-content-between align-items-center mb-3"
              style="margin-bottom:3px; border-bottom:1px solid rgba(55,62,107,0.3)"
              v-for="(follow, i) in followInfo"
              :key="i"
            >
              <div>
                <span class="mr-3" style="font-size:12px"
                  >{{ follow.grade }} 기</span
                >
                <button>{{ follow.nickname }}</button>
              </div>
              <div>
                <button
                  @click="inviteSend(follow.nickname)"
                  class="btn"
                  style="background: var(--color7); color:#fff"
                >
                  초대
                </button>
              </div>
            </div>
          </div>
          <!-- 친구 목록 끝  -->

          <!-- 채팅방 나가기 시작 -->
          <div class="chatExit">
            <button v-b-modal.chat-exit class="btn">
              <span style="color:red; font-size:14px">
                채팅방 나가기
                <font-awesome-icon icon="times-circle" />
              </span>
            </button>
          </div>
          <!-- 채팅방 나가기 끝 -->

          <!-- 탈퇴 모달 시작 -->
          <div>
            <b-modal id="chat-exit" title="채팅방 탈퇴" hide-footer>
              <p class="my-4">
                채팅방을 나가면 재가입 승인 후 들어올 수 있어요.
              </p>
              <p>그래도 나가시겠어요?</p>

              <v-divider></v-divider>

              <button
                style="text-align:center"
                class="btn btn-danger"
                @click="deleteChatRoom"
              >
                나가기
              </button>
            </b-modal>
          </div>
          <!-- 탈퇴 모달 끝 -->
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { SOCK_URL } from "@/config/index.js";

import {
  axiosReadChat,
  axiosDeleteChatRoom,
  axiosReadChatRoomInfo,
  axiosReadAllChat
} from "@/api/chat.js";
import { axiosReadFollow } from "@/api/user.js";

// let reconnect = 0;

export default {
  data: function() {
    return {
      stompClient: null,
      // 소켓에는 헤더가 없어 jwt를 사용할 수 없으므로, userId를 직접 보내주어야 한다.
      chat: {
        chatRoomId: this.$route.params.chatRoomId,
        name: this.$route.params.name,
        content: "",
        userId: this.$store.state.userInfo.userId,
        nickname: this.$route.params.nickname
      },
      chatList: [],
      followInfo: [],
      roomType: "",
      chatCountAtFirst: 0,
      oldUlScrollHeight: 0,
      newUlScrollHeight: 0,
      flag: false,
      flag2: false
    };
  },
  components: {},
  created() {
    axiosReadChatRoomInfo(
      this.chat.chatRoomId,
      res => {
        this.roomType = res.data.chatRoomDto.type;
      },
      err => console.log(err)
    );
    this.connect();

    axiosReadChat(
      this.chat.chatRoomId,
      res => {
        this.chatList = res.data.chatDtoList;
        this.chatCountAtFirst = this.chatList.length;
      },
      err => console.log(err)
    );

    axiosReadFollow(
      res => {
        this.followInfo = res.data.followerUserDtoList;
      },
      err => console.log(err)
    );
  },
  methods: {
    chatSend() {
      if (this.chat.content.length === 0) return;
      else {
        this.chat["createdAt"] = new Date();
        this.stompClient.send(`/pub/chat`, JSON.stringify(this.chat), {});
        this.chat.content = "";
      }
    },
    scrollCheck() {
      const ulScrollTop = document.querySelector("#scrollEvent").scrollTop;

      if (this.oldUlScrollHeight < ulScrollTop && this.flag === false) {
        this.oldUlScrollHeight = ulScrollTop;
      }

      if (this.flag === true && this.flag2 === false) {
        this.newUlScrollHeight = ulScrollTop;
        document.querySelector("#scrollEvent").scrollTop =
          this.newUlScrollHeight - this.oldUlScrollHeight;

        this.flag2 = true;
      }

      if (
        ulScrollTop === 0 &&
        this.flag === false &&
        this.chatCountAtFirst >= 100
      ) {
        axiosReadAllChat(
          this.chat.chatRoomId,
          res => {
            this.chatList = res.data.chatDtoList;
            this.flag = true;
          },
          err => console.log(err)
        );
      }
    },
    inviteSend(userNickname) {
      const inviteAlertParams = {
        hostId: this.$store.state.userInfo.userId,
        nickname: this.chat.nickname,
        chatRoomId: this.chat.chatRoomId,
        name: this.chat.name,
        userNickname: userNickname
      };
      this.stompClient.send(
        `/pub/invite`,
        JSON.stringify(inviteAlertParams),
        {}
      );
    },

    deleteChatRoom() {
      axiosDeleteChatRoom(
        { chatRoomId: this.chat.chatRoomId },
        () => {
          this.$router.push(`/group/all`);
        },
        () => this.$router.push(`/group/all`)
      );
    },

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
            `/sub/chatRoom/${this.chat.chatRoomId}`,
            frame => {
              this.chatList.push(JSON.parse(frame.body));
            },
            error => console.log(error)
          );
        },
        error => {
          console.log(error);
        }
      );
    }
  }
};
</script>
<style>
.friend-list {
  height: 70vh;
  min-width: 200px;
}

/* 채팅방 탈퇴 모달 */
#chat-exit___BV_modal_header_ {
  background: var(--color7);
}

#chat-exit___BV_modal_content_ {
  font-family: "Noto Sans", "Roboto", sans-serif;
}

#chat-exit___BV_modal_title_ {
  font-weight: bold;
  color: #fff;
}
/*  */

ul.messages {
  width: 100%;
  height: 70vh;
  border: 1px solid rgba(0, 0, 0, 0.3);
  padding: 10px !important;
  border-radius: 5px;
  overflow-y: auto;
}
/* 스크롤 바 */
.messages::-webkit-scrollbar {
  width: 3px;
}
.messages::-webkit-scrollbar-thumb {
  background-color: var(--color7);
}
.messages::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px white;
}

.message-box {
  margin: 3px 10px;
}

.my-message {
  display: inline-block;
  background: var(--color1);
  padding: 10px;
  border-radius: 10px;
  color: #fff;
}

.message {
  display: inline-block;
  background: var(--color1);
  padding: 10px;
  border-radius: 10px;
  color: #fff;
}
.chatExit {
  position: fixed;
  right: 1rem;
  top: 80px;
}
</style>
