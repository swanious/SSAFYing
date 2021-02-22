import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./vuex/store";
import vuetify from "./plugins/vuetify";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import {
  ValidationObserver,
  ValidationProvider,
  extend,
  localize
} from "vee-validate";
import en from "vee-validate/dist/locale/en.json";
import * as rules from "vee-validate/dist/rules";
import "./vee-validate";

// Import FontAwesomeIcon
import "../fontAwesomeIcon.js";

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// pagination
import JwPagination from "jw-vue-pagination";
Vue.component("jw-pagination", JwPagination);

// vue datetime librart
import vueMoment from "vue-moment";
Vue.use(vueMoment);

// lodash
import VueLodash from "vue-lodash";
import lodash from "lodash";
// name is optiona
Vue.use(VueLodash, { name: "custom", lodash: lodash });

// vue truncate library
var VueTruncate = require("vue-truncate-filter");
Vue.use(VueTruncate);

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);

import VueChatScroll from "vue-chat-scroll";
Vue.use(VueChatScroll);

// install rules and localization
Object.keys(rules).forEach(rule => {
  extend(rule, rules[rule]);
});

localize("en", en);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  async beforeCreate() {
    let token = localStorage.getItem("access-token");
    if (store.state.userInfo == null) {
      await store.dispatch("getMemberInfo", token);
    }
  },
  render: h => h(App)
}).$mount("#app");
