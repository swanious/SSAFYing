import { createWsInstance } from "./index.js";

const instance = createWsInstance();

function axiosReadChatRoom(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/chatRoom/")
    .then(success)
    .catch(fail);
}

function axiosReadChatRoomOfType(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/chatRoom/${chatRoomItem}`)
    .then(success)
    .catch(fail);
}

function axiosReadChatRoomInfo(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/chatRoom/info/${chatRoomItem}`)
    .then(success)
    .catch(fail);
}

function axiosCreateChatRoom(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/chatRoom/", chatRoomItem)
    .then(success)
    .catch(fail);
}

function axiosEnterChatRoom(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/chatRoom/enroll/login", chatRoomItem)
    .then(success)
    .catch(fail);
}

function axiosReadChat(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/chat/${chatRoomItem}`)
    .then(success)
    .catch(fail);
}

function axiosReadAllChat(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/chat/all/${chatRoomItem}`)
    .then(success)
    .catch(fail);
}

function axiosDeleteChatRoom(chatRoomItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .delete(`/chatRoom/enroll/${chatRoomItem.chatRoomId}`)
    .then(success)
    .catch(fail);
}

export {
  axiosReadChat,
  axiosReadAllChat,
  axiosReadChatRoom,
  axiosReadChatRoomOfType,
  axiosReadChatRoomInfo,
  axiosCreateChatRoom,
  axiosEnterChatRoom,
  axiosDeleteChatRoom
};
