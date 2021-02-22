// import jwt_decode from "jwt-decode";

export default {
  // getUserId: state => {
  //   return jwt_decode(state.token).userId;
  // },

  getBoardArticles: state => subCategory => {
    return state.articles
      .filter(article => {
        if (article.subCategory === subCategory) {
          return true;
        }
        return false;
      })
      .map(article => {
        return article;
      });
  },
  getArticle: state => title => {
    // console.log(title);
    // console.log(state.articles.find(article => article.titie === title));
    return state.articles.find(article => article.title === title);
  },
  getComments: state => title => {
    return state.comments.map(comment => {
      // console.log(comment);
      // console.log(title);
      if (comment.articleid === title) {
        return comment;
      }
    });
  }
};
