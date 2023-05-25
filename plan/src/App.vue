<template>
  <div id="app">
    <the-header></the-header>
    <div class="background-container">
      <!-- <img src="@/assets/main5.jpg" class="background-image" /> -->
      <div class="bg-video">
        <video class="bg-video__content" autoplay muted loop>
          <source src="@/assets/airplain.mp4" type="video/mp4" />
        </video>
      </div>
    </div>
    <router-view class="router"></router-view>
    <!-- <the-footer></the-footer> -->
  </div>
</template>

<script>
import TheHeader from "./components/common/TheHeader.vue";
// import TheFooter from "./components/common/TheFooter.vue";

export default {
  name: "App",
  components: {
    TheHeader,
    // TheFooter,
  },
  data() {
    return {
      showOverlay: true,
    };
  },
  mounted() {
    this.setVideoPlaybackRate();
  },
  methods: {
    setVideoPlaybackRate() {
      const video = document.querySelector(".bg-video__content");
      video.playbackRate = 0.65; // 0.5의 재생 속도로 설정
    },
  },
};
</script>

<style scoped>
#app {
  width: 100%;
  /* height: 100%; */
  margin: 0 auto;
  animation: fadein 5s ease 3s;
  -webkit-animation: fadein 3s;
  /* Safari and Chrome */
}

.background-container {
  width: 100%;
  margin: 0 auto; /* 중앙 정렬을 위해 margin을 auto로 설정 */
  /* position: relative; */
}

.bg-video {
  position: absolute;
  /* top: 0; */
  /* left: 0; */
  height: 100%;
  width: 100%;
  z-index: -1;
  opacity: 0.5;
  
}

.bg-video__content {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.router {
  margin-bottom: 100px;
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

