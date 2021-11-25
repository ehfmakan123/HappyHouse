<template>
  <div class="wrapper" id="app">
    <notifications></notifications>
    <side-bar>
      <template slot="links">
        <sidebar-item
          :link="{
            name: 'Home',
            path: '/dashboard',
            icon: 'ni ni-tv-2 text-primary',
          }"
        >
        </sidebar-item>

        <!-- <sidebar-item
            :link="{
              name: 'Icons',
              path: '/icons',
              icon: 'ni ni-planet text-blue'
              }"
            >
        </sidebar-item> -->

        <sidebar-item
              :link="{
                name: '아파트 실거래 조회',
                path: '/maps',
                icon: 'ni ni-pin-3 text-orange',
              }">
        </sidebar-item>

        <sidebar-item
                :link="{
                  name: '게시판',
                  path: '/tables',
                  icon: 'ni ni-bullet-list-67 text-red'
                }">
        </sidebar-item>


        <sidebar-item 
              :link="{
                name: 'Introduce',
                path: '/introduce',
                icon: 'ni ni-air-baloon text-purple'
                }">
        </sidebar-item>
        
        <sidebar-item v-if="userInfo != null"
              :link="{
                name: 'My Page',
                path: '/profile',
                icon: 'ni ni-single-02 text-yellow'
                }">
        </sidebar-item>
<!-- 
        <sidebar-item v-if="userInfo != null && userInfo.userid == 'admin'"
              :link="{
                name: 'Send Email (admin)',
                path: '/subscribe',
                icon: 'ni ni-email-83 text-green'
                }">
        </sidebar-item> -->

        <!-- <sidebar-item v-if="isLogin" @click="onClickLogout" value="Logout" icon='ni ni-key-25 text-info'/> -->
        <b-nav-item v-if="userInfo != null" @click="setLogout" href="#" icon="ni ni-key-25 text-info"><b-icon icon="unlock-fill"></b-icon>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logout</b-nav-item>
        <sidebar-item v-else
                  :link="{
                    name: 'Login',
                    path: '/login',
                    icon: 'ni ni-key-25 text-info'
                  }">
        </sidebar-item>
        <sidebar-item v-if="userInfo == null"
                  :link="{
                    name: 'Register',
                    path: '/register',
                    icon: 'ni ni-circle-08 text-pink'
                  }">
        </sidebar-item>
      </template>

      <!-- <template slot="links-after">
        <hr class="my-3">
        <h6 class="navbar-heading p-0 text-muted">Documentation</h6>

        <b-nav class="navbar-nav mb-md-3">
          <b-nav-item
               href="https://www.creative-tim.com/learning-lab/bootstrap-vue/quick-start/argon-dashboard"
               >
              <i class="ni ni-spaceship"></i>
              <b-nav-text class="p-0">Getting started</b-nav-text>
          </b-nav-item>
          <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/colors/argon-dashboard">
              <i class="ni ni-palette"></i>
              <b-nav-text class="p-0">Foundation</b-nav-text>
          </b-nav-item>
          <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/avatar/argon-dashboard">
              <i class="ni ni-ui-04"></i>
              <b-nav-text class="p-0">Components</b-nav-text>
          </b-nav-item>
        </b-nav>
      </template> -->
    </side-bar>
    <div class="main-content">
      <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar>

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>
      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template>
<script>
  /* eslint-disable no-new */
  import PerfectScrollbar from 'perfect-scrollbar';
  import 'perfect-scrollbar/css/perfect-scrollbar.css';

  function hasElement(className) {
    return document.getElementsByClassName(className).length > 0;
  }

  function initScrollbar(className) {
    if (hasElement(className)) {
      new PerfectScrollbar(`.${className}`);
    } else {
      // try to init it later in case this component is loaded async
      setTimeout(() => {
        initScrollbar(className);
      }, 100);
    }
  }

  import DashboardNavbar from './DashboardNavbar.vue';
  import ContentFooter from './ContentFooter.vue';
  import DashboardContent from './Content.vue';
  import { FadeTransition } from 'vue2-transitions';
  import { mapState, mapMutations } from "vuex";
  const memberStore = "memberStore";

  export default {
    components: {
      DashboardNavbar,
      ContentFooter,
      DashboardContent,
      FadeTransition
    },
    computed: {
      ...mapState(memberStore, ["isLogin", "userInfo"]),
    },
    mounted() {
      console.log("created");
      if (sessionStorage.getItem("access-token") == null) {
      // if (sessionStoragethis.userInfo == null) {
        this.setLogout;
      }
    },
    methods: {
      ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
      setLogout() {
        if (confirm("로그아웃 하시겠습니까?")) {
          this.isLogin = false;
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("access-token");
          this.$router.push('/');
        }
      },
      // onClickLogout() {
      //   this.isLogin = false;
      //   this.SET_IS_LOGIN(false);
      //   this.SET_USER_INFO(null);
      //   if (this.$route.path != "/dashboard") this.$router.push({ name: "dashboard" });
      // },
      initScrollbar() {
        let isWindows = navigator.platform.startsWith('Win');
        if (isWindows) {
          initScrollbar('sidenav');
        }
      }
    },
    mounted() {
      this.initScrollbar()
    }
  };
</script>
<style lang="scss">
</style>
