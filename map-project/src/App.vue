<template>
  <div id="app">
    <the-header></the-header>
    <div class="background-container">
      <!-- <img :src="require(`@/assets/back.jpg`)" class="background-image"> -->
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

.background-container {
  width: 100%;
  margin: 0 auto; /* 중앙 정렬을 위해 margin을 auto로 설정 */
  position: relative; /* 이미지를 포함하는 요소에 position: relative; 설정 */
}

.background-image {
  width: 100%;

  position: absolute;
  object-fit: cover;
  z-index: -1; /* 이미지를 뒤로 보냄 */
  
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

