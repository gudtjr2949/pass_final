<template>
  <div class="template-container">
    <div class="review-container">
      <div class="review-header">
        <div class="review-title">{{ plan.title }}</div>

        <div class="author-info">
          <h5 class="author-name">작성자 : {{ plan.user_id }}</h5>
          <div class="author-actions">
            <div v-if="idCheck == false && followCheck == false">
              <button @click="Follow" class="follow-btn">팔로우</button>
            </div>
            <div v-else-if="idCheck == false && followCheck == true">
              <button @click="UnFollow" class="unfollow-btn">언팔로우</button>
            </div>
          </div>
        </div>
        <div>
          <router-link :to="`/plan/modify/${plan_id}`">
            <button class="modify-btn">수정</button>
          </router-link>
          <button class="modify-btn">스크랩</button>
        </div>
      </div>
      <div class="divider"></div>
      <!-- 추가: 점을 그어줄 구분선 -->

  <div class="route-section" v-if="routes">
    <h4 class="section-title">경로</h4>
    <div class="item-container">
      <div v-for="route in routes" :key="route.route_id" class="route-item">
        <a :href="route.url" target="_blank" class="item-link">
          <div class="route-image" v-if="route && routes.length && route.photo">
            <img :src="route.photo" :alt="route.name" class="route-photo" style="width: 100px; height: 100px;" />
          </div>
          <div class="route-image" v-else>
            <img src="@/assets/pngwing.png" alt="Default Image" class="plan-image"/>
          </div>
          <div class="route-info">
            <h5 class="route-name">{{ route.formatted_address }}</h5>
          </div>
        </a>
      </div>
    </div>
  </div>

<div class="row-container">
<div class="place-section" v-if="places">
  <h4 class="section-title">장소</h4>
  <div class="item-container">
    <div v-for="place in places" :key="place.place_id" class="place-item">
      <a :href="place.url" target="_blank" class="item-link">
        <div class="place-image" v-if="place && places.length && place.photo">
          <img :src="place.photo" :alt="place.name" class="place-photo" style="width: 100px; height: 100px;"/>
        </div>
        <div class="place-image" v-else>
          <img src="@/assets/pngwing.png" alt="Default Image" class="plan-image"/>
        </div>
        <div class="place-info">
          <h5 class="place-name">{{ place.name }}</h5>
          <p class="place-description">{{ place.vicinity }}</p>
        </div>
      </a>
    </div>
  </div>
</div>



  <div class="plane-section" v-if="planes">
    <h4 class="section-title">항공</h4>
    <div class="item-container">
      <div v-for="plane in planes" :key="plane.plane_id" class="plane-item">
        <div class="plane-info">
          <h5 class="plane-name">{{ plane.start }} -> {{ plane.end }}</h5>
          <p class="plane-name">{{ plane.arrival }} -> {{ plane.departure }}</p>
          <p class="plane-description">항공: {{ plane.carrierCode }}</p>
          <p class="plane-description">가격 : {{ plane.price }}</p>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="divider"></div>

      <h4 class="section-title">본문</h4>
    <div>
      여행 시작 : {{plan.start_date}} 고생 시작 : {{plan.end_date}}
    </div>
      <div class="review-content">{{ plan.content }}</div>
      <!-- <div class="image-gallery">
        <div v-for="image in image" :key="image.image_path" class="image-item">
          <img :src="getImageUrl(image.image_name)" :alt="image.image_name" />
        </div>
      </div> -->
      <div class="divider"></div>
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
import { h } from "vue";

export default {
  data() {
    return {
      plan: {},
      routes: [],
      planes: [],
      places: [],
      plan_id: "",

      newComment: "",
      comments: [],
      idCheck: "", // 현재 로그인되어 있는 계정과 review.user_id가 다른지 확인
      followCheck: "", // 현재 로그인되어 있는 계정이 review.user_id를 팔로우 했는지 확인
      userInfo: {},
    };
  },
  created() {
    const plan_id = this.$route.params.plan_id;

    http.get("/plan/api/detail/" + plan_id)
      .then((res) => {
        console.log(res);
        this.plan = res.data.plan;
        this.plan_id = plan_id;

        this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));


         http.get("/plan/api/place/" + plan_id)
        .then((res) => {
          this.places = res.data.list;
        })
        .catch((error) => {
          console.error(error);
        });

        http.get("/plan/api/route/" + plan_id)
        .then((res) => {
          this.routes = res.data.list;
        })
        .catch((error) => {
          console.error(error);
        });

        http.get("/plan/api/plane/" + plan_id)
        .then((res) => {
          this.planes = res.data.list;
        })
        .catch((error) => {
          console.error(error);
        });

        http.get("/plancomment/api/" + plan_id)
        .then((res) => {
          this.comments = res.data.list;
        })
        .catch((error) => {
          console.error(error);
        });

        return http.post("/user/api/checkFollow", {
          user_id: this.userInfo.user_id,
          following_id: this.plan.user_id,
        });
      })  
      .then((res) => {
        if (res.data.check == 1) {
          this.followCheck = true;
        } else {
          this.followCheck = false;
        }

        console.log("팔로우 여부 " + this.followCheck);

        if (this.userInfo.user_id != this.plan.user_id) {
          console.log(this.userInfo.user_id + " " + this.plan.user_id);
          this.idCheck = false;
        } else {
          this.idCheck = true;
        }
      });     

    console.log("작성자와 로그인 유저 동일 여부 " + this.idCheck);
  },
  methods: {
    
    addComment() {
      const comment = {
        plan_id: this.planw_id,
        user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
        content: this.$refs.content.value,
      };

      console.log(comment);

      this.comments.push(comment);
      this.newComment = "";
      // 여기서 서버로 댓글을 보내는 요청(axios 등)을 보내면 됩니다.
      http.post("/plancomment/api", {
        plan_id: this.plan_id,
        user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
        content: this.$refs.content.value,
      });
      // axios.post("http://localhost:80/review_comment/api/write", comment)
    },
    toggleLike(comment) {
      console.log(this.routes)
      console.log(this.places)
      console.log(this.planes)
      comment.liked = !comment.liked;
    },
    Follow() {
      http
        .post("/user/api/follow", {
          user_id: this.userInfo.user_id,
          following_id: this.plan.user_id,
        })
        .then(() => {
          this.followCheck = true;
        });
    },
    UnFollow() {
      http
        .post("/user/api/unfollow", {
          user_id: this.userInfo.user_id,
          following_id: this.plan.user_id,
        })
        .then(() => {
          this.followCheck = false;
        });
    },
  },
};
</script>
<style scoped>
.item-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: center;
}

.place-item,
.route-item,
.plane-item {
  display: flex;
  flex-basis: 33.33%;
  margin-bottom: 10px;
  align-items: flex-start;
}

.item-link {
  text-decoration: none;
  color: inherit;
  display: flex;
  align-items: center;
}

.place-image,
.route-image,
.plane-image {
  flex-basis: 40%;
  margin-right: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.place-info,
.route-info,
.plane-info {
  flex-grow: 1;
}

.place-photo,
.route-photo,
.plane-photo {
  width: 100%;
  height: auto;
  max-width: 100px;
  max-height: 100px;
  border-radius: 4px;
  object-fit: cover;
}

.default-image {
  width: 50px;
  height: 50px;
}

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