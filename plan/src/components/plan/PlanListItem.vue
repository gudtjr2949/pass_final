<template>
  <div class="plan-card">
    <router-link :to="`/plan/detail/${plan.plan_id}`" class="plan-card-link">
      <div class="plan-card-image">
        <div class="image-container">
          <!-- <img :src="getImageUrl(plan)" alt="plan Image" class="plan-image" style="width: 200px; height: 150px;"/> -->
        </div>
      </div>
      <div class="plan-card-title">
        <h3>{{ plan.title }}</h3>
      </div>
      <div class="plan-card-content">
        <p><strong>작성자:</strong> {{ plan.user_id }}</p>
        <p><strong>조회수:</strong> {{ plan.hit }}</p>
        <p><strong>작성일:</strong> {{ plan.register_time }}</p>
      </div>
    </router-link>
  </div>
</template>
  
<script>
// import axios from 'axios';

export default {
  name: "PlanListItem",
  props: {
    plan: Object,
  },
  methods: {
    
    getImageUrl(plan) {
      console.log(plan)
      console.log(plan);
      return require("@/assets/save_image/" + plan.plan + "/" + plan.first_image);
    },
  }
};
</script>
  
<style scoped>
.plan-card {
  flex: 0 0 auto;
  width: 20%; /* Adjust the width as desired */
  padding: 8px; /* Adjust the padding as desired */
  background-color: #ffffff;
  /* border: 2px solid gray; */
  box-sizing: border-box;
  transition: transform 0.3s ease;
}
.plan-card:hover {
  transform: scale(1.05);
}

.plan-card-link {
  display: block;
  height: 100%;
  color: inherit;
  text-decoration: none;
}

.plan-card-title {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 8px;
}

.plan-card-content {
  font-size: 14px;
  color: #666666;
}

/* .plan-card-image {
  display: flex;
  align-items: center;
} */

.image-container {
  max-width: 100%;
  max-height: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center; 
  align-items: center;
}

.image-container img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.plan-image {
  width: 100%;
  height: 100%;
}
</style>
