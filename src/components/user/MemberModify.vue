<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>개인 정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <member-join-form type="modify" />
  </b-container>
</template>

<script>
import MemberJoinForm from "./child/MemberJoinForm.vue";

export default {
  name: "MemberUpdate",
  components: {
    MemberJoinForm,
  },
};
</script>

<style></style>
