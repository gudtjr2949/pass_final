<template>
  <div class="notice-list">
    <div class="table-container">
      <router-link to="/notice/write">
        <button class="write-button">공지글 작성</button>
      </router-link>
      <table class="notice-table">
        <thead>
          <tr>
            <th>글 번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성 시간</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(chunk, index) in chunkedNotices" :key="index">
            <td v-for="notice in chunk" :key="notice.notice_id">
              {{ notice.notice_id }}
            </td>
            <td v-for="notice in chunk" :key="notice.notice_id">
              {{ notice.title }}
            </td>
            <td v-for="notice in chunk" :key="notice.notice_id">
              {{ notice.user_id }}
            </td>
            <td v-for="notice in chunk" :key="notice.notice_id">
              {{ notice.hit }}
            </td>
            <td v-for="notice in chunk" :key="notice.notice_id">
              {{ notice.register_time }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
  
<script>
import NoticeListItem from "./NoticeListItem.vue";
import { http } from "@/api/http";

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
    http.get("/notice/api/list").then((response) => {
      this.notice = response.data.list;
    });
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
  methods: {},
};
</script>

<style scoped>
.notice-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 100px;
  width: 800px;
  border: 1px solid black;
}

.table-container {
  width: 50px;
  left: 0;
  right: 0;
  /* margin-left: auto; */
  /* margin-right: auto; */
}

.write-button-container {
  /* position: absolute; */
  top: 0;
  right: 0;
  margin: 10px;
}

.notice-table th,
.notice-table td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
  text-align: center;
  height: 80px;
  border-radius: 4px;
}

.notice-table {
  width: auto;
  margin: 0 auto;
}

.notice-table th {
  background-color: #f2f2f2;
}

.notice-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.notice-table tbody tr:hover {
  background-color: #e6f7ff;
  cursor: pointer;
}

.write-button {
  padding: 10px 20px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 10px;
}

.table-container {
  width: 90%;
  position: relative;
}
</style>