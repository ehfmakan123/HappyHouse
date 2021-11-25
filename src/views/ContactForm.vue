<template>
  <div class="container" align="center">
    <br /><br />
    <br /><br />
    Send mail
    <br />
    <b-card class="t1">
      <form ref="form" @submit.prevent="sendEmail" class="bg">
        <label>ID</label>
        <input
          type="text"
          v-model="name"
          name="name"
          placeholder="Receiving ID"
        />
        <label>Email</label>
        <input type="email" v-model="email" name="email" placeholder="Email" />
        <label>Message</label>
        <textarea
          name="message"
          v-model="message"
          cols="30"
          rows="5"
          placeholder="Message"
        >
        </textarea>

        <input type="submit" value="Send" />
      </form>
    </b-card>
    <br /><br /><br /><br />
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
      try {
        emailjs.sendForm(
          "HappyHouse",
          "template_92xfmzn",
          e.target,
          "user_xXq7X8YXK6mZeeYLZsN6n",
        );
      } catch (error) {
        console.log({ error });
      }
      // Reset form field
      this.name = "";
      this.email = "";
      this.message = "";
    },
  },
};
</script>

<style scope="this api replaced by slot-scope in 2.5.0+">
* {
  box-sizing: border-box;
}
.container {
  background-color: darkseagreen;
}

.t1 {
  display: auto;
  margin-top: 10px;
  margin-bottom: 10px;
}
.bg {
  display: auto;
  margin: auto;
  text-align: center;
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  width: 50%;
}
label {
  float: left;
}
input[type="text"],
[type="email"],
textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}
input[type="submit"] {
  background-color: #4caf50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
input[type="submit"]:hover {
  background-color: #45a049;
}
</style>
