<template>
  <div class="template-container">
    <div class="review-container">
      <div class="review-header">
        <div class="review-title">{{ review.title }}</div>
        <!-- <div class="bookmark-icon">
          계획 글 : <i class="fas fa-bookmark">{{ review.plan_id }}</i>
        </div> -->
        <div class="bookmark-icon" v-if="this.existPlan">
          <router-link :to="`/plan/detail/${review.plan_id}`"
            >여행 계획 글</router-link
          >
        </div>
        <div class="author-info">
          <h5 class="author-name">작성자 : {{ review.user_id }}</h5>
          <div class="author-actions">
            <div v-if="idCheck == false && followCheck == false">
              <button @click="Follow" class="follow-btn">팔로우</button>
            </div>
            <div v-else-if="idCheck == false && followCheck == true">
              <button @click="UnFollow" class="unfollow-btn">언팔로우</button>
            </div>
          </div>
        </div>
        <div v-if="idCheck == true">
          <router-link :to="`/review/modify/${review_id}`">
            <button class="modify-btn">수정</button>
          </router-link>
          <button class="delete-btn" @click="deleteReview">삭제</button>
        </div>
      </div>
      <div class="divider"></div>
      <!-- 추가: 점을 그어줄 구분선 -->
      <div class="review-content">{{ review.content }}</div>
      <div class="image-gallery">
        <div v-for="image in image" :key="image.image_path" class="image-item">
          <img :src="getImageUrl(image.image_name)" :alt="image.image_name" />
        </div>
      </div>
      <div class="comment-section">
        <div class="comment-input">
          <textarea
            v-model="newComment"
            rows="4"
            placeholder="댓글을 입력하세요..."
            ref="content"
          ></textarea>
          <button @click="addComment" :disabled="newComment.length === 0">
            댓글 작성
          </button>
        </div>
        <div v-if="comments.length > 0" class="comment-list">
          <ul>
            <li
              v-for="comment in comments"
              :key="comment.comment_id"
              class="comment-item"
            >
              <div class="comment-header">
                <span class="user-id">{{ comment.user_id }}</span>
              </div>
              <div class="comment-content">{{ comment.content }}</div>
              <div class="comment-actions">
                <button class="like-button" @click="toggleLike(comment)">
                  좋아요
                </button>
              </div>
            </li>
          </ul>
        </div>
        <div v-else class="no-comments">댓글이 없습니다.</div>
      </div>
    </div>
  </div>
</template>
<script>
import { http } from "@/api/http";
import router from "@/router";

export default {
  data() {
    return {
      review: {},
      image: {},
      review_id: "",
      newComment: "",
      comments: [],
      idCheck: "", // 현재 로그인되어 있는 계정과 review.user_id가 다른지 확인
      followCheck: "", // 현재 로그인되어 있는 계정이 review.user_id를 팔로우 했는지 확인
      userInfo: {},
      existPlan: false,
    };
  },
  created() {
    const review_id = this.$route.params.review_id;

    http
      .get("/review/api/detail/" + review_id)
      .then((res) => {
        this.review = res.data.review;
        this.image = res.data.image;
        this.comments = res.data.comment;
        this.review_id = review_id;

        if (res.data.review.plan_id != 0) {
          this.existPlan = true;
        }

        console.log(res.data.image);

        this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));

        return http.post("/user/api/checkFollow", {
          user_id: this.userInfo.user_id,
          following_id: this.review.user_id,
        });
      })
      .then((res) => {
        if (res.data.check == 1) {
          this.followCheck = true;
        } else {
          this.followCheck = false;
        }

        console.log("팔로우 여부 " + this.followCheck);

        if (this.userInfo.user_id != this.review.user_id) {
          console.log(this.userInfo.user_id + " " + this.review.user_id);
          this.idCheck = false;
        } else {
          this.idCheck = true;
        }
      });

    console.log("작성자와 로그인 유저 동일 여부 " + this.idCheck);
  },
  methods: {
    getImageUrl(imageName) {
      return require("@/assets/save_image/" + this.review_id + "/" + imageName);
    },
    addComment() {
      const comment = {
        review_id: this.review_id,
        user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
        content: this.$refs.content.value,
      };

      console.log(comment);

      this.comments.push(comment);
      this.newComment = "";

      http.post("/review_comment/api/write", {
        review_id: this.review_id,
        user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
        content: this.$refs.content.value,
      });
    },
    toggleLike(comment) {
      comment.liked = !comment.liked;
    },
    Follow() {
      http
        .post("/user/api/follow", {
          user_id: this.userInfo.user_id,
          following_id: this.review.user_id,
        })
        .then(() => {
          this.followCheck = true;
        });
    },
    UnFollow() {
      http
        .post("/user/api/unfollow", {
          user_id: this.userInfo.user_id,
          following_id: this.review.user_id,
        })
        .then(() => {
          this.followCheck = false;
        });
    },
    deleteReview() {
      http.delete(`/review/api/delete/${this.review_id}`).then(() => {
        router.push("/review/list");
      });
    },
  },
};
</script>
<style scoped>
.template-container {
  background-color: #ffffff;
  /* margin: 100px; */
}

.review-container {
  max-width: 1300px;
  margin: 0 auto;
  padding: 20px;
  background-color: #ffffff;
}

.review-header {
  margin-bottom: 40px;
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
  margin: 10px;
}

.delete-btn {
  width: 70px;
  height: 30px;
  background-color: red;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 10px;
}
</style>