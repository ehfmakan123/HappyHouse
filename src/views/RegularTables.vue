<template>
  <div id="app">
    <div class="header bg-gradient-warning py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white"> 게 시 판 </h1>
              <!-- <p class="text-lead text-white">Use these awesome forms to login or create new account in your project for
                free.</p> -->
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg
          x="0"
          y="0"
          viewBox="0 0 2560 100"
          preserveAspectRatio="none"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <polygon
            class="fill-default"
            points="2560 0 2560 100 0 100"
          ></polygon>
        </svg>
      </div>
    </div>
    <div class="bg2">
      <b-container fluid class="mt--7">
        <b-row> </b-row>
        <b-row>
          <b-col>
            <light-table />
            <br/><br/><br/><br/>
          </b-col>
        </b-row>
        <!-- <div class="mt-5"></div>
      <dark-table></dark-table> -->
      </b-container>
    </div>
  </div>
</template>
<script>
import {
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Table,
  TableColumn,
} from "element-ui";
import LightTable from "./Tables/RegularTables/LightTable";
// import DarkTable from "./Tables/RegularTables/DarkTable";

export default {
  components: {
    LightTable,
    // DarkTable,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {};
  },
};
</script>
<style scoped>
.el-table tr :hover {
  cursor: pointer;
  /* background-color: lightblue; */
}
/* 
.bg-image {
  width: 100%;
  height: 100%;
  text-align: center;
  position: relative;
  z-index: 0;
}

.bg-image::after {
  width: 100%;
  height: 100%;
  content: "";
  background-image: url(/assets/bg2.PNG);
  min-height: 500px;
  position: absolute;
  opacity: 0.9;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: cover;
  background-position: center top;
} */

.bg2{
  background-color: rgb(25, 22, 46);
}
</style>
