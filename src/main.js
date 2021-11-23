/*!

=========================================================
* BootstrapVue Argon Dashboard - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/bootstrap-vue-argon-dashboard
* Copyright 2020 Creative Tim (https://www.creative-tim.com)

* Coded by www.creative-tim.com

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Vue from 'vue';
import DashboardPlugin from './plugins/dashboard-plugin';
import App from './App.vue';
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// import "bootstrap/dist/css/bootstrap.css";
// import "bootstrap-vue/dist/bootstrap-vue.css";
import memberStore from "./store/modules/memberStore";

// router setup
import router from './routes/router';

import store from './store/index'

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);
// plugin setup
Vue.use(DashboardPlugin);
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  store,
  router,
  async beforeCreate() {
    let token = sessionStorage.getItem("access-token");
    if (memberStore.state.userInfo == null && token) {
      await memberStore.dispatch("getUserInfo", token);
    }
  },
}).$mount("#app");
