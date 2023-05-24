<template>
  <div class="plan-list">
    <div class="button-container">
      <div class="tab-buttons">
        <button
          @click="showAllplans"
          :class="{ active: activeTab === 'all' }"
          class="tab-button"
        >
          전체
        </button>
        <button
          @click="showFollowerplans"
          :class="{ active: activeTab === 'following' }"
          class="tab-button"
        >
          팔로잉
        </button>
        <button
          @click="showMyplans"
          :class="{ active: activeTab === 'my' }"
          class="tab-button"
        >
          내 글
        </button>
      </div>    
    </div>
    <div
      v-for="(chunk, index) in chunkedplans"
      :key="index"
      class="plan-row"
    >
      <plan-list-item
        v-for="plan in chunk"
        :key="plan.plan"
        :plan="plan"
      ></plan-list-item>
    </div>
  </div>
</template>

<script>
import PlanListItem from "./PlanListItem.vue";
import { http } from "@/api/http";

export default {
  name: "PlanList",
  components: {
    PlanListItem,
  },
  data() {
    return {
      user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
      plans: [],
      my_plans: [],
      following_plans: [],
      currentPage: 1,
      pageSize: 8,
      activeTab: "",
    };
  },
  created() {
    http.get("/plan/api").then((res) => {
      console.log(res);
      
      const requests = res.data.list.map((plan) => {
      console.log(plan)
      let id = plan.plan_id;
      console.log(id)

      const placeRequest = http.get("/plan/api/place/" + id);
      const routeRequest = http.get("/plan/api/route/" + id);
      const planeRequest = http.get("/plan/api/plane/" + id);

      return Promise.all([placeRequest, routeRequest, planeRequest])
        .then(([placeResponse, routeResponse, planeResponse]) => {
          const planObject = {
            plan: plan,
            places: placeResponse.data.list ? placeResponse.data.list : "",
            routes: routeResponse.data.list ? routeResponse.data.list : "",
            planes: planeResponse.data.list ? planeResponse.data.list : "",
          };
          return planObject;
        });
    });
    return Promise.all(requests);
  })
  .then((planObjects) => {    
    this.plans = planObjects;    
  })
  .catch((error) => {
    console.error(error);
  });


    http
      .get("/plan/api/following_plan/" + this.user_id)
      .then((response) => {
      const requests = response.data.list.map((plan) => {
      let id = plan.plan_id;
      console.log(id)

      const placeRequest = http.get("/plan/api/place/" + id);
      const routeRequest = http.get("/plan/api/route/" + id);
      const planeRequest = http.get("/plan/api/plane/" + id);

      return Promise.all([placeRequest, routeRequest, planeRequest])
        .then(([placeResponse, routeResponse, planeResponse]) => {
          const planObject = {
            plan: plan,
            places: placeResponse.data.list ? placeResponse.data.list : "",
            routes: routeResponse.data.list ? routeResponse.data.list : "",
            planes: planeResponse.data.list ? planeResponse.data.list : "",
          };
          return planObject;
        });
    });
    return Promise.all(requests);
  })
  .then((planObjects) => {    
    this.my_plans = planObjects;    
  })
  .catch((error) => {
    console.error(error);
  });


    http.get("/plan/api/" + this.user_id).then((response) => {
      const requests = response.data.list.map((plan) => {
      console.log(plan)
      let id = plan.plan_id;
      console.log(id)
      const placeRequest = http.get("/plan/api/place/" + id);
      const routeRequest = http.get("/plan/api/route/" + id);
      const planeRequest = http.get("/plan/api/plane/" + id);

      return Promise.all([placeRequest, routeRequest, planeRequest])
        .then(([placeResponse, routeResponse, planeResponse]) => {
          const planObject = {
            plan: plan,
            places: placeResponse.data.list ? placeResponse.data.list : "",
            routes: routeResponse.data.list ? routeResponse.data.list : "",
            planes: planeResponse.data.list ? planeResponse.data.list : "",
          };
          return planObject;
        });
    });
    return Promise.all(requests);
  })
  .then((planObjects) => {    
    this.following_plans = planObjects;    
  })
  .catch((error) => {
    console.error(error);
  });
  },
  computed: {
    chunkedplans() {
      const chunkSize = 4; // 한 줄에 보여줄 리뷰 개수
      const chunks = [];
      let index = 0;

      let tmp_plans = [];

      if (this.activeTab == "following") {
        tmp_plans = this.following_plans;
        console.log("팔로잉 글 보기");
      } else if (this.activeTab == "my") {
        tmp_plans = this.my_plans;
        console.log("내 글 보기");
      } else {
        tmp_plans = this.plans;
        console.log("전체 보기");
      }

      console.log(tmp_plans);

      while (index < tmp_plans.length) {
        chunks.push(tmp_plans.slice(index, index + chunkSize));
        index += chunkSize;
      }

      return chunks;
    },

    paginatedplans() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tmp_plans.slice(startIndex, endIndex);
    },
    totalPages() {
      return Math.ceil(this.tmp_plans.length / this.pageSize);
    },

    currentplans() {
      // 현재 선택된 탭에 따라 보여줄 리뷰 목록을 결정
      if (this.activeTab === "following") {
        return this.following_plans;
      } else if (this.activeTab === "my") {
        return this.my_plans;
      } else {
        return this.plans;
      }
    },
  },
  methods: {
    goToPage(page) {
      this.currentPage = page;
    },

    showFollowerplans() {
      this.activeTab = "following";
    },

    showMyplans() {
      this.activeTab = "my";
    },

    showAllplans() {
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

.plan-list {
  overflow-x: auto;
  white-space: nowrap;
  margin: 100px;
  /* 상하좌우 여백 설정 */
}

.plan-row {
  display: flex;
  margin-bottom: 16px;
  justify-content: center;
}

.plan-card {
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