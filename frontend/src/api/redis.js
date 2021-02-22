// /redis/main
import { createInstance } from "./index.js";

const instance = createInstance();

function axiosGetMain(success, fail) {
  instance.defaults.headers["access-token"] = window.localStorage.getItem(
    "access-token"
  );
  instance
    .get("/redis/main/")
    .then(success)
    .catch(fail);
}

export { axiosGetMain };
