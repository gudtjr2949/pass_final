<template>
  <div class="following-list">
    <h3>Following List</h3>
    <!-- <ul> -->
    <div
      v-for="following in followingList"
      :key="following.user_id"
      class="following-item"
    >
      <div class="following-info">
        <strong>아이디:</strong> {{ following.user_id }} -
        <strong>이름:</strong> {{ following.user_name }}
      </div>
    </div>
    <!-- </ul> -->
  </div>
</template>
  
<script>
// import axios from "axios";
import { http } from "@/api/http";

export default {
  data() {
    return {
      followingList: [],
    };
  },
  created() {
    const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;

    http.get(`/user/api/following_list/${userId}`).then((res) => {
      this.followingList = res.data.list;
      console.log(this.followingList);
    });
  },
};
</script>
  
<style scoped>
.following-list {
  margin-block: 100px;
  margin-inline: 400px;
  text-align: center;
}

.following-item {
  margin-bottom: 10px;
}

.following-info {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
}

.following-info strong {
  margin-right: 5px;
}
</style>
  