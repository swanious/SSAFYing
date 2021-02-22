import Board from "@/components/board/Board.vue";

export default {
  name: "Board",
  path: "/board",
  component: Board,
  children: [
    // community - 게시판
    // 게시판 메인
    {
      path: "community",
      name: "All",
      component: () => import("@/components/board/community/All")
    },
    // 자유 게시판
    {
      path: "free",
      name: "Free",
      component: () => import("@/components/board/community/Free")
    },
    // 익명 게시판
    {
      path: "anonymous",
      name: "Anonymous",
      component: () => import("@/components/board/community/Anonymous")
    },
    {
      path: "grade",
      name: "Grade",
      component: () => import("@/components/board/community/Grade")
    },
    // market - 싸피장터
    // 게시판 메인
    {
      path: "market",
      name: "All",
      component: () => import("@/components/board/market/All.vue")
    },
    {
      path: "buy",
      name: "Buy",
      component: () => import("@/components/board/market/Buy")
    },
    {
      path: "sell",
      name: "Sell",
      component: () => import("@/components/board/market/Sell")
    },
    {
      path: "share",
      name: "Share",
      component: () => import("@/components/board/market/Share")
    },
    {
      path: "room",
      name: "Room",
      component: () => import("@/components/board/market/Room")
    },
    // information - 정보
    // 게시판 메인
    {
      path: "information",
      name: "InformationMain",
      component: () => import("@/components/board/information/All")
    },
    {
      path: "job",
      name: "Job",
      component: () => import("@/components/board/information/Job")
    },
    {
      path: "it",
      name: "IT",
      component: () => import("@/components/board/information/IT")
    },
    {
      path: "life",
      name: "Life",
      component: () => import("@/components/board/information/Life")
    },
    // 게시글 공통 수정, 삭제, 생성
    // {
    //   path: "register",
    //   name: "Register",
    //   component: () => import("@/components/board/Register")
    // },
    {
      path: ":subCategory/register",
      name: "Register",
      props: true,
      component: () => import("@/components/board/Register")
    },
    {
      path: "detail/:articleId",
      name: "Detail",
      component: () => import("@/components/board/Detail")
    },
    {
      path: "update/:articleId",
      name: "Update",
      component: () => import("@/components/board/Update")
    },
    {
      path: ":subCategory/register",
      name: "RegisterMarket",
      props: true,
      component: () => import("@/components/board/RegisterMarket3")
    },
    {
      path: "detail/:articleId",
      name: "DetailMarket",
      component: () => import("@/components/board/DetailMarket")
    },
    {
      path: "update/:articleId",
      name: "UpdateMarket",
      component: () => import("@/components/board/UpdateMarket")
    },
    {
      path: "anonymous/:articleId",
      name: "DetailAnonymous",
      component: () => import("@/components/board/DetailAnonymous")
    }
  ]
};
