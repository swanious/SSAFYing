export default {
  WRITE_ARTICLE(state, articleItem) {
    state.articles.push(articleItem);
  },
  DELETE_ARTICLE(state, articleItem) {
    const idx = state.articles.indexOf(articleItem);
    state.articles.splice(idx, 1);
  },
  UPDATE_ARTICLE(state, articleItem) {
    state.articles = state.articles.map(article => {
      if (article === articleItem) {
        return articleItem;
      }
      return article;
    });
  },
  CREATE_COMMENT(state, commentItem) {
    state.comments.push(commentItem);
  },
  DELETE_COMMENT(state, commentItem) {
    const idx = state.comments.indexOf(commentItem);
    state.comments.splice(idx, 1);
  },
  UPDATE_COMMENT(state, commentItem) {
    state.comments = state.comments.map(comment => {
      if (comment.articleid === commentItem.articleid) {
        return commentItem;
      }
      return comment;
    });
  },
  SET_IS_LOGINED(state, isLogin) {
    state.isLogin = isLogin;
  },
  SET_USER_INFO(state, userInfo) {
    // console.log(userInfo);
    state.userInfo = userInfo;
  },
  LOGOUT(state) {
    state.isLogin = false;
    state.userInfo = null;
    window.localStorage.removeItem("access-token");
  },
  GET_SIDEBAR_DATA(state, sideBarItmes) {
    state.sideBarItmes = sideBarItmes;
  }
};
