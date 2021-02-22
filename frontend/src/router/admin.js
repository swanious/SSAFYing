import Admin from "../views/Admin.vue";

export default {
  name: "Admin",
  path: "/admin",
  component: Admin,
  children: [
    {
      path: "standby",
      name: "Standby",
      component: () => import("@/components/admin/Standby.vue")
    },
    {
      path: "user",
      name: "User",
      component: () => import("@/components/admin/User.vue")
    },
    {
      path: "article",
      name: "Article",
      component: () => import("@/components/admin/Article.vue")
    },
    {
      path: "comment",
      name: "Comment",
      component: () => import("@/components/admin/Comment.vue")
    },
    {
      path: "group",
      name: "Group",
      component: () => import("@/components/admin/Group.vue")
    }
  ]
};
