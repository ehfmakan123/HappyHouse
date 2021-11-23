<template>
    <b-card no-body>
        <b-card-header class="border-0">
            <h3 class="mb-0">게시판</h3>
        </b-card-header>

        <el-table class="table-responsive table"
                  header-row-class-name="thead-light"
                  :data="articles">
            <el-table-column label="글번호"
                             min-width="140px"
                             prop="articleno">
            </el-table-column>
            <el-table-column label="제목"
                             prop="subject"
                             min-width="310px">
            </el-table-column>

            <el-table-column label="조회수"
                             min-width="170px"
                             prop="hit">
            </el-table-column>

            <el-table-column label="작성자" min-width="190px" prop="userid">
            </el-table-column>

            <el-table-column label="작성일"
                             prop="regtime"
                             min-width="240px">
            </el-table-column>
        </el-table>

        <b-card-footer align="center" class="py-4 d-flex justify-content-end">
            <b-pagination
            v-model="currentPage"
            :total-rows="totalRows"
            :per-page="perPage"
            align="center"
            @page-click="pageClick"
            >
            </b-pagination>
        </b-card-footer>
    </b-card>
</template>
<script>
  import { Table, TableColumn} from 'element-ui'
import { listArticle } from "@/api/board.js";
  export default {
    name: 'light-table',
    components: {
      [Table.name]: Table,
      [TableColumn.name]: TableColumn
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
  }
</script>
