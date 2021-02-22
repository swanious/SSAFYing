<template>
  <div class="card p-3" @click="viewArticle()">
    <div class="d-flex justify-content-between">
      <div class="d-flex align-items-center">
        <div class="mr-2">
          <b-badge>{{ article.articleDto.score }}%</b-badge>
        </div>
        <div
          :class="{
            title: article,
            trending: article.articleDto.watchCount > 10 ? true : false
          }"
          v-html="emphasisTitle"
        >
          <font-awesome-icon
            v-if="article.articleDto.watchCount > 10"
            class="mx-1"
            :icon="['fas', 'fire-alt']"
          />
          <!-- {{ article.articleDto.title | truncate(30) }} -->
        </div>
      </div>
      <div class="d-flex">
        <!-- <font-awesome-icon
          class="profile__icon"
          icon="user-circle"
          :style="{ color: '#3e3e3e' }"
        /> -->
        <!-- <div class="ml-2">
          <router-link
            :to="{
              name: 'Profile',
              params: { nickname: article.nickname }
            }"
            style="color: #636DA6"
            >{{ article.nickname }}</router-link
          >
        </div> -->
      </div>
    </div>

    <div class="content">
      <span v-html="emphasisContent">
        <!-- {{
        article.articleDto.content | truncate(100)
      }} -->
      </span>
    </div>

    <div class="d-flex justify-content-end">
      <ul>
        <li class="mx-1">
          <span>{{
            article.articleDto.createdAt | moment("from", "now")
          }}</span>
        </li>
      </ul>
    </div>
    <div class="d-flex flex-direction-row justify-content-end">
      <ul class="status d-flex justify-content-end">
        <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'thumbs-up']" />{{
            article.articleLike
          }}
        </li>
        <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'comment-dots']" />{{
            article.articleCommentCount
          }}
        </li>
        <li class="mx-1">
          <font-awesome-icon class="mx-1" :icon="['far', 'eye']" />
          {{ article.articleDto.watchCount }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    result: {
      type: Object
    }
  },
  data() {
    return {
      emphasisTitle: "",
      emphasisContent: "",
      article: {}
    };
  },
  methods: {
    viewArticle() {
      this.$router.push({
        name: "Detail",
        params: { articleId: this.article.articleDto.articleId }
      });
    }
  },
  created() {
    this.article = this.result;

    this.emphasisTitle = this.article.articleDto.title.replace(
      this.$route.params.tag,
      `<span style="color:#636DA6;font-weight:bold;font-size:1.5rem;">${this.$route.params.tag}</span>`
    );
    this.emphasisContent = this.article.articleDto.content
      .replace(
        this.$route.params.tag,
        `<span style="color:#636DA6;font-weight:bold;font-size:1.2rem;">${this.$route.params.tag}</span>`
      )
      .slice(0, 150);
    this.emphasisContent += "........";
  },
  watch: {
    result(val) {
      this.article = val;

      this.emphasisTitle = this.article.articleDto.title.replace(
        this.$route.params.tag,
        `<span style="color:#636DA6;font-weight:bold;font-size:1.5rem;">${this.$route.params.tag}</span>`
      );
      this.emphasisContent = this.article.articleDto.content
        .replace(
          this.$route.params.tag,
          `<span style="color:#636DA6;font-weight:bold;font-size:1.2rem;">${this.$route.params.tag}</span>`
        )
        .slice(0, 150);
      this.emphasisContent += "........";

      console.log("WATCH!!!");
    }
  }
};
</script>

<style></style>
