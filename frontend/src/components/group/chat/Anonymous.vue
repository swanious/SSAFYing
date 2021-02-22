<template>
  <v-container>
    <!-- 헤더 -->
    <div class="community__header">
      <h3><b>익명 채팅방</b></h3>
    </div>
    <!-- 컴포넌트 router -->
    <div class="community__links">
      <div class="btn__group">
        <v-btn to="/group/all">전체 챗</v-btn>
        <v-btn to="/group/free"> 자유 챗 </v-btn>
        <v-btn id="active" to="/group/anonymous">익명 챗 </v-btn>
        <v-btn to="/group/study">스터디 챗</v-btn>
      </div>
      <b-button class="create__btn1" v-b-modal.modal-prevent-closing
        ><font-awesome-icon icon="quote-left" size="xs" />
        <span class="mx-1" style="font-size:14px">방 만들기</span>
      </b-button>
      <b-button class="create__btn2" v-b-modal.modal-prevent-closing size="lg"
        ><font-awesome-icon icon="pencil-alt" />
      </b-button>
    </div>
    <div>
      <!-- 채팅방 생성 -->
      <b-modal
        id="modal-prevent-closing"
        ref="modal"
        title="오픈 싸핑방"
        hide-footer
      >
        <form ref="form" @submit.stop.prevent="createRoom">
          <b-form-group
            label="채팅방 이름"
            label-for="name-input"
            invalid-feedback="채팅방 이름을 지어주세요!"
          >
            <b-form-input
              id="name-input"
              v-model="chatroom.name"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="소개"
            label-for="textarea"
            invalid-feedback="채팅방을 소개해주세요!"
          >
            <b-form-textarea
              id="textarea"
              v-model="chatroom.content"
              placeholder="간략한 채팅방 소개(200자 이내)"
              rows="3"
              max-rows="6"
              style="overflow-y:hidden"
            ></b-form-textarea>
          </b-form-group>
          <hr />
          <div class="modal-btn">
            <button class="btn btn-create">생성</button>
          </div>
        </form>
      </b-modal>
      <!-- 채팅방 생성 끝 -->

      <!-- 채팅방 리스트 시작-->

      <ul class="list-group" style="padding:0">
        <li
          class="list-group-item list-group-item-action"
          v-for="(item, i) in chatrooms"
          :key="i"
          v-b-modal="'anonymous-prevent-closing'"
          @click="sendInfo(item)"
        >
          <div class="room-title">
            <h5>
              {{ item.name }}
            </h5>
          </div>
          <div class="room-content">
            <p>{{ item.content }}</p>
          </div>
        </li>
      </ul>
      <!-- 채팅방 리스트 끝 -->

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
            enterRoom(selectedItem.chatRoomId, selectedItem.name, nickname)
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
            <button class="btn btn-create">생성</button>
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
  axiosReadChatRoomOfType,
  axiosCreateChatRoom,
  axiosEnterChatRoom
} from "@/api/chat.js";

import swal from "sweetalert";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { SOCK_URL } from "@/config/index.js";

export default {
  name: "Anonymous",
  data: function() {
    return {
      chatroom: {
        name: "",
        content: "",
        isLock: false,
        type: "Anonymous",
        nickname: "",
        hostId: this.$store.state.userInfo.userId
      },
      chatrooms: [],
      selectedItem: {},
      nickname: ""
    };
  },
  created() {
    this.findAllRoom();
    this.connect();
  },
  methods: {
    findAllRoom: function() {
      axiosReadChatRoomOfType(
        "Anonymous",
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
          () => {
            swal({
              title: "채팅방 생성 완료",
              text: "방 개설에 성공하였습니다.",
              icon: "success"
            });

            this.chatroom.name = "";
            this.chatroom.content = "";

            this.findAllRoom();

            // 버튼 클릭 후 modal창 끄기
            this.$nextTick(() => {
              this.$bvModal.hide("modal-prevent-closing");
            });
          },
          () => {
            swal({
              title: "채팅방 생성 실패",
              text: "방 개설에 실패하였습니다.",
              icon: "error"
            });
          }
        );
      }
    },
    enterRoom: function(chatRoomId, name, nickname) {
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
    },
    // 모달 영역에 클릭한 방의 데이터 넘겨주기
    sendInfo: function(item) {
      this.selectedItem = item;
    }
  }
};
</script>
