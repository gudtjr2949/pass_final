import axios from "axios";

// const baseURL = "http://192.168.208.54:80"
// const baseURL = "http://192.168.208.64:80"
// const baseURL = "http://172.30.1.49:8080"
// const baseURL = "http://localhost/"
const baseURL = "http://172.30.1.58:80"

const http = axios.create({
    baseURL: `${baseURL}`,
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    },
})


export { baseURL, http };