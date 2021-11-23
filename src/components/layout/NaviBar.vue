<template>
  <b-navbar toggleable="lg" class="navbar-custom" fixed="top">
    <b-navbar-brand href="#">
      <router-link to="/">
        <img
          src="/assets/sitelogo.png"
          class="d-inline-block align-middle"
          width="55px"
          alt="Kitten"
        />
      </router-link>
    </b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#"
          ><router-link :to="{ name: 'Home' }" class="link"
            ><b-icon icon="house-fill" font-scale="1"></b-icon> Happy
            House</router-link
          ></b-nav-item
        >
        <b-nav-item href="#"
          ><router-link :to="{ name: 'Board' }" class="link"
            ><b-icon icon="exclamation-square" font-scale="1"></b-icon>
            NOTICE</router-link
          ></b-nav-item
        >
        <b-nav-item href="#"
          ><router-link :to="{ name: 'Instargram' }" class="link">
            <b-icon icon="star-fill" font-scale="1"></b-icon>
            사이트소개</router-link
          ></b-nav-item
        >
        <b-nav-item href="#"
          ><router-link :to="{ name: 'House' }" class="link"
            ><b-icon icon="building" font-scale="1"></b-icon>
            아파트정보</router-link
          ></b-nav-item
        >
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto" v-if="userInfo">
        <b-nav-item class="align-self-center">
          <p class="text-info">
            <br />
            <b-avatar
              variant="warning"
              v-text="userInfo ? userInfo.userid.charAt(0).toUpperCase() : ''"
            ></b-avatar>
            {{ userInfo.username }}({{ userInfo.userid }})님 환영합니다.
          </p>
        </b-nav-item>
        <b-nav-item class="align-self-center"
          ><router-link :to="{ name: 'MyPage' }" class="link align-self-center"
            >내정보보기</router-link
          ></b-nav-item
        >
        <b-nav-item class="align-self-center" v-if="userInfo.userid === 'admin'"
          ><router-link :to="{ name: 'MemberList' }" class="link"
            ><b-icon icon="person-circle" font-scale="1"></b-icon>
            회원관리</router-link
          ></b-nav-item
        >
        <b-button
          variant="danger"
          class="link align-self-center"
          @click.prevent="onClickLogout"
        >
          로그아웃
        </b-button>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto" v-else>
        <b-nav-item-dropdown right>
          <template #button-content>
            <b-icon icon="people" font-scale="2"></b-icon>
          </template>
          <b-dropdown-item href="#"
            ><router-link :to="{ name: 'SignUp' }" class="link"
              ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
            ></b-dropdown-item
          >
          <b-dropdown-item href="#"
            ><router-link :to="{ name: 'SignIn' }" class="link"
              ><b-icon icon="key"></b-icon> 로그인</router-link
            ></b-dropdown-item
          >
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NaviBar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style>
.navbar-custom {
  background-color: darkslategrey;
}

a.router-link-exact-active {
  color: #330e36;
}

.link {
  text-decoration: none;
}
</style>
