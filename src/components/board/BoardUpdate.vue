<template>
  <div id="app">
    <div class="header bg-gradient-info py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white"> 글 수정 페이지 </h1>
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
    <div class="bg2">
      <b-container fluid class="mt--7">
        <b-row> </b-row>
        <b-row>
          <b-col>
            <board-write-form type="modify" />
            <br/><br/><br/><br/>
          </b-col>
        </b-row>
        <!-- <div class="mt-5"></div>
      <dark-table></dark-table> -->
      </b-container>
    </div>
  </div>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardUpdate",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
