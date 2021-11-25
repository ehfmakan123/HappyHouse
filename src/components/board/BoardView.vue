<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <div align="left">
        <b-col>
          <b-button variant="outline-primary" @click="listArticle"
            >목록</b-button
          >
        </b-col>
      </div>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          v-if="this.userInfo.userid === article.userid"
          >글수정</b-button
        >
        <b-button
          variant="outline-danger"
          size="sm"
          @click="removeArticle"
          v-if="this.userInfo.userid === article.userid"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.articleno}.
          ${article.subject} [${comments.length}]</h3>            
          <div><h6>${article.userid}<br><br>
             <div><h6>조회수:${article.hit}</div><br>
             <div>${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <br /><br />
    <comment-write :articleno="article.articleno" />
    <comment-write
      v-if="isModifyShow && modifyComment != null"
      :modifyComment="modifyComment"
      @modify-comment-cancel="onModifyCommentCancel"
    />
    <comment-list
      v-for="(comment, index) in comments"
      :key="index"
      :comment="comment"
      @modify-comment="onModifyComment"
    />
  </b-container>
</template>

<script>
// import moment from "moment";
import { mapState } from "vuex";
import { getArticle, deleteArticle } from "@/api/board";
// import { getComment, deleteComment } from "@/api/comment";
import CommentWrite from "../comment/CommentWrite.vue";
import CommentList from "../comment/CommentList.vue";

export default {
  components: { CommentWrite, CommentList },
  data() {
    return {
      article: Object,
      isModifyShow: false,
      modifyComment: Object,
    };
  },
  computed: {
    ...mapState("commentStore", ["comments"]),
    ...mapState("memberStore", ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },

    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
  },
  created() {
    getArticle(
      this.$route.params.articleno,
      (response) => {
        console.log(response.data);
        this.article = response.data;
        this.$store.dispatch(
          "commentStore/getComments",
          this.article.articleno,
          {
            root: true,
          }
        );
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "tables" });
    },
    // listComment() {
    //   this.$router.push({ name: "CommentList" });
    // },
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardUpdate",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    removeArticle() {
      if (confirm("정말로 삭제?")) {
        deleteArticle(this.article.articleno, () => {
          this.$router.push({ name: "BoardList" });
        });
      }
    },
    // removeComment() {
    //   if (confirm("정말로 삭제?")) {
    //     deleteComment(this.comment.commentno, () => {
    //       this.$router.push({ name: "CommentList" });
    //     });
    //   }
    // },
    onModifyComment(comment) {
      this.isModifyShow = true;
      this.modifyComment = comment;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
};
</script>

<style></style>
