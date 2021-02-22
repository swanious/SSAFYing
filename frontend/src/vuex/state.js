export default {
  fields: [
    // { key: "commentNum", label: "댓글", tdClass: "tdClass" },
    // { key: "userId", label: "작성자", tdClass: "tdClass" },
    { key: "title", label: "제목", tdClass: "tdSubject" },
    { key: "content", label: "내용", tdClass: "tdSubject" },
    { key: "subCategory", label: "분류", tdClass: "tdClass" },
    { key: "watchCount", label: "조회수", tdClass: "tdClass" },
    { key: "createdAt", label: "작성일", tdClass: "tdClass" }
  ],
  // token: window.localStorage.getItem('access-token'),
  userInfo: null,
  isLogin: false,
  sideBarItmes: {},
  searchResult: {},
  searchResult2: {}
};
