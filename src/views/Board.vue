<template>
<div id="app">
    <div class="bg-image pb-6 pb-8 pt-5 pt-md-8">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
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
     <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <router-view/>
        </b-col>
      </b-row>
     </b-container>
    </div>
</template>
<script>
export default {
  name: "Board",
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>
<style scoped>
.el-table tr :hover {
  cursor: pointer;
  background-color: lightblue;
}

.bg-image {
  width: 100%;
  height: 100%;
  text-align: center;
  position: relative;
  z-index: 0;
}

.bg-image::after {
  width: 100%;
  height: 100%;
  content: "";
  background-image: url(/assets/bg2.PNG);
  min-height: 500px;
  position: absolute;
  opacity: 0.9;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: cover;
  background-position: center top;
}
</style>
