import DashboardLayout from '@/views/Layout/DashboardLayout.vue';
import AuthLayout from '@/views/Pages/AuthLayout.vue';

import NotFound from '@/views/NotFoundPage.vue';

import Member from "@/views/Member.vue";
import MemberLogin from "@/components/user/MemberLogin.vue";
import MemberJoin from "@/components/user/MemberJoin.vue";
import MemberMyPage from "@/components/user/MemberMyPage.vue";
import MemberList from "@/components/user/MemberList.vue";
import MemberUpdate from "@/components/user/MemberUpdate.vue";
import MemberModify from "@/components/user/MemberModify.vue";
import MemberView from "@/components/user/MemberView.vue";

import Board from "@/views/Board.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardView from "@/components/board/BoardView.vue";
import BoardUpdate from "@/components/board/BoardUpdate.vue";

import store from "@/store/index.js";

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("회원만 이용 가능합니다 :)");
    // next({ name: "SignIn" });
    next({ name: "login" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "*",
    redirect: "/",
  },
  {
    path: '/',
    redirect: 'dashboard',
    component: DashboardLayout,
    children: [
      {
        path: '/user',
        redirect: 'login',
        component: AuthLayout,
        children: [
          {
            path: '/login',
            name: 'login',
            component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Login.vue')
          },
          {
            path: '/register',
            name: 'register',
            component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Register.vue')
          },
          {
            path: '/findpwd',
            name: 'findpwd',
            component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/FindPassword.vue')
          },
          { path: '*', component: NotFound }
        ]
      },
      {
        path: '/dashboard',
        name: 'dashboard',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "demo" */ '../views/Dashboard.vue')
      },
      {
        path: '/icons',
        name: 'icons',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Icons.vue')
      },
      {
        path: '/profile',
        name: 'profile',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/UserProfile.vue')
      },
      {
        path: '/introduce',
        name: 'introduce',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Introduce.vue')
      },
      {
        path: '/maps',
        name: 'maps',
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "demo" */ '../views/KakaoMaps.vue')
      },
      {
        path: '/tables',
        name: 'tables',
        component: () => import(/* webpackChunkName: "demo" */ '../views/RegularTables.vue')
      },
      {
        path: '/subscribe',
        name: 'subscribe',
        component: () => import(/* webpackChunkName: "demo" */ '../views/ContactForm.vue')
      },
      {
        path: "/board",
        name: "Board",
        component: Board,
        redirect: "/board/list",
        children: [
          {
            path: "list",
            name: "BoardList",
            component: BoardList,
          },
          {
            path: "write",
            name: "BoardWrite",
            beforeEnter: onlyAuthUser,
            component: BoardWrite,
          },
          {
            path: "detail/:articleno",
            name: "BoardView",
            beforeEnter: onlyAuthUser,
            component: BoardView,
          },
          {
            path: "update/:articleno",
            name: "BoardUpdate",
            beforeEnter: onlyAuthUser,
            component: BoardUpdate,
          },
        ],
      },
    ]
  },
];

export default routes;
