import Vue from 'vue'
import Router from 'vue-router'

// Common
import Main from "@/components/VueMain"
// import TheHeader from "@/components/common/TheHeader"

// User
import Login from "@/components/user/UserLogin"
import Join from "@/components/user/UserJoin"
import MyPage from "@/components/user/MyPage"
import Follower from "@/components/user/UserFollower"
import Following from "@/components/user/UserFollowing"
import Modify from "@/components/user/UserModify"

// Review
import ReviewWrite from "@/components/review/ReviewWrite"
import ReviewDetail from "@/components/review/ReviewDetail"
// import ReviewListItem from "@/components/review/ReviewListItem"
import ReviewList from "@/components/review/ReviewList"
import ReviewModify from "@/components/review/ReviewModify"

// Map
import Map from "@/components/GoogleMap"

// Notice
import NoticeWrite from "@/components/notice/NoticeWrite"
import NoticeDetail from "@/components/notice/NoticeDetail"
// import NoticeListItem from "@/components/notice/NoticeListItem"
import NoticeList from "@/components/notice/NoticeList"
import NoticeModify from "@/components/notice/NoticeModify"

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Main,
    },
    {
      path: '/user/login',
      component: Login
    },
    {
      path: '/user/join',
      component: Join
    },
    {
      path: '/user/mypage',
      component: MyPage
    },
    {
      path: '/user/follower',
      component: Follower
    },
    {
      path: '/user/following',
      component: Following
    },
    {
      path: '/user/modify',
      component: Modify
    },
    {
      path: '/map',
      component: Map
    },
    {
      path: '/kakao',
      beforeEnter() {
        const clientId = '980d869835ee11e022c77686d37291f1';
        const redirectUri = 'http://localhost:80/user/api/kakao/callback';
        const responseType = 'code';
        const authUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=${responseType}`;
        window.location.href = authUrl;
      },
    },
    {
      path: '/review/list',
      component: ReviewList
    },
    {
      path: '/review/write',
      component: ReviewWrite
    },
    {
      path: '/review/detail/:review_id',
      component: ReviewDetail
    },
    {
      path: '/review/modify/:review_id',
      component: ReviewModify
    },
    {
      path: '/notice/list',
      component: NoticeList

    },
    {
      path: '/notice/write',
      component: NoticeWrite
    },
    {
      path: '/notice/detail',
      component: NoticeDetail
    },
    {
      path: '/notice/modify',
      component: NoticeModify
    },
  ]
})
