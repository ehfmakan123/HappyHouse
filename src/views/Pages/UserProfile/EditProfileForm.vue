<template>
  <card>
    <b-row align-v="center" slot="header">
      <b-col cols="8">
        <h3 class="mb-0">My profile</h3>
      </b-col>
    </b-row>

    <validation-observer
      v-slot="{ handleSubmit }"
      ref="formValidator"
    >
    <b-form ref="form" @submit.prevent="handleSubmit(updateProfile)">
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
              v-model="userInfo.email"
              name="Email"
              :rules="{ required: true, email: true }"
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
              v-model="userInfo.username"
              name="UserName"
              :rules="{ required: true }"
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
              type="password"
              label="PassWord"
              placeholder="PassWord"
              v-model="userInfo.userpwd"
              name="PassWord"
              :rules="{ required: true, min: 6 }"
            >
            </base-input>
          </b-col>

          <b-col lg="6" >
          </b-col>
        </b-row>
        <br/>
      <!-- <button class="btn btn-info" >Edit profile</button>  -->
      <button  v-if="ismodify" type="submit" class="btn btn-info">Edit profile</button> <!-- ismodify false일때 클릭하면 true 바꿈(modifyform)  -->
      <button v-else @click="changeToModify" class="btn btn-info">Edit profile</button>  <!-- idmodify ture(수정모드) submit! -->
      <hr class="my-4" />
      </div>
    </b-form>
    </validation-observer>
  </card>
</template>

<script>


import { mapState } from "vuex";
const memberStore = "memberStore";
import { modifyMember } from "@/api/member";
export default {
  data() {
    return {
      ismodify : false,
    };
  },
  mounted:{
    ismodify :false 
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    updateProfile() {
      console.log("updateProfile 호출");
      console.log(this.ismodify);
      modifyMember(
        {
          userid: this.userInfo.userid,
          username: this.userInfo.username,
          email: this.userInfo.email,
          userpwd: this.userInfo.userpwd,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
        },
        (error) => {
          console.log(error);
        }
      );
      this.ismodify = false;
    },
    changeToModify() {
      console.log("changeToModify 호출");
      console.log(this.ismodify);
      this.ismodify = true;
    },
  },
}
</script>


<style></style>