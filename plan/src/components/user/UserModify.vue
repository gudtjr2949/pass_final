<template>
    <div class="modify-container">
        <div class="modify-form">
            <div class="form-group">
                <label for="user_id">아이디</label>
                <div class="input-container">
                    <input type="text" id="user_id" v-model="user.user_id" readonly />
                </div>
            </div>
            <div class="form-group">
                <label for="user_pw">비밀번호</label>
                <input type="password" id="user_pw" ref="user_pw" :placeholder="user.user_pw" />
            </div>
            <div class="form-group">
                <label for="user_name">이름</label>
                <input type="text" id="user_name" ref="user_name" :placeholder="user.user_name" />
            </div>
            <div class="form-group">
                <label for="user_email_domain">이메일</label>
                <div class="email-domain-container">
                    <input type="text" id="user_email" ref="user_email" :placeholder="user.user_email" />
                    <span class="at-symbol">@</span>
                    <input type="text" id="user_domain" ref="user_domain" :placeholder="user.user_domain" />
                </div>
            </div>
            <div>
                <button class="modify-button" @click="modifyUser">회원정보 수정</button>
                <router-link to="/">
                    <button class="modify-button" >메인페이지로 이동</button></router-link>
            </div>

        </div>
    </div>
</template>
<script>
import { http } from "@/api/http";
import router from "../../router";

export default {
    data() {
        return {
            user: {},
        };
    },
    created() {
        const userId = JSON.parse(sessionStorage.getItem("userInfo")).user_id;

        http.get(`/user/api/mypage/${userId}`).then((res) => {
            this.user = res.data.user;
        });
    },
    methods: {
        modifyUser() {
            http.put("/user/api/modify", this.user).then(() => {
                router.push("/");
            })
        },
    }
}
</script>
<style scoped>
.modify-form {
    margin-top: 100px;
    background-color: #ffffff;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* 수평 가운데 정렬 */
    align-items: center;
    /* 수직 가운데 정렬 */
    margin-bottom: 20px;
}

.input-container {
    position: relative;
    /* margin-right: 10px; */
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 10px;
}

.email-domain-container {
    align-items: center;
}

input {
    padding: 8px;
    border: 1px solid #cccccc;
    border-radius: 4px;
}

.at-symbol {
    margin: 0 4px;
    font-weight: bold;
}

.modify-button {
    padding: 12px;
    background-color: #42a1ff;
    color: #ffffff;
    font-weight: bold;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 10px;
    margin: 5px;
}

.modify-button:hover {
    background-color: #1e88e5;
}
</style>