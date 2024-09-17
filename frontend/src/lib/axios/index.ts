import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

export default axiosClient;
