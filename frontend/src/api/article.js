import { createInstance } from "./index.js";

const instance = createInstance();

function axiosCreateArticle(articleItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post("/article/", JSON.stringify(articleItem))
    .then(success)
    .catch(fail);
}

function axiosReadAllArticle(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/article/")
    .then(success)
    .catch(fail);
}

// 메인 카테고리 Read
function axiosReadMain(mainCategory, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/main/${mainCategory}/`)
    .then(success)
    .catch(fail);
}

// 서브 카테고리 Read
function axiosReadSub(subCategory, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/sub/${subCategory}/`)
    .then(success)
    .catch(fail);
}

function axiosReadOneArticle(articleId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/info/${articleId}/`)
    .then(success)
    .catch(fail);
}

function axiosUpdateArticle(articleItem, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .put(`/article/`, JSON.stringify(articleItem))
    .then(success)
    .catch(fail);
}

function axiosDeleteArticle(articleId, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .delete(`/article/${articleId}/`)
    .then(success)
    .catch(fail);
}

// /article/nick/{nickname}
function axiosGetWroteArticles(nickname, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/nick/${nickname}/`)
    .then(success)
    .catch(fail);
}

// /article/like/
function axiosPostArticleLike(articleInfo, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .post(`/article/like/`, JSON.stringify(articleInfo))
    .then(success)
    .catch(fail);
}

// /article/like/{articleId}/{userId}
function axiosDeleteArticleLike(articleInfo, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .delete(`/article/like/${articleInfo.articleId}/${articleInfo.userId}/`)
    .then(success)
    .catch(fail);
}

function axiosGetSearch(tag, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/search/${tag}/`)
    .then(success)
    .catch(fail);
}

function axiosGetSearch2(tag, success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get(`/article/search2/${tag}/`)
    .then(success)
    .catch(fail);
}
export {
  axiosGetSearch2,
  axiosCreateArticle,
  axiosReadAllArticle,
  axiosReadOneArticle,
  axiosReadMain,
  axiosReadSub,
  axiosUpdateArticle,
  axiosDeleteArticle,
  axiosGetWroteArticles,
  axiosPostArticleLike,
  axiosDeleteArticleLike,
  axiosGetSearch
};
