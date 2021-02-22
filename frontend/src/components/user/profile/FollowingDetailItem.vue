<template>
  <div class="card p-3" @click="goToDetailPage()">
    <div class="d-flex justify-content-around align-items-center">
      <div class="d-flex align-items-center">
        <b-avatar></b-avatar>
        <div class="ml-1" @click="goToDetailPage">
          {{ user.username }}
        </div>
      </div>
      <div class="d-flex align-items-center">
        <div>
          {{ user.nickname }}
        </div>
        <b-badge>{{ user.grade }}기</b-badge>
      </div>
      <div>
        {{ user.email }}
      </div>
      <div>
        {{ user.birthday }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object
    }
  },
  created() {
    console.log(this.user);
  },
  methods: {
    goToDetailPage() {
      this.$router.go(
        this.$router.push({
          name: "Profile",
          params: { nickname: this.user.nickname }
        })
      );
    }
  }
};
</script>

<style scoped>
.trending {
  color: red;
}
</style>
