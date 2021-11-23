<template>
  <div>
    <h1>
      <div class="mainbg">
        <br /><br /><br /><br />

        <h1><img src="assets/happyhouse60px.png" /> Happy House</h1>
        <br />
        <div class="text-white">
          <h3>친구들과 지낼 최고의 장소를 함께 찾아보세요!!</h3>
          <h5>Find your best place to spend time with your friends</h5>
        </div>
        <br /><br /><br />
      </div>
    </h1>
    <br /><br /><br />
    <b-card>
      <first />
      <br /><br /><br />
    </b-card>
    <br /><br /><br /><br /><br /><br />
    <b-card class="sc2">
      <br /><br /><br />
      <second />
      <br /><br /><br />
    </b-card>
    <br /><br /><br /><br /><br /><br />
    <b-card class="sc3">
      <br /><br /><br />
      <third />
      <br /><br /><br />
    </b-card>
    <br /><br /><br /><br /><br /><br /><br /><br /><br />
  </div>
</template>

<script>
import Second from "./second.vue";
import First from "./first.vue";
import Third from "./third.vue";

export default {
  name: "Main",
  props: {
    msg: String,
  },
  components: {
    First,
    Second,
    Third,
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}
body,
html {
  height: 100%;
}
.home {
  background-color: lightblue;
}
.sc2 {
  width: 100%;
  content: "";
  background-image: url("/assets/bg-sc2.png");

  top: 0;
  background-position: center;
  background-size: cover;
}
.mainbg {
  height: 5%;
  background-image: url("/assets/bg.jpg");
  background-position: center;
  background-size: cover;
}

.sc3 {
  width: 100%;
  height: 100%;
  content: "";
  background-image: url("/assets/house.png");

  top: 0;
  background-position: center;
  background-size: cover;
}

/* .bg {
  

  height: 800px;

  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
} */
</style>
