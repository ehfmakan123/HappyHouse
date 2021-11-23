<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <div align="left">
        &nbsp;&nbsp;&nbsp;&nbsp;전체 글 수 : {{ totalRows }}
        <!-- articles.length 가 페이지에 나타나는 글수밖에 출력을 못함 -->
      </div>
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <!-- 하위 component인 ListRow에 데이터 전달(props) -->
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              v-bind="article"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>
    <b-pagination
      v-model="currentPage"
      :total-rows="totalRows"
      :per-page="perPage"
      align="center"
      @page-click="pageClick"
    >
    </b-pagination>
  </b-container>
</template>

<script>
import BoardListRow from "@/components/board/child/BoardListRow";
import { listArticle } from "@/api/board.js";

export default {
  name: "BoardList",
  components: {
    BoardListRow,
  },
  data() {
    return {
      articles: [],
      totalRows: 0,
      // 몇개 씩 보여줄지
      perPage: 10,
      // 현재 페이지
      currentPage: 1,
    };
  },
  created() {
    listArticle(
      this.currentPage,
      (response) => {
        console.log(response.data);
        this.totalRows = response.data.count;
        this.articles = response.data.blist;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    pageClick: function (button, page) {
      this.currentPage = page;
      listArticle(
        this.currentPage,
        (response) => {
          console.log(response.data);
          this.totalRows = response.data.count;
          this.articles = response.data.blist;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
