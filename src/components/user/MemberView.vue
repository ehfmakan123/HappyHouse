<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>유저 상세 보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listMember">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyMember"
          class="mr-2"
          >회원정보수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="removeMember"
          >회원정보삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${userInfo.username}.
          ${userInfo.userid} [${userInfo.email}]</h3><div>${userInfo.joindate}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import { getMember, deleteMember } from "@/api/member";

export default {
  data() {
    return {
      userInfo: {},
    };
  },
  computed: {
    // message() {
    //   if (this.userInfo.content)
    //     return this.userInfo.content.split("\n").join("<br>");
    //   return "";
    // },
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
  },
  created() {
    getMember(
      this.$route.params.userid,
      (response) => {
        this.userInfo = response.data;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
  },
  methods: {
    listMember() {
      this.$router.push({ name: "MemberList" });
    },
    moveModifyMember() {
      this.$router.replace({
        name: "MemberUpdate",
        params: { userid: this.userInfo.userid },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    removeMember() {
      if (confirm("정말로 삭제?")) {
        deleteMember(this.userInfo.userid, () => {
          this.$router.push({ name: "MemberList" });
        });
      }
    },
  },
};
</script>

<style></style>
