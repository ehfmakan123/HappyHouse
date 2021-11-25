<template>
  <card>
    <b-row align-v="center" slot="header">
      <b-col cols="8">
        <h3 class="mb-0">My profile</h3>
      </b-col>
    </b-row>

    <b-form @submit.prevent="updateProfile">
      <h6 class="heading-small text-muted mb-4">User information</h6>
      <br />
      <!-- 수정 모드가 아닐때 (ismodify = false 모드 )-->
      
      <div class="pl-lg-4"> 
        <b-row>
          <b-col lg="6" > <!-- 모드 상관없이 수정 X -->
            <base-input type="text" label="UserID" 
              ><b-iocn class="ni ni-circle-08" />&nbsp;&nbsp;&nbsp;
              {{ userInfo.userid }}
            </base-input>
          </b-col>
          <b-col lg="6" v-if="!ismodify">  <!-- (ismodify = false 모드 email 보여줌 )-->
            <base-input type="text" label="Email"
              ><b-iocn class="ni ni-email-83" />&nbsp;&nbsp;&nbsp;
              {{ userInfo.email }}
            </base-input>
          </b-col>

          <b-col lg="6" v-if="ismodify"> <!-- 수정 모드일때 (ismodify = true 모드 )-->
            <base-input
              type="Email"
              label="Email"
              placeholder="example@email.com"
              v-model="user.email"
            >
            </base-input>
          </b-col>

        </b-row>

        <b-row>
          <b-col lg="6" v-if="!ismodify"> <!-- (ismodify = false 모드 username 보여줌)-->
            <base-input type="text" label="UserName"
              ><b-iocn class="ni ni-hat-3" />&nbsp;&nbsp;&nbsp;
              {{ userInfo.username }}
            </base-input>
          </b-col>

          <b-col lg="6" v-if="ismodify"> <!-- 수정 모드일때 (ismodify = true 모드 )-->
            <base-input
              type="text"
              label="UserName"
              placeholder="UserName"
              v-model="user.username"
            >
            </base-input>
          </b-col>

          <b-col lg="6"> <!-- 모드 상관없이 수정 X -->
            <base-input type="text" label="Join Date"
              ><b-iocn class="ni ni-time-alarm" />&nbsp;&nbsp;&nbsp;
              {{ userInfo.joindate }}
            </base-input>
          </b-col>

        </b-row>

        <b-row> <!-- 수정 모드일때 (ismodify = true 모드 )-->
          <b-col lg="6" v-if="ismodify">
            <base-input
              type="text"
              label="PassWord"
              placeholder="PassWord"
              v-model="userInfo.userpwd"
            >
            </base-input>
          </b-col>

          <b-col lg="6" >
          </b-col>
        </b-row>
        <br/>
      <button class="btn btn-info">Edit profile</button> 
      <!-- <button @click="modifyform" class="btn btn-info">Edit profile</button>  클릭하면 modifyform 함수를 사용해서 idmodify를 true로  -->
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
        ismodify : true,
        userpwd:"",
      },
    };
  },
  mounted:{
    ismodify :true 
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    updateProfile() {
      alert("Your data: " + JSON.stringify(this.user));
    },
    modifyform(){ //?????
      ismodify = true;
      console.log(this.ismodify);
      this.$router.go();
    }
  },
};
</script>
<style></style>
