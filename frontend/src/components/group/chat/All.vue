<template>
  <v-container>
    <!-- 헤더 -->
    <div class="community__header">
      <h3><b>모든 채팅방</b></h3>
    </div>

    <!-- 컴포넌트 router -->
    <div class="community__links">
      <div class="btn__group">
        <v-btn id="active" to="/group/all">전체 챗</v-btn>
        <v-btn to="/group/free"> 자유 챗 </v-btn>
        <v-btn to="/group/anonymous">익명 챗 </v-btn>
        <v-btn to="/group/study">스터디 챗</v-btn>
      </div>
    </div>

    <!-- 사이드 바 -->
    <div class="community__sidebar">
      <Sidebar />
    </div>

    <div class="community__contents board">
      <ul style="padding:0" class="list-group">
        <li
          class="list-group-item list-group-item-action"
          v-for="(item, i) in chatrooms"
          :key="i"
        >
          <!-- 잠금방 -->
          <div
            class="d-flex justify-content-between align-items-center"
            v-if="item.isLock"
          >
            <div
              style="cursor:pointer"
              v-on:click="enterRoom(item.chatRoomId, item.name, item.type)"
            >
              <div>
                <span v-if="item.type === 'Study'" class="badge study">
                  {{ item.type }}
                </span>
                <span v-else-if="item.type === 'Free'" class="badge free">
                  {{ item.type }}
                </span>
              </div>

              <div class="room-title">
                <h5>
                  {{ item.name }}
                  <font-awesome-icon icon="lock" />
                </h5>
              </div>

              <div class="room-content">
                <p>{{ item.content }}</p>
              </div>
            </div>

            <div>
              <button
                class="btn"
                style="background: var(--color7); color:#ffffff; font-size:12px"
                @click="send(item.chatRoomId, item.name)"
              >
                가입 신청
              </button>
            </div>
          </div>

          <!-- 공개방 -->
          <div v-else>
            <div
              v-if="item.type === 'Anonymous'"
              v-b-modal="'anonymous-prevent-closing'"
              @click="sendInfo(item)"
            >
              <div class="room-badge">
                <span v-if="item.type === 'Study'" class="badge study">
                  {{ item.type }}
                </span>
                <span v-else-if="item.type === 'Free'" class="badge free">
                  {{ item.type }}
                </span>
                <span
                  v-else-if="item.type === 'Anonymous'"
                  class="badge anonymous"
                >
                  {{ item.type }}
                </span>
              </div>

              <div class="room-title">
                <h5>
                  {{ item.name }}
                </h5>
              </div>

              <div class="room-content">
                <p>
                  {{ item.content }}
                </p>
              </div>
            </div>

            <div
              v-else
              style="cursor:pointer"
              @click="enterRoom(item.chatRoomId, item.name, item.type)"
            >
              <div class="room-badge">
                <span v-if="item.type === 'Study'" class="badge study">
                  {{ item.type }}
                </span>
                <span v-else-if="item.type === 'Free'" class="badge free">
                  {{ item.type }}
                </span>
                <span
                  v-else-if="item.type === 'Anonymous'"
                  class="badge anonymous"
                >
                  {{ item.type }}
                </span>
              </div>

              <div class="room-title">
                <h5>
                  {{ item.name }}
                </h5>
              </div>

              <div class="room-content">
                <p>
                  {{ item.content }}
                </p>
              </div>
            </div>
          </div>
        </li>
      </ul>
      <!-- 닉네임 생성 -->
      <b-modal
        id="anonymous-prevent-closing"
        ref="modal"
        :title="selectedItem.name"
        hide-footer
      >
        <form
          ref="form"
          @submit.stop.prevent="
            enterRoom(
              selectedItem.chatRoomId,
              selectedItem.name,
              selectedItem.type
            )
          "
        >
          <b-form-group
            label="닉네임"
            label-for="name-input"
            invalid-feedback="채팅방 이름을 지어주세요!"
          >
            <b-form-input
              id="name-input"
              v-model="nickname"
              required
            ></b-form-input>
          </b-form-group>
          <hr />
          <div class="modal-btn">
            <button class="btn btn-create">입장하기</button>
          </div>
        </form>
      </b-modal>
      <!-- 닉네임 생성 끝 -->
    </div>
  </v-container>
</template>

<!-- JavaScript -->
<script>
import {
  axiosReadChatRoom,
  axiosCreateChatRoom,
  axiosEnterChatRoom
} from "@/api/chat.js";

import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import swal from "sweetalert";
import { SOCK_URL } from "@/config/index.js";

import Sidebar from "@/components/common/Sidebar.vue";

export default {
  name: "All",
  data: function() {
    return {
      chatroom: {
        name: "",
        content: "",
        isLock: false,
        nickname: this.$store.state.userInfo.nickname,
        hostId: this.$store.state.userInfo.userId
      },
      chatrooms: [],
      selectedItem: {},
      nickname: ""
    };
  },
  components: {
    // MarketList,
    Sidebar
  },
  created() {
    this.findAllRoom();
    this.connect();
  },
  methods: {
    findAllRoom: function() {
      axiosReadChatRoom(
        res => {
          this.chatrooms = res.data.chatRoomDtoList;
        },
        err => console.log(err)
      );
    },
    send(chatRoomId, name) {
      let enrollAlert = {
        userId: this.$store.state.userInfo.userId,
        nickname: this.$store.state.userInfo.nickname,
        chatRoomId: chatRoomId,
        name: name
      };
      this.stompClient.send(`/pub/enroll`, JSON.stringify(enrollAlert), {});
      swal({
        title: "승인 요청 완료",
        text: "방장에게 승인 메세지를 보냈어요!",
        icon: "info"
      });
      // alert("방장에게 가입 요청 메세지를 보냈어요. 조금만 기다려주세요 😉");
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
        },
        error => {
          console.log(error);
        }
      );
    },
    createRoom: function() {
      if ("" === this.chatroom.name) {
        alert("방 제목을 입력해 주십시요.");
      } else {
        this.chatroom.isLock = this.chatroom.isLock ? 1 : 0;
        console.log(this.chatroom);
        axiosCreateChatRoom(
          this.chatroom,
          response => {
            alert(response.data.name + "방 개설에 성공하였습니다.");

            this.chatroom.name = "";
            this.chatroom.content = "";

            this.findAllRoom();
          },
          () => alert("채팅방 개설에 실패하였습니다.")
        );
      }
    },
    enterRoom: function(chatRoomId, name, type) {
      let nickname = this.$store.state.userInfo.nickname;
      if (type === "Anonymous") {
        nickname = this.nickname;
        axiosEnterChatRoom(
          { name },
          () => {
            this.$router.push({
              path: `chatroom/${chatRoomId}/${name}/${nickname}`
            });
          },
          () => {
            alert("가입 승인이 필요합니다.");
          }
        );
      } else {
        let nickname = this.$store.state.userInfo.nickname;
        axiosEnterChatRoom(
          { name },
          () => {
            this.$router.push({
              path: `chatroom/${chatRoomId}/${name}/${nickname}`
            });
          },
          () => {
            alert("가입 승인이 필요합니다.");
          }
        );
      }
    },
    // 모달 영역에 클릭한 방의 데이터 넘겨주기
    sendInfo: function(item) {
      this.selectedItem = item;
    }
  }
};
</script>
<style scoped>
.badge.free {
  background: #d00000;
  color: #ffffff;
}
.badge.anonymous {
  background: #fff825cf;
  color: var(--color7);
}
.badge.study {
  background: #0ea400;
  color: #ffffff;
}
</style>
