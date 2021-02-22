<template>
  <v-container>
    <!--게시글이 들어갈 곳-->
    <v-sheet>
      <div class="container">
        <div class="row header">
          <h1>CONTACT US &nbsp;</h1>
          <h3>Fill out the form below to learn more!</h3>
        </div>
        <div class="row body">
          <form action="#">
            <ul>
              <li>
                <p class="left">
                  <label for="first_name">first name</label>
                  <input type="text" name="first_name" placeholder="John" />
                </p>
                <p class="pull-right">
                  <label for="last_name">last name</label>
                  <input type="text" name="last_name" placeholder="Smith" />
                </p>
              </li>

              <li>
                <p>
                  <label for="email">email <span class="req">*</span></label>
                  <input
                    type="email"
                    name="email"
                    placeholder="john.smith@gmail.com"
                  />
                </p>
              </li>
              <li><div class="divider"></div></li>
              <li>
                <label for="comments">comments</label>
                <textarea cols="46" rows="3" name="comments"></textarea>
              </li>

              <li>
                <input class="btn btn-submit" type="submit" value="Submit" />
                <small>or press <strong>enter</strong></small>
              </li>
            </ul>
          </form>
        </div>
      </div>
    </v-sheet>
  </v-container>
</template>

<script>
import Vue from "vue";
import VueRouter from "vue-router";
import { mapState } from "vuex";
import { axiosReadSub } from "@/api/article.js";

Vue.use(VueRouter);

export default {
  beforeCreate() {
    const subCategory = "buy";
    this.articles = axiosReadSub(
      subCategory,
      res => {
        this.articles = res.data;
        console.log(res.data);
      },
      err => {
        console.log(err);
      }
    );
  },
  data: () => ({
    articles: [],
    pageOfItems: [],
    category: "삽니다",
    locations: ["전체", "서울", "광주", "대전", "구미"],
    currentDate: "00:00:00",
    defaultImage: require("@/assets/images/SSAFYING_LOGO.png")
  }),
  computed: {
    ...mapState(["fields"])
  },
  methods: {
    // onChangePage(pageOfItems) {
    //   // update page of items
    //   this.pageOfItems = pageOfItems;
    // },
    writeArticle() {
      this.$router.push({
        name: "RegisterMarket",
        params: { mainCategory: "market", subCategory: "buy" }
      });
    },
    viewArticle(item) {
      this.$router.replace({
        name: "DetailMarket",
        params: {
          articleId: item.articleId
        }
      });
    }
  }
};
</script>

<style scoped>
@import "compass/css3";

// Font imports

@import url(https://fonts.googleapis.com/css?family=Lato:300, 400, 700);

// Color vars

$white: #fff;
$grey: #ccc;
$dark_grey: #555;
$blue: #4f6fad;
$pink: #ee9cb4;

// Mixins

@mixin lato-book {
  font-family: "Lato", sans-serif;
  font-weight: 300;
}
@mixin lato-reg {
  font-family: "Lato", sans-serif;
  font-weight: 400;
}
@mixin lato-bold {
  font-family: "Lato", sans-serif;
  font-weight: 700;
}
@mixin btn($color) {
  background-color: $color;
  border-bottom-color: darken($color, 15%);
  &:hover {
    background-color: lighten($color, 5%);
  }
}

// Functions

@function pxtoem($target, $context) {
  @return ($target/$context)+0em;
}

//

body {
  background-color: lighten($grey, 10%);
  font-size: 100%;
  @include lato-reg;
}
div,
textarea,
input {
  @include box-sizing(border-box);
}
.container {
  max-width: 510px;
  min-width: 324px;
  margin: 50px auto 0px;
  background-color: $white;
  border: 1px solid lighten($grey, 1%);
  border-bottom: 3px solid $grey;
}
.row {
  width: 100%;
  margin: 0 0 1em 0;
  padding: 0 2.5em;
  &.header {
    padding: 1.5em 2.5em;
    border-bottom: 1px solid $grey;
    background: url(https://images2.imgbox.com/a5/2e/m3lRbCCA_o.jpg) left -80px;
    color: $white;
  }
  &.body {
    padding: 0.5em 2.5em 1em;
  }
}
.pull-right {
  float: right;
}
h1 {
  @include lato-book;
  display: inline-block;
  font-weight: 100;
  font-size: pxtoem(45, 16);
  border-bottom: 1px solid hsla(100%, 100%, 100%, 0.3);
  margin: 0 0 0.1em 0;
  padding: 0 0 0.4em 0;
}
h3 {
  @include lato-reg;
  font-size: pxtoem(20, 16);
  margin: 1em 0 0.4em 0;
}
.btn {
  font-size: pxtoem(17, 16);
  display: inline-block;
  padding: 0.74em 1.5em;
  margin: 1.5em 0 0;
  color: $white;
  border-width: 0 0 0 0;
  border-bottom: 5px solid;
  text-transform: uppercase;
  @include btn(darken($grey, 10%));
  @include lato-book;
  &.btn-submit {
    @include btn($blue);
  }
}

form {
  max-width: 100%;
  display: block;
  ul {
    margin: 0;
    padding: 0;
    list-style: none;
    li {
      margin: 0 0 0.25em 0;
      clear: both;
      display: inline-block;
      width: 100%;
      &:last-child {
        margin: 0;
      }
      p {
        margin: 0;
        padding: 0;
        float: left;
        &.right {
          float: right;
        }
      }
      .divider {
        margin: 0.5em 0 0.5em 0;
        border: 0;
        height: 1px;
        width: 100%;
        display: block;
        background-color: $blue;
        background-image: linear-gradient(to right, $pink, $blue);
      }
      .req {
        color: $pink;
      }
    }
  }
  label {
    display: block;
    margin: 0 0 0.5em 0;
    color: $blue;
    font-size: pxtoem(16, 16);
  }
  input {
    margin: 0 0 0.5em 0;
    border: 1px solid $grey;
    padding: 6px 10px;
    color: $dark_grey;
    font-size: pxtoem(16, 16);
  }
  textarea {
    border: 1px solid $grey;
    padding: 6px 10px;
    width: 100%;
    color: $dark_grey;
  }
  small {
    color: $blue;
    margin: 0 0 0 0.5em;
  }
}

// Media Queries

@media only screen and (max-width: 480px) {
  .pull-right {
    float: none;
  }
  input {
    width: 100%;
  }
  label {
    width: 100%;
    display: inline-block;
    float: left;
    clear: both;
  }
  li,
  p {
    width: 100%;
  }
  input.btn {
    margin: 1.5em 0 0.5em;
  }
  h1 {
    font-size: pxtoem(36, 16);
  }
  h3 {
    font-size: pxtoem(18, 16);
  }
  li small {
    display: none;
  }
}
</style>
