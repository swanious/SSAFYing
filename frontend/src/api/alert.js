import { createWsInstance } from "./index.js";

const instance = createWsInstance();

function axiosReadEnrollAlert(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/alert/enroll/")
    .then(success)
    .catch(fail);
}

function axiosReadInviteAlert(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/alert/invite/")
    .then(success)
    .catch(fail);
}

function axiosUpdateEnrollAlert(alertItem, success, fail) {
  instance
    .put("/alert/enroll/", alertItem)
    .then(success)
    .catch(fail);
}

function axiosUpdateInviteAlert(alertItem, success, fail) {
  instance
    .put("/alert/invite/", alertItem)
    .then(success)
    .catch(fail);
}

function axiosDeleteEnrollAlert(alertItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/alert/enroll/", alertItem)
    .then(success)
    .catch(fail);
}

function axiosDeleteInviteAlert(alertItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/alert/invite/", alertItem)
    .then(success)
    .catch(fail);
}

export {
  axiosReadEnrollAlert,
  axiosDeleteEnrollAlert,
  axiosReadInviteAlert,
  axiosDeleteInviteAlert,
  axiosUpdateEnrollAlert,
  axiosUpdateInviteAlert
};
