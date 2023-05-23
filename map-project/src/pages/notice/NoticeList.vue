<template>
    <div>
        <div v-for="(chunk, index) in chunkedNotices" :key="index">
            <notice-list-item v-for="notice in chunk" :key="notice.notice_id" :notice="notice"></notice-list-item>
        </div>

        <router-link to="/notice/write">
            <button class="write-button">글 작성</button>
        </router-link>

    </div>
</template>  
  
<script>
import NoticeListItem from "./NoticeListItem.vue";
import axios from "axios";

export default {
    name: "NoticeList",
    components: {
        NoticeListItem,
    },
    data() {
        return {
            user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
            notice: [],
        };
    },
    created() {
        axios.get("http://localhost:80/notice/api/list").then((response) => {
            this.notice = response.data.list;
        })
    },
    computed: {
        chunkedNotices() {
            // Chunk the notices array into smaller arrays, if desired
            const chunkSize = 1; // Set the desired chunk size here
            const chunkedArray = [];

            for (let i = 0; i < this.notice.length; i += chunkSize) {
                const chunk = this.notice.slice(i, i + chunkSize);
                chunkedArray.push(chunk);
            }

            return chunkedArray;
        },
    },
    methods: {
    },

};
</script>
  
<style scoped>
.write-button {
    position: fixed;
    top: 20px;
    right: 20px;
    padding: 10px 20px;
    background-color: #4caf50;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
</style>