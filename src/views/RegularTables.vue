<template>
  <div>
    <base-header class="bg-image pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
    </base-header>
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
  import projects from './Tables/projects'
  import users from './Tables/users'
  import LightTable from "./Tables/RegularTables/LightTable";
  import DarkTable from "./Tables/RegularTables/DarkTable";

  export default {
    components: {
      LightTable,
      DarkTable,
      [Dropdown.name]: Dropdown,
      [DropdownItem.name]: DropdownItem,
      [DropdownMenu.name]: DropdownMenu,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn
    },
    data() {
      return {
        projects,
        users
      };
    }
  };
</script>
<style>
.el-table.table-dark{
  background-color: #172b4d;
  color: #f8f9fe;
}

.el-table.table-dark th,
.el-table.table-dark tr{
  background-color: #172b4d;
}

.el-table.table-dark td,
.el-table.table-dark th.is-leaf{
  border-bottom: none;
}
.bg-image {
  width: 100%;
  height: 100%;
  text-align: center;
  position: relative;
  z-index: -1;
}

.bg-image::after {
  width: 100%;
  height: 100%;
  content: "";
  background-image: url("/assets/house.png");
  position: absolute;
  opacity: 0.7;
  top: 0;
  left: 0;
  z-index: -1;
  min-height: 500px;
  background-size: cover; 
  background-position: center top;
}

</style>
