import { createInstance } from "./index.js";

const instance = createInstance();

function nicknameDuplicateCheck(nickname, success, fail) {
  instance
    .post("/user/duplicate", {nickname})
    .then(success)
    .catch(fail);
}

function axiosSignup(user, success, fail) {
  instance
    .post("user/", JSON.stringify(user))
    .then(success)
    .catch(fail);
}
function axiosLogin(loginInfo, success, fail) {
  instance
    .post("jwt/authenticate", JSON.stringify(loginInfo))
    .then(success)
    .catch(fail);
}

async function findById(token, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance.defaults.headers["access-token"] = token;
  await instance
    .get(`/user/`)
    .then(success)
    .catch(fail);
}

async function axiosGetUserInfo(userId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .get(`/user/${userId}`)
    .then(success)
    .catch(fail);
}

async function axiosPutUserInfo(userInfo, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .put(`/user/`, JSON.stringify(userInfo))
    .then(success)
    .catch(fail);
}

async function axiosGetIsFollowInfo(nickname, success, fail) {
  // /user/follow/{following_nickname}
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .get(`/user/follow/${nickname}`)
    .then(success)
    .catch(fail);
}

async function axiosPostFollow(nickname, success, fail) {
  // /user/follow/{following_nickname}
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .post(`/user/follow/${nickname}`)
    .then(success)
    .catch(fail);
}

async function axiosDeleteFollow(nickname, success, fail) {
  // /user/follow/{following_nickname}
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .delete(`/user/follow/${nickname}`)
    .then(success)
    .catch(fail);
}

async function axiosPutPassword(passwordInfo, success, fail) {
  // /user/follow/{following_nickname}
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  await instance
    .put(`/user/password/`, JSON.stringify(passwordInfo))
    .then(success)
    .catch(fail);
}

function axiosReadFollow(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/user/`)
    .then(success)
    .catch(fail);
}

function axiosPostEmailVerify(emailInfo, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post(`/user/email`, JSON.stringify(emailInfo))
    .then(success)
    .catch(fail);
}

export {
  axiosPostEmailVerify,
  nicknameDuplicateCheck,
  axiosSignup,
  axiosLogin,
  findById,
  axiosGetUserInfo,
  axiosGetIsFollowInfo,
  axiosPostFollow,
  axiosDeleteFollow,
  axiosPutPassword,
  axiosReadFollow,
  axiosPutUserInfo
};
