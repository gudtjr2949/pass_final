<template>
    <div>
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" ref="title" class="form-control" />
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" ref="content" class="form-control"></textarea>
        </div>

        <button @click="checkValue" class="btn btn-primary">작성</button>
    </div>
</template>
<script>

import axios from "axios";
import router from "../../router";

export default {
    data() {
        return {
            user_id: '',
            title: '',
            content: '',
        }
    },
    methods: {
        checkValue() {
            // 사용자 입력값 체크하기
            // 작성자아이디, 제목, 내용이 없을 경우 각 항목에 맞는 메세지를 출력
            let err = true;
            let msg = "";

            !this.$refs.title.value &&
                ((msg = "제목 입력해주세요"),
                    (err = false),
                    this.$refs.title.focus());
            err &&
                !this.$refs.content.value &&
                ((msg = "내용 입력해주세요"),
                    (err = false),
                    this.$refs.content.focus());

            if (!err) alert(msg);
            // 만약, 내용이 다 입력되어 있다면 registReview 호출
            else this.registReview();
        },
        registReview() {
            const formData = new FormData();
            const imageFiles = this.image;

            console.log(formData.get("image[]"));

            formData.append("user_id", JSON.parse(sessionStorage.getItem("userInfo")).user_id);
            formData.append("title", this.$refs.title.value);
            formData.append("content", this.$refs.content.value);

            axios.post("http://localhost:80/notice/api/write", {
                user_id: JSON.parse(sessionStorage.getItem("userInfo")).user_id,
                title: this.$refs.title.value,
                content: this.$refs.content.value
            }).then(() => {
                router.push("/notice/list");
            })
        },
    }
}
</script>
<style></style>