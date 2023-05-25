<template>
  <div class="notice-list-container">
    <h1 class="underline">공지사항</h1>
    <div class="add-button-container">
      <router-link to="/notice/write" v-if="isAdmin">
        <button class="add-button">글 등록</button>
      </router-link>
    </div>
    <div v-if="notices.length">
      <table id="notice-list">
        <colgroup>
          <col style="width: 5%" />
          <col style="width: 65%" />
          <col style="width: 10%" />
          <col style="width: 5%" />
          <col style="width: 15%" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <notice-list-item
            v-for="notice in notices"
            v-bind:key="notice.notice_id"
            v-bind:notice="notice"
          ></notice-list-item>
        </tbody>
      </table>
    </div>
    <div v-else>
      <div class="text-center">게시글이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import { http } from "@/api/http";
import NoticeListItem from "./NoticeListItem.vue";
import axios from "axios";

export default {
  name: "NoticeList",
  components: {
    NoticeListItem,
  },
  data() {
    return {
      notices: [],
      isAdmin: "",
    };
  },
  created() {
    // 비동기
    // TODO : 글목록 얻기.
    http.get("/notice/api/list").then((response) => {
      this.notices = response.data.list;
    });

    axios
      .get(
        "http://172.30.1.58:80/user/api/checkAdmin/" +
          JSON.parse(sessionStorage.getItem("userInfo")).user_id
      )
      .then((response) => {
        if (response.data.check) {
          this.isAdmin = true;
        } else {
          this.isAdmin = false;
        }
      });
  },
  methods: {
    movePage() {},
  },
};
</script>

<style scoped>
.notice-list-container {
  margin-bottom: 20px;
  margin: 200px;
}

.underline {
  text-decoration: underline;
}

.add-button-container {
  text-align: right;
  margin-bottom: 10px;
}

.add-button {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-button:hover {
  background-color: #1e88e5;
}

#notice-list {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

#notice-list th {
  border-bottom: 1px solid #cccccc;

  padding: 15px;
}
#notice-list td {
  border-bottom: 1px solid #cccccc;
  text-align: center;
}
</style>