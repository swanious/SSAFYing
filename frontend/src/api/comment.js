import { createInstance } from "./index";

const instance = createInstance();

function axiosCreateComment(commentItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/comment/", JSON.stringify(commentItem))
    .then(success)
    .catch(fail);
}

function axiosReadComment(articleId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/comment/${articleId}`)
    .then(success)
    .catch(fail);
}

function axiosUpdateComment(commentItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .put(`/comment/`, JSON.stringify(commentItem))
    .then(success)
    .catch(fail);
}

function axiosDeleteComment(commentItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .delete(`/comment/${commentItem.commentId}`)
    .then(success)
    .catch(fail);
}

// /comment/nick/{nickname}
function axiosGetWroteComments(nickname, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/comment/nick/${nickname}/`)
    .then(success)
    .catch(fail);
}

export {
  axiosCreateComment,
  axiosReadComment,
  axiosUpdateComment,
  axiosDeleteComment,
  axiosGetWroteComments
};
