<template>
  <div class="review-write">
    <div class="form-group">
      <label for="title">제목</label>
      <input
        type="text"
        id="title"
        ref="title"
        class="form-control"
        v-model="notice.title"
      />
    </div>
    <div class="form-group">
      <label for="content">내용</label>
      <textarea
        id="content"
        ref="content"
        class="form-control"
        v-model="notice.content"
      ></textarea>
    </div>
    <div class="text-center">
      <button @click="modifyNotice" class="btn btn-primary">작성</button>
    </div>
  </div>
</template>
<script>
import router from "../../router";
import { http } from "@/api/http";

export default {
  data() {
    return {
      user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
      notice_id: "",
      notice: {},
    };
  },
  created() {
    this.notice_id = this.$route.params.notice_id;

    http.get(`/notice/api/detail/${this.notice_id}`).then((response) => {
      this.notice = response.data.notice;
    });
  },
  methods: {
    modifyNotice() {
      console.log(this.notice);
      http.put(`/notice/api/modify`, this.notice).then(() => {
        router.push(`/notice/detail/${this.notice_id}`);
      });
    },
  },
};
</script>
<style scoped>
.review-write {
  max-width: 400px;
  margin: 0 auto;
  height: 600px;
  margin-top: 100px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 10px;
}

textarea.form-control {
  height: 150px;
}

.custom-file {
  position: relative;
  display: inline-block;
  width: 100%;
  height: calc(2.25rem + 2px);
}

.custom-file-input {
  position: relative;
  z-index: 2;
  width: 100%;
  height: calc(2.25rem + 2px);
  margin: 0;
  opacity: 0;
}

.custom-file-input:focus ~ .custom-file-label {
  border-color: #80bdff;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.custom-file-label {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  z-index: 1;
  padding: 0.375rem 0.75rem;
  font-weight: 400;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  border: 1px solid #ced4da;
  border-radius: 10px;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 10px;
}

.btn-primary {
  background-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-primary:active {
  background-color: #004080;
}

.uploaded-images {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 10px;
}

.uploaded-image {
  position: relative;
  margin-right: 10px;
  margin-bottom: 10px;
}

.uploaded-image img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.remove-image {
  position: absolute;
  top: -5px;
  right: -5px;
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  border-radius: 50%;
  color: #ff0000;
  font-weight: bold;
  cursor: pointer;
}

.text-center {
  text-align: center;
  margin-top: 20px;
}
</style>