<template>
  <div i1="app">
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Send mail (Admin)</h1>
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

    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8">
          <b-card no-body class="bg-primary border-3">
          <div class="text-center"></div>
            <div class="container" align="center">
              <br />
              <div class="text-white">
              Send mail
              </div>
              <br />
              <b-card class="t1">
                <b-form ref="form" @submit.prevent="sendEmail" class="bg">
                  <label>ID</label>
                  <input
                    class="mb-3"
                    prepend-icon="ni ni-circle-08"
                    type="text"
                    v-model="name"
                    name="name"
                    placeholder="Receiving ID"
                  />
                  <label>Email</label>
                  <input
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    type="email"
                    v-model="email"
                    name="email"
                    placeholder="Email"
                  />
                  <label>Message</label>
                  <textarea
                    name="message"
                    v-model="message"
                    cols="30"
                    rows="5"
                    placeholder="Message"
                  >
                  </textarea>
                  <br /><br />
                   <b-button type="submit" variant="info" class="mt-4"
                      >Send</b-button
                    >
                  <br />
                </b-form>
              </b-card>
            </div>
            <br /><br /><br />
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import emailjs from "emailjs-com";

export default {
  name: "subscribe",
  data() {
    return {
      name: "",
      email: "",
      message: "",
    };
  },

  methods: {
    sendEmail(e) {
      if (confirm("메일을 발송 하시겠습니까?")) {
      try {
        emailjs.sendForm(
          "HappyHouse",
          "template_92xfmzn",
          e.target,
          "user_xXq7X8YXK6mZeeYLZsN6n"
        );
        console.log(e.target);
        alert("메일이 발송 되었습니다.")
      } catch (error) {
        console.log({ error });
      }
      // Reset form field
      this.name = "";
      this.email = "";
      this.message = "";
      }
    },
  }
};
</script>

<style scope="this api replaced by slot-scope in 2.5.0+">

</style>
