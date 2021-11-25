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
      <div class="text-center">
        <div class="h3 font-weight-300">
          <br/><br/>
          <i class="ni location_pin mr-2"></i>이름
        </div>
        <div class="h3 mt-4">
          <i class="ni business_briefcase-24 mr-2"></i>이메일
        </div>
        <div><i class="ni education_hat mr-2"></i>desc</div>
        <hr class="my-4" />
        <p>
          Ryan — the name taken by Melbourne-raised, Brooklyn-based Nick Murphy
          — writes, performs and records all of his own music.
        </p>
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
