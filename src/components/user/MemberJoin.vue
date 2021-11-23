<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <member-join-form type="register" />
  </b-container>
</template>

<script>
import MemberJoinForm from "./child/MemberJoinForm.vue";

export default {
  name: "MemberJoin",
  components: {
    MemberJoinForm,
  },
};
</script>

<style></style>
