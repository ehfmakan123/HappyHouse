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
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <large>Send Mail</large>
              </div>
              <validation-observer ref="formValidator">
                <b-form ref="form" @submit.prevent="sendEmail">
                  <label>ID</label>
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-circle-08"
                    type="text"
                    v-model="name"
                    name="name"
                    placeholder="Receiving ID"
                  />
                  <label>Email</label>
                  <base-input
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    type="email"
                    v-model="email"
                    name="email"
                    placeholder="Email"
                  />
                  <label>Message</label>
                  <base-input
                    name="message"
                    type="textarea"
                    v-model="message"
                    prepend-icon="ni ni-collection"
                    cols="30"
                    rows="5"
                    placeholder="Message"
                  />
                  <br /><br />
                  <b-button type="submit" variant="info" class="mt-4"
                    >Send</b-button
                  >
                  <br />
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>

          <br /><br /><br />
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
            "HappyHouse2",
            "template_krtb7gd",
            e.target,
            "user_Lgc823DTswKIAXETkb7HJ"
          );
          console.log(e.target);
          alert("메일이 발송 되었습니다.");
        } catch (error) {
          console.log({ error });
        }
        // Reset form field
        this.name = "";
        this.email = "";
        this.message = "";
      }
    },
  },
};
</script>

<style></style>
