<template>
  <div v-show="isShow" class="comment">
    <div class="head">{{ comment.userid }} ({{ comment.commenttime }})</div>
    <div class="content" v-html="enterToBr(comment.comment)"></div>
    <!-- 로그인 기능 구현 후 로그인한 자신의 글에만 보이게 한다. -->
    <div class="cbtn" v-if="this.userInfo.userid === comment.userid">
      <label @click="modifyCommentView">수정</label> |
      <label @click="commentDelete">삭제</label>
    </div>
  </div>
</template>

<script>
//import { writeComment, getComment, modifyComment } from "@/api/comment";
import moment from "moment";
import { mapState } from "vuex";
import { deleteComment } from "@/api/comment";
export default {
  props: {
    comment: Object,
  },
  data() {
    return {
      isShow: true,
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    modifyCommentView() {
      this.$emit("modify-comment", {
        commentno: this.comment.commentno,
        comment: this.comment.comment,
        articleno: this.comment.articleno,
      });
    },
    commentDelete() {
      //if(confirm("삭제하시겠습니까?")){
      deleteComment(
        this.comment.commentno,
        ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$store.dispatch(
            "commentStore/getComments",
            this.comment.articleno,
            {
              root: true,
            }
          );
        },
        (error) => {
          console.log(error);
        }
      );
      //}
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY-MM-DD HH:mm:ss");
    },
    enterToBr(str) {
      // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
      return String(str).replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style scoped>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #d6e7fa;
  padding: 10px 20px;
  margin: 10px;
}
.head {
  font-weight: bold;
  margin-bottom: 5px;
}
.content {
  padding: 5px;
}
.cbtn {
  text-align: right;
  color: steelblue;
  margin: 5px 0px;
}
</style>
