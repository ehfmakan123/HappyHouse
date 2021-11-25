<template>
  <card>
    <b-row align-v="center" slot="header">
      <b-col cols="8">
        <h3 class="mb-0">My profile</h3>
      </b-col>
    </b-row>

    <b-form @submit.prevent="updateProfile">
      <h6 class="heading-small text-muted mb-4">User information</h6>

      <div class="pl-lg-4">
        <b-row>
          <b-col lg="6">
            <base-input type="text" label="UserID" aria-disabled
              ><b-iocn class="ni ni-circle-08" />&nbsp;&nbsp;&nbsp; {{ userInfo.userid }}
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input type="text" label="Email" aria-disabled
              ><b-iocn class="ni ni-email-83" />&nbsp;&nbsp;&nbsp; {{ userInfo.email }}
            </base-input>
          </b-col>
        </b-row>
        <b-row>
          <b-col lg="6">
            <base-input type="text" label="UserName" aria-disabled
              ><b-iocn class="ni ni-hat-3" />&nbsp;&nbsp;&nbsp; {{ userInfo.username }}
            </base-input>
          </b-col>
         <b-col lg="6">
            <base-input type="text" label="Join Date" aria-disabled
              ><b-iocn class="ni ni-time-alarm" />&nbsp;&nbsp;&nbsp; {{ userInfo.joindate }}
            </base-input>
          </b-col>
        </b-row>
      </div>
      <hr class="my-4" />
    </b-form>
  </card>
</template>
<script>
import { mapState } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      user: {
        company: "Creative Code Inc.",
        userid: "",
        email: "",
        username: "",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    updateProfile() {
      alert("Your data: " + JSON.stringify(this.user));
    },
  },
};
</script>
<style></style>
