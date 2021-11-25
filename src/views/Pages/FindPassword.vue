<template>
  <div id="app">
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white"><i class="ni ni-lock-circle-open"></i> Find the password</h1>
              <!-- <p class="text-lead text-white">Use these awesome forms to login or create new account in your project for
                free.</p> -->
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg
          x="0"
          y="0"
          viewBox="0 0 2560 100"
          preserveAspectRatio="none"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <polygon
            class="fill-default"
            points="2560 0 2560 100 0 100"
          ></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8">
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <large>정보 입력</large>
              </div>
              <validation-observer
                v-slot="{ handleSubmit }"
                ref="formValidator"
              >
                <b-form
                  id="sendf"
                  ref="form"
                  @submit.prevent="handleSubmit(onSubmit)"
                >
                  <base-input
                    alternative
                    id="userid"
                    class="mb-3"
                    prepend-icon="ni ni-circle-08"
                    placeholder="ID"
                    name="userid"
                    :rules="{ required: true }"
                    v-model="userid"
                  >
                  </base-input>

                  <base-input
                    alternative
                    type="text"
                    class="mb-3"
                    prepend-icon="ni ni-hat-3"
                    placeholder="Name"
                    name="name"
                    :rules="{ required: true }"
                    v-model="username"
                  >
                  </base-input>

                  <base-input
                    alternative
                    type="email"
                    id="email"
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    placeholder="Email"
                    name="email"
                    :rules="{ required: true, email: true }"
                    v-model="email"
                  >
                  </base-input>
                  <div class="text-center">
                    <b-button type="submit" variant="primary" class="mt-4"
                      >비밀번호 찾기</b-button
                    >
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  <br/><br/><br/>
  </div>
</template>
<script>
import { mapState } from "vuex";
import { findpwd } from "@/api/member";
import emailjs from "emailjs-com";
const memberStore = "memberStore";


export default {
  name: "register",
  data() {
    return {
      username: "",
      userid: "",
      userpwd: "",
      email: "",
      isUserid: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    onSubmit() {
      // this will be called only after form is valid. You can do an api call here to register users
      findpwd(
        {
          userid: this.userid,
          username: this.username,
          email: this.email,
        },

        ({ data }) => {
          let msg = "비밀번호 찾기 도중 문제가 발생했습니다.";
          if (data === "fail") {
            alert('일치하는 회원 정보가 없습니다!');
            // this.userid = "";
            // this.username = "";
            // this.email = "";
          } else {
            msg = "이메일로 비밀번호 전송하였습니다";
            try {
              var formData = this.$refs.form;
              var emailel = document.createElement('input');
              emailel.type="email";
              emailel.name="email";
              emailel.value=this.email;
              var nameel = document.createElement('input');
              nameel.type="name";
              nameel.name="name";
              nameel.value=this.username;
              var pwd = document.createElement('input');
              pwd.type="password";
              pwd.name="password";
              pwd.value=data;
              formData.appendChild(emailel);
              formData.appendChild(nameel);
              formData.appendChild(pwd);

              emailjs.sendForm(
                "HappyHouse",
                "template_92xfmzn",
                formData,
                "user_xXq7X8YXK6mZeeYLZsN6n"
              );
              // console.log(e.target);
            } catch (error) {
              console.log({ error });
            }
            alert(msg);
            this.$router.push({ name: "login" });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // check() {
    //   if (idCheck(this.userid) == 0) return true;
    //   else false;
    // }
  },
};
</script>

<style></style>
