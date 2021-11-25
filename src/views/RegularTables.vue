<template>
  <div id="app">
    <div class="bg-image pb-6 pb-8 pt-5 pt-md-8">
      <!-- Card stats -->
    </div>
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <stats-card sub-title="게시판"
                      icon="ni ni-bullet-list-67"
                      class="mb-4">
          </stats-card>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <light-table/>
        </b-col>
      </b-row>
      <!-- <div class="mt-5"></div>
      <dark-table></dark-table> -->
    </b-container>
  </div>
</template>
<script>
  import { Dropdown, DropdownItem, DropdownMenu, Table, TableColumn } from 'element-ui';
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
      [TableColumn.name]: TableColumn
    },
    data() {
      return {
      };
    }
  };
</script>
<style>
.el-table tr :hover {
  cursor:pointer;
  /* background-color: lightblue; */
}
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
  background-image: url("/assets/bg2.jpg");
  min-height: 500px;
  position: absolute;
  opacity: 0.7;
  top: 0;
  left: 0;
  z-index: -1;
  /* min-height: 500px; */
  background-size: cover; 
  background-position: center top;
}

</style>