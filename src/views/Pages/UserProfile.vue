<template>
  <div id="app">
    <div class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
        style="min-height: 600px; background-image: url(assets/bg3.jpg); background-size: cover; background-position: center top;">
      <b-container fluid>
        <!-- Mask -->
        <span class="mask bg-gradient-success opacity-5"></span>
        <!-- Header container -->
        <b-container fluid class="d-flex align-items-center">
          <b-row >
            <b-col lg="7" md="10">
              <h1 class="display-2 text-white">Hello!! {{ userInfo.username }} </h1>
              <!-- <p class="text-white mt-0 mb-5">This is your profile page. You can see the progress you've made with your
                work and manage your projects or assigned tasks</p> -->
            </b-col>
          </b-row>
        </b-container>
      </b-container>
    </div>

    <b-container fluid class="mt--6">
      <b-row>
        <b-col xl="8" class="order-xl-2 mb-5">
          <edit-profile-form></edit-profile-form>
        </b-col>
        <b-col xl="4" class="order-xl-1">
          <user-card></user-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
  import EditProfileForm from './UserProfile/EditProfileForm.vue';
  import UserCard from './UserProfile/UserCard.vue';
  import { mapState } from "vuex";
  const memberStore = "memberStore";

  export default {
    components: {
      EditProfileForm,
      UserCard
    },
    computed: {
      ...mapState(memberStore, ["userInfo"]),
    },
    methods: {
      modifyMember() {
        this.$router.replace({
          name: "MemberModify",
          params: { userid: this.userInfo.userid },
        });
        //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
      },
      removeMember() {
        if (confirm("정말로 탈퇴하시겠습니까?")) {
          deleteMember(this.userInfo.userid, () => {
            this.$store.commit("memberStore/SET_USER_INFO", null, { root: true });
            this.$store.commit("memberStore/SET_IS_LOGIN", false, { root: true });
            this.$router.push({ name: "dashboard" });
          });
        }
      },
    },
  };
</script>
<style>
</style>
