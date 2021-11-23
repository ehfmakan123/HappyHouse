<template>
  <b-tr>
    <!-- <b-th class="text-left">
      <router-link :to="{ name: 'MemberView', params: { userid: userid } }">{{
        userid
      }}</router-link>
    </b-th> -->
    <b-td>{{ userid }}</b-td>
    <b-td>{{ username }}</b-td>
    <b-td>{{ email }}</b-td>
    <b-td>{{ joindate }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "MemberListRow",
  props: {
    userid: String,
    username: String,
    email: String,
    joindate: String,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style></style>
