<template>
  <div class="mypage">
    <div class="user-info">
      <h3 class="user-info-title">사용자 정보</h3>
      <div class="user-info-item">
        <strong>아이디:</strong> {{ user.user_id }}
      </div>
      <div class="user-info-item">
        <strong>이름:</strong> {{ user.user_name }}
      </div>
      <div class="user-info-item">
        <strong>이메일:</strong> {{ user.user_email }} @ {{ user.user_domain }}
      </div>
      <div class="user-info-item"><strong>역할:</strong> {{ user.role }}</div>
    </div>

    <div class="follower-following">
      <router-link to="/user/follower" class="follower-container">
        <h3>팔로워</h3>
        {{ follower.length }}
      </router-link>

      <router-link to="/user/following" class="follower-container">
        <h3>팔로잉</h3>
        {{ following.length }}
      </router-link>
    </div>
    <div class="modify-btn">
      <router-link to="/user/modify">
        <button class="add-button">회원정보 수정</button>
      </router-link>
      <button class="add-button" @click="deleteUser">회원탈퇴</button>
    </div>
  </div>
</template>
  
<script>
// import axios from "axios";
import router from "../../router";
import { http } from "@/api/http";

export default {
  data() {
    return {
      user: {},
      follower: [],
      following: [],
      mylist: [],
    };
  },
  created() {
    const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;
    http.get(`/user/api/mypage/${userId}`).then((res) => {
      this.user = res.data.user;
    });

    http.get(`/user/api/follower_list/${userId}`).then((res) => {
      this.follower = res.data.list;
      console.log(this.follower);
    });

    http.get(`/user/api/following_list/${userId}`).then((res) => {
      this.following = res.data.list;
      console.log(this.following);
    });

    http.get(`/review/api/my_review/${userId}`).then((res) => {
      this.mylist = res.data.review;
      console.log(this.mylist);
    });
  },

  methods: {
    deleteUser() {
      sessionStorage.removeItem("userInfo");
      http.delete(`/user/api/delete/${this.user.user_id}`).then(() => {
        router.push("/");
        this.$router.go(this.$router.currentRoute);
      });
    },
  },
};
</script>
  
<style scoped>
.mypage {
  margin: 10px 300px;
}

.user-info {
  margin: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.user-info-title {
  margin-bottom: 8px;
}

.user-info-item {
  margin-bottom: 4px;
}

.follower-following {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.follower-container,
.following-container {
  width: 45%;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
  margin: 10px;
}

.follower-container h3,
.following-container h3 {
  margin-bottom: 8px;
}

.modify-btn {
  /* align-content: center; */
  /* justify-content: center; */
  display: flex;
  justify-content: center;
}

.add-button {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 10px;
}

.add-button:hover {
  background-color: #1e88e5;
}
</style>
  