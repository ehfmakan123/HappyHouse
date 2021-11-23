<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="아이디:"
          label-for="userid"
          description="아이디를 입력하세요."
          v-if="this.type === 'register'"
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="userid"
            type="text"
            required
            placeholder="아이디 입력..."
            v-if="this.type === 'register'"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="username-group"
          label="이름:"
          label-for="username"
          description="이름을 입력하세요."
        >
          <b-form-input
            id="username"
            v-model="username"
            type="text"
            placeholder="이름 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="userpwd-group"
          label="비밀번호:"
          label-for="userpwd"
          description="비밀번호를 입력하세요."
        >
          <b-form-input
            id="userpwd"
            v-model="userpwd"
            type="password"
            required
            placeholder="비밀번호 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="email-group" label="이메일:" label-for="email">
          <b-form-input
            id="email"
            v-model="email"
            type="text"
            placeholder="이메일 입력..."
          ></b-form-input>
        </b-form-group>

        <b-button
          type="submit"
          variant="primary"
          class="m-1"
          v-if="this.type === 'register'"
          >회원 가입</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else
          >회원 정보 수정</b-button
        >
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { mapState } from "vuex";
import { joinMember, modifyMember } from "@/api/member";

const memberStore = "memberStore";

export default {
  name: "MemberJoinForm",
  data() {
    return {
      username: "",
      userid: "",
      userpwd: "",
      email: "",
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    if (this.type === "modify") {
      // this.$store.dispatch("getMember", this.$route.params.userid);
      this.username = this.userInfo.username;
      this.userid = this.userInfo.userid;
      this.userpwd = this.userInfo.userpwd;
      this.email = this.userInfo.email;
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.userid &&
        ((msg = "아이디 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.username &&
        ((msg = "이름 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.userpwd &&
        ((msg = "비밀번호 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      if (!err) alert(msg);
      else this.type === "register" ? this.registMember() : this.updateMember();
    },
    onReset(event) {
      event.preventDefault();
      this.username = "";
      this.userid = "";
      this.userpwd = "";
      this.email = "";
    },
    registMember() {
      joinMember(
        {
          userid: this.userid,
          username: this.username,
          userpwd: this.userpwd,
          email: this.email,
        },
        ({ data }) => {
          let msg = "회원가입시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "회원가입이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "SignIn" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateMember() {
      console.log(this.$store);
      modifyMember(
        {
          userid: this.userid,
          username: this.username,
          userpwd: this.userpwd,
          email: this.email,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$store.commit(
            "memberStore/SET_USER_INFO",
            {
              userid: this.userid,
              username: this.username,
              userpwd: this.userpwd,
              email: this.email,
              joindate: this.userInfo.joindate,
            },
            { root: true }
          );
          this.$router.push({ name: "MyPage" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "MemberList" });
    },
  },
};
</script>

<style></style>
