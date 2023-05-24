<template>
  <div>
    <h3>Follower List</h3>
    <!-- <ul> -->
    <div
      v-for="follower in followerList"
      :key="follower.user_id"
      class="follower-item"
    >
      <div class="follower-info">
        <strong>아이디:</strong> {{ follower.user_id }} -
        <strong>이름:</strong> {{ follower.user_name }}
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
      followerList: [],
    };
  },
  created() {
    const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;

    http.get(`/user/api/follower_list/${userId}`).then((res) => {
      this.followerList = res.data.list;
      console.log(this.followerList);
    });
  },
};
</script>
  
<style scoped>
.follower-item {
  margin-bottom: 10px;
}

.follower-info {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
}

.follower-info strong {
  margin-right: 5px;
}
</style>
  