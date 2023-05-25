<template>
  <div class="template-container">
    <div class="review-container">
      <div class="review-header">
        <div class="review-title">{{ notice.title }}</div>
        <div class="author-info">
          <h5 class="author-name">작성자 : {{ notice.user_id }}</h5>
        </div>
        <div>
          <router-link :to="`/notice/modify/${this.notice_id}`" v-if="isAdmin">
            <button class="modify-btn">수정</button>
          </router-link>
        </div>
      </div>
      <div class="divider"></div>
      <!-- 추가: 점을 그어줄 구분선 -->
      <div class="review-content">{{ notice.content }}</div>
    </div>
  </div>
</template>
<script>
import { http } from "@/api/http";

export default {
  data() {
    return {
      notice: {},
      notice_id: "",
      userInfo: {},
      isAdmin: "",
    };
  },
  created() {
    // 비동기
    // TODO : 글목록 얻기.
    this.notice_id = this.$route.params.notice_id;
    http.get(`/notice/api/detail/${this.notice_id}`).then((response) => {
      this.notice = response.data.notice;
      console.log(this.notice);
    });

    axios
      .get(
        "http://192.168.208.54:80/user/api/checkAdmin/" +
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
};
</script>
<style scoped>
.template-container {
  background-color: #ffffff;
  margin: 100px;
}

.review-container {
  max-width: 1300px;
  margin: 0 auto;
  padding: 20px;
  background-color: #ffffff;
}

.review-header {
  margin-bottom: 40px;
  /* 제목과 계획글, 내용 사이의 간격을 늘림 */
}

.divider {
  width: 100%;
  height: 2px;
  background-color: #ddd;
  margin: 20px 0;
  /* 점을 그어줄 구분선의 간격 조정 */
}

.review-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.review-content {
  font-size: 18px;
  color: #555;
}

.image-gallery {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-item {
  flex: 0 0 300px;
  margin: 30px;
}

.image-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.comment-section {
  margin-top: 20px;
}

.comment-section h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.comment-section textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
  margin-bottom: 10px;
}

.comment-section button {
  padding: 10px 20px;
  background-color: #fff;
  color: red;
  border: 1px solid red;
  border-radius: 4px;
  cursor: pointer;
}

.comment-list {
  margin-top: 20px;
}

.comment-list ul {
  list-style-type: none;
  padding: 0;
}

.comment-list li {
  margin-bottom: 10px;
}

.no-comments {
  margin-top: 20px;
  color: #888;
}

.comment-input {
  display: flex;
  align-items: center;
}

.comment-input textarea {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
  margin-bottom: 10px;
}

.comment-input button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}

.comment-separator {
  width: 100%;
  height: 1px;
  background-color: #ddd;
  margin: 10px 0;
}

.user-id {
  font-weight: bold;
  margin-right: 5px;
}

.comment-item {
  display: flex;
  align-items: center;
}

.comment-header {
  flex: 0 0 auto;
  margin-right: 10px;
}

.comment-content {
  flex: 1;
  text-align: center;
}

.comment-actions {
  flex: 0 0 auto;
}

.like-button {
  background: none;
  border: none;
  cursor: pointer;
}

.like-button i {
  color: #888;
  transition: color 0.3s;
}

.like-button i.liked {
  color: red;
}

.author-name {
  margin-left: auto;
  text-align: right;
}

.author-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.author-actions {
  flex: 0 0 auto;
  align-self: self-start;
  padding-left: 10px;
}

.follow-btn {
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.unfollow-btn {
  background-color: #ffffff;
  color: #42a1ff;
  font-weight: bold;
  border: 1px solid #42a1ff;
  border-radius: 4px;
  cursor: pointer;
}

.modify-btn {
  width: 70px;
  height: 30px;
  background-color: #42a1ff;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>