import axios from "axios";
import { API_BASE_URL, API_WS_URL } from "../config";

function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      "access-token" : window.localStorage.getItem("access-token")
    }
  });
  return instance;
}

function createWsInstance() {
  const instance = axios.create({
    baseURL: API_WS_URL,
    headers: {
      "Content-Type": "application/json",
      "access-token": window.localStorage.getItem("access-token")
    }
  });
  return instance;
}

export { createInstance, createWsInstance };
