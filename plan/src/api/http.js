import axios from "axios";

const baseURL = "http://192.168.208.54:80"

const http = axios.create({
    baseURL: `${baseURL}`,
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    },
})


export { baseURL, http };