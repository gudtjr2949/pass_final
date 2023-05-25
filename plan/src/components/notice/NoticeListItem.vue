<template>
  <tr class="notice-list-item">
    <td>{{ notice.notice_id }}</td>
    <td>
      <router-link
        :to="`/notice/detail/${notice.notice_id}`"
        class="notice-title"
      >
        {{ notice.title }}
      </router-link>
    </td>
    <td>{{ notice.user_id }}</td>
    <td>{{ notice.hit }}</td>
    <td>{{ notice.register_time }}</td>
  </tr>
</template>

<script>
export default {
  name: "NoticeListItem",
  props: {
    notice: Object,
  },
};
</script>

<style scoped>
.notice-list-item {
  background-color: #f7f7f7;
  text-align: center;
  height: 70px;
}

.notice-title {
  color: #007bff;
  text-decoration: none;
  transition: color 0.3s;
}

.notice-title:hover {
  color: #0056b3;
}
</style>
