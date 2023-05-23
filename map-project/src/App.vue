<template>
  <div id="app">
    <the-header></the-header>
    <div class="background-container">
      <img :src="require(`@/assets/airplain.jpg`)" class="background-image">
      <!-- <img
        src="https://images.unsplash.com/photo-1627328714377-dd9f50ebc7d6?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=701&q=80"
        alt=""> -->
    </div>
    <router-view />
    <!-- <the-footer></the-footer> -->
  </div>
</template>

<script>
import TheHeader from './pages/common/TheHeader.vue';
import TheFooter from './pages/common/TheFooter.vue'

export default {
  name: 'App',
  components: {
    TheHeader,
    TheFooter,
  },
  data() {
    return {
      showOverlay: true
    }
  },
  mounted() {

  },
}
</script>

<style>
#app {
  width: 100%;
  margin: 0 auto;
  animation: fadein 5s ease 3s;
  -webkit-animation: fadein 3s;
  /* Safari and Chrome */
}

/* #app img {
  width: 100%;
} */
.background-container {
  width: 100%; /* 이미지를 80% 크기로 설정 */
  margin: 0 auto; /* 중앙 정렬을 위해 margin을 auto로 설정 */
}

.background-image {
  width: 100%;
}

@keyframes fadein {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@-webkit-keyframes fadein {

  /* Safari and Chrome */
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>

