<template>
    <div>
    <router-link :to="`/notice/detail/${notice.notice_id}`">
      <div>
        <h3>{{ notice.title }}</h3>
      </div>
      <div>
        <p><strong>작성자:</strong> {{ notice.user_id }}</p>
        <p><strong>조회수:</strong> {{ notice.hit }}</p>
        <p><strong>작성일:</strong> {{ notice.register_time }}</p>
      </div>
    </router-link>
  </div>
</template>
    
<script>
import axios from 'axios';

export default {
    name: "NoticeListItem",
    props: {
        notice: Object,
    },
    methods: {
      
    }
};
</script>
    
<style scoped>

</style>
  