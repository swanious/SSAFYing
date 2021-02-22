<template>
  <v-app-bar app color="blue" class="elevation-3">
    <div class="admin__logo">
      <router-link to="/admin">
        Admin Page
      </router-link>
    </div>
    <div class="admin__menus">
      <ul>
        <li v-for="(title, i) in titles" :key="i">
          <router-link
            :to="links[i]"
            :title="titles[i]"
            role="link"
            :aria-label="titles[i]"
          >
            <span>
              {{ titles[i] }}
            </span>
          </router-link>
        </li>
      </ul>
    </div>
    <div class="admin__logout">
      <button class="btn btn-danger">로그아웃</button>
    </div>
  </v-app-bar>
</template>

<script>
export default {
  data: () => {
    return {
      links: [
        "/admin/standby",
        "/admin/user",
        "/admin/article",
        "/admin/comment",
        "/admin/group"
      ],
      titles: ["대기유저", "유저", "게시물", "댓글", "그룹"]
    };
  }
};
</script>

<style></style>
