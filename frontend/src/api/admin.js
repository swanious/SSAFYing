import { createInstance } from "./index.js";

const instance = createInstance();

function axiosReadUserAll(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/user/admin")
    .then(success)
    .catch(fail);
}

function axiosUpdateUserAccept(userId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .put('/user/admin', { userId })
    .then(success)
    .catch(fail);
}

function axiosDeleteUserDeny(userId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
  .delete(`/user/admin/${userId}`)
  .then(success)
    .catch(fail);
}

export { axiosReadUserAll, axiosUpdateUserAccept, axiosDeleteUserDeny };