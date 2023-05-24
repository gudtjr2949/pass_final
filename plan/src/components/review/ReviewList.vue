<template>
  <div class="review-list">
    <div class="button-container">
      <div class="tab-buttons">
        <button
          @click="showAllReviews"
          :class="{ active: activeTab === 'all' }"
          class="tab-button"
        >
          전체
        </button>
        <button
          @click="showFollowerReviews"
          :class="{ active: activeTab === 'following' }"
          class="tab-button"
        >
          팔로잉
        </button>
        <button
          @click="showMyReviews"
          :class="{ active: activeTab === 'my' }"
          class="tab-button"
        >
          내 글
        </button>
      </div>
      <router-link to="/review/write">
        <button class="register-button">후기 글 작성</button>
      </router-link>
    </div>
    <div
      v-for="(chunk, index) in chunkedReviews"
      :key="index"
      class="review-row"
    >
      <review-list-item
        v-for="review in chunk"
        :key="review.review_id"
        :review="review"
      ></review-list-item>
    </div>
  </div>
</template>

<script>
import ReviewListItem from "./ReviewListItem.vue";
import { http } from "@/api/http";

export default {
  name: "ReviewList",
  components: {
    ReviewListItem,
  },
  data() {
    return {
      user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
      reviews: [],
      my_reviews: [],
      following_reviews: [],
      currentPage: 1,
      pageSize: 8,
      activeTab: "",
    };
  },
  created() {
    http.get("/review/api/list").then((res) => {
      this.reviews = res.data.list;
    });

    http
      .get("/review/api/following_review/" + this.user_id)
      .then((response) => {
        this.following_reviews = response.data.review;
      });

    http.get("/review/api/my_review/" + this.user_id).then((response) => {
      this.my_reviews = response.data.review;
    });
  },
  computed: {
    chunkedReviews() {
      const chunkSize = 4; // 한 줄에 보여줄 리뷰 개수
      const chunks = [];
      let index = 0;

      let tmp_reviews = [];

      if (this.activeTab == "following") {
        tmp_reviews = this.following_reviews;
        console.log("팔로잉 글 보기");
      } else if (this.activeTab == "my") {
        tmp_reviews = this.my_reviews;
        console.log("내 글 보기");
      } else {
        tmp_reviews = this.reviews;
        console.log("전체 보기");
      }

      console.log(tmp_reviews);

      while (index < tmp_reviews.length) {
        chunks.push(tmp_reviews.slice(index, index + chunkSize));
        index += chunkSize;
      }

      return chunks;
    },

    paginatedReviews() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tmp_reviews.slice(startIndex, endIndex);
    },
    totalPages() {
      return Math.ceil(this.tmp_reviews.length / this.pageSize);
    },

    currentReviews() {
      // 현재 선택된 탭에 따라 보여줄 리뷰 목록을 결정
      if (this.activeTab === "following") {
        return this.following_reviews;
      } else if (this.activeTab === "my") {
        return this.my_reviews;
      } else {
        return this.reviews;
      }
    },
  },
  methods: {
    goToPage(page) {
      this.currentPage = page;
    },

    showFollowerReviews() {
      this.activeTab = "following";
    },

    showMyReviews() {
      this.activeTab = "my";
    },

    showAllReviews() {
      this.activeTab = "all";
    },
  },
};
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.register-button-container {
  margin-left: auto;
  margin-right: 16px;
  margin-top: 10px;
}

.tab-button {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  margin-top: 10px;
}

.tab-button.active {
  background-color: #185ea4;
}

.register-button {
  padding: 8px 16px;
  background-color: #42a1ff;
  color: #ffffff;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 16px;
  margin-top: 10px;
}

.register-button-container {
  margin-left: auto;
  margin-right: 16px;
  margin-top: 10px;
}

.review-list {
  overflow-x: auto;
  white-space: nowrap;
  margin: 100px;
  /* 상하좌우 여백 설정 */
}

.review-row {
  display: flex;
  margin-bottom: 16px;
  justify-content: center;
}

.review-card {
  flex: 0 0 auto;
  width: 25%;
  /* 가로로 4개씩 */
  padding: 16px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}

.pagination button {
  padding: 6px 10px;
  background-color: #f5f5f5;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 8px;
}

.pagination button.active {
  background-color: #42a1ff;
  color: #ffffff;
}
</style>