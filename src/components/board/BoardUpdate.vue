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
          <stats-card sub-title="게시판"
                      icon="ni ni-bullet-list-67"
                      class="mb-4">
          </stats-card>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <board-write-form type="modify" />
        </b-col>
      </b-row>
     </b-container>
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
