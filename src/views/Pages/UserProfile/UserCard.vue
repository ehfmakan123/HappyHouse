<template>
  <b-card no-body class="card-profile" alt="Image placeholder" img-top>
    <b-row class="justify-content-center">
      <b-col lg="3" class="order-lg-2">
        <div class="card-profile-image">
          <a href="#">
            <b-img src="img/theme/team-4.jpg" rounded="circle" />
          </a>
        </div>
      </b-col>
    </b-row>

    <b-card-header class="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4">
      <div class="d-flex justify-content-between">
        <a href="#" class="btn btn-sm btn-info mr-4">Connect</a>
        <a href="#" class="btn btn-sm btn-default float-right">Message</a>
      </div>
    </b-card-header>

    <b-card-body class="pt-0">
      <b-row>
        <b-col >
          <div class="card-profile-stats d-flex justify-content-center mt-md-5">
            <div>
              <span class="heading">22</span>
              <span class="description">Friends</span>
            </div>
            <div>
              <span class="heading">10</span>
              <span class="description">Photos</span>
            </div>
            <div>
              <span class="heading">89</span>
              <span class="description">Comments</span>
            </div>
          </div>
        </b-col>
      </b-row>
      <div class="text-center">
        <h5 class="h3">
          {{userInfo.userid}}<span class="font-weight-light"></span>
        </h5>
        <div class="h3 font-weight-300">
          <i class="ni location_pin mr-2"></i>{{userInfo.username}}
        </div>
        <div class="h3 mt-4">
          <i class="ni business_briefcase-24 mr-2"></i>{{userInfo.email}}
        </div>
        <div>
          <i class="ni education_hat mr-2"></i>{{userInfo.joindate}}
        </div>
        <hr class="my-4">
        <p>Ryan — the name taken by Melbourne-raised, Brooklyn-based Nick Murphy — writes, performs and records all of his own music.</p>
        <a href="#">Go Top</a>

      </div>
    </b-card-body>
  </b-card>
</template>
<script>
  import { mapState } from "vuex";
  const memberStore = "memberStore";  
  export default {
    computed: {
      ...mapState(memberStore, ["userInfo"]),
    },
  };
</script>
<style></style>
