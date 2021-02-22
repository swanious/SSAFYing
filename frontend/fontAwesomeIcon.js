import Vue from "vue";
// 설치했던 fontawesome-svg-core와 vue-fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

// 설치했던 아이콘파일에서 해당 아이콘만 불러옵니다.
import {
  faSearch,
  faHome,
  faUsers,
  faBullhorn,
  faChalkboard,
  faBars,
  faShoppingCart,
  faEdit,
  faEllipsisH,
  faChevronLeft,
  faBell,
  faUserCircle,
  faDoorOpen,
  faLock,
  faPencilAlt,
  faQuoteLeft,
  faQuoteRight,
  faTimesCircle
} from "@fortawesome/free-solid-svg-icons";
import {
  faThumbsUp as fasThumbsUp,
  faCommentDots as fasCommentDots,
  faEye as fasEye,
  faFireAlt as fasFireAlt
} from "@fortawesome/free-solid-svg-icons";

import {
  faThumbsUp as farThumbsUp,
  faCommentDots as farCommentDots,
  faEye as farEye
} from "@fortawesome/free-regular-svg-icons";

// 불러온 아이콘을 라이브러리에 담습니다.
library.add(
  fasFireAlt,
  fasEye,
  farEye,
  fasCommentDots,
  farCommentDots,
  fasThumbsUp,
  farThumbsUp,
  faEdit,
  faHome,
  faUsers,
  faBullhorn,
  faChalkboard,
  faBars,
  faShoppingCart,
  faEllipsisH,
  faSearch,
  faChevronLeft,
  faBell,
  faUserCircle,
  faDoorOpen,
  faLock,
  faPencilAlt,
  faQuoteLeft,
  faQuoteRight,
  faTimesCircle
);

// fontawesome아이콘을 Vue탬플릿에 사용할 수 있게 등록해 줍니다.
Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false;
