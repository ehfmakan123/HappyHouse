<template>
  <b-container class="bv-example-row mt-3 text-center">
    <br /><br /><br /><br />
    <h3 class="underline-orange">
    </h3>
    <b-row>
      <b-col>
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-card bg-variant="info" text-variant="white" header="카카오맵 지도 표시">
      <kakao-map ref="map" />
    </b-card>
    <br />
    <b-row>
      <b-col cols="6" align="left">
        <house-list />
      </b-col>
      <b-col cols="6">
        <house-detail />
      </b-col>
    </b-row>
    <br />
    <br />
  </b-container>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import KakaoMap from "@/components/map/KakaoMap.vue";
import { mapState } from "vuex";

export default {
  name: "House",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
    KakaoMap,
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  created() {
    this.$store.commit("houseStore/SET_HOUSE_LIST", [], { root: true });
    this.$store.commit("houseStore/SET_DETAIL_HOUSE", null, { root: true });
  },
  computed: {
    ...mapState("houseStore", ["houses"]),
  },
  watch: {
    houses(val) {
      if (this.houses.length > 0) {
        console.log("지도 watch 호출");
        this.$refs.map.displayMarker(val);
      }
    },
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}
</style>
