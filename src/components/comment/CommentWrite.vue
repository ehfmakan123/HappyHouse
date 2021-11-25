<template>
  <div class="regist">
    <div v-if="modifyComment != null" class="regist_form">
      <textarea
        name="comment"
        id="comment"
        v-model="modifyComment.comment"
        cols="35"
        rows="2"
      ></textarea>
      <button class="small" @click="updateCommentCancel">취소</button>
      <button class="small" @click="updateComment">수정</button>
    </div>
    <div v-else class="regist_form">
      <textarea
        name="comment"
        id="comment"
        v-model="comment"
        cols="35"
        rows="2"
      ></textarea>
      <button type="button" class="btn btn-success" @click="registComment">등록</button>
    </div>
  </div>
</template>

<script>
import { writeComment, modifyComment } from "@/api/comment";
import { mapState } from "vuex";
// import memberStore from "@/store/modules/memberStore";
export default {
  props: {
    articleno: Number,
    modifyComment: Object,
  },
  data() {
    return {
      // 차후 작성자 이름은 로그인 구현후 로그인한 사용자로 바꾼다.
      comment: "",
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    registComment() {
      writeComment(
        {
          userid: this.userInfo.userid,
          comment: this.comment,
          articleno: this.articleno,
        },
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.comment = "";
          this.$store.dispatch("commentStore/getComments", this.articleno, {
            root: true,
          });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateComment() {
      modifyComment(
        this.modifyComment,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$store.dispatch(
            "commentStore/getComments",
            this.modifyComment.articleno,
            {
              root: true,
            }
          );
          this.updateCommentCancel();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateCommentCancel() {
      this.$emit("modify-comment-cancel", false);
    },
  },
};
</script>

<style>
textarea {
  width: 90%;
  font-size: large;
}
button {
  float: right;
}
button.small {
  width: 60px;
  font-size: small;
  font-weight: bold;
}
</style>
