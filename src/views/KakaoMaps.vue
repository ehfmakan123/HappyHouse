<template>
  <div id="app">
    
      <base-header class="bg-image pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
        <!-- Card stats -->

        <b-row class="mt-4 mb-4 text-center">
          <b-col class="sm-3 h1 text-black"> 원하는 지역의 매물 정보를 찾아보세요! </b-col>
        </b-row>
        <b-row class="mt-4 mb-4 text-center">
          <!-- <b-col class="sm-3">
            <b-form-input
              v-model.trim="dongCode"
              placeholder="동코드 입력...(예 : 11110)"
              @keypress.enter="sendKeyword"
            ></b-form-input>
          </b-col>
          <b-col class="sm-3" align="left">
            <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
          </b-col> -->
          <b-col class="sm-3">
            <b-form-select
              v-model="sidoCode"
              :options="sidos"
            ></b-form-select>
          </b-col>
          <b-col class="sm-3">
            <b-form-select
              v-model="gugunCode"
              :options="guguns"
            ></b-form-select>
          </b-col>
        </b-row>
      </base-header>

    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <div id="map-custom" class="map-canvas" style="height: 600px"></div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { sidoname, gugunname } from "@/api/house";
const houseStore = "houseStore";

export default {
  name: "maps",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      map: null,
      markers: [],
      infowindow: null,
      // sido: null,
      // gugun: null,
      markerImageSrc:
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png", // 마커이미지의 주소입니다. 스프라이트 이미지 입니다
      coffeeMarkers: [], // 커피숍 마커 객체를 가지고 있을 배열입니다
      storeMarkers: [], // 편의점 마커 객체를 가지고 있을 배열입니다
      carparkMarkers: [], // 주차장 마커 객체를 가지고 있을 배열입니다
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns"]),
    ...mapState(houseStore, ["sido"]),
    ...mapState(houseStore, ["gugun"]),
    ...mapState(houseStore, ["houses"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.$store.commit("houseStore/SET_HOUSE_LIST", [], { root: true });
    this.$store.commit("houseStore/SET_DETAIL_HOUSE", null, { root: true });
    // this.$store.commit("houseStore/SET_SIDO", null, { root: true });
    this.getSido();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    }
    else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6331aee7aadedb9f97782a69e73678a9&libraries=services";
      console.log("mounted");
      document.head.appendChild(script);
    }
  },
  watch: {
    sidoCode(val) {
      console.log("sido watch 호출");
      this.sidoCode = val;
      this.gugunList();
    },
    gugunCode(val) {
      console.log("gugun watch 호출");
      this.gugunCode = val;
      this.aptList();
    },
    houses(val) {
      if (this.houses.length > 0) {
        console.log("지도 watch 호출");
        this.displayMarker(val);
      }
    },
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getSidoName",
      "getGugunName",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      console.log("gugunList 호출");
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      // this.$store.commit("houseStore/SET_GUGUN", null, { root: true });
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
      console.log(this.sido);
      sidoname(
        {sidoCode:this.sidoCode},
        ({ data }) => {
          console.log(data);
          this.$store.commit("houseStore/SET_SIDO", data, { root: true });
          // this.sido = data;
        },
        (error) => {
          console.log(error);
        }
      );
      console.log(this.sido);
    },
    // this.sido(this.sidoCode);
  },
  // dongList() {
  //   // console.log(this.sidoCode);
  //   if (this.gugunCode) this.getHouseList(this.gugunCode);
  //   this.CLEAR_DONG_LIST();
  //   this.dongCode = null;
  //   if (this.gugunCode) this.getDong(this.gugunCode);
  // },
  aptList() {
    console.log("aptList 호출");
    this.$store.commit("houseStore/SET_DETAIL_HOUSE", null, { root: true });
    if (this.gugunCode) {
      this.getHouseList(this.gugunCode);
      // this.gugun(this.gugunCode);
      gugunname(
        {gugunCode:this.gugunCode},
        ({ data }) => {
          this.$store.commit("houseStore/SET_GUGUN", data, { root: true });
        },
        (error) => {
          console.log(error);
        }
      );
    }
    console.log(this.houses);
  },
  initMap() {
    const container = document.getElementById("map-custom");
    // container.style.width = `90%`;
    // container.style.height = `500px`;
    const options = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567),
      level: 3,
    };
    this.map = new kakao.maps.Map(container, options);
    const mapTypeControl = new kakao.maps.MapTypeControl();
    this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    const zoomControl = new kakao.maps.ZoomControl();
    this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
  },
  zoomIn() {
    const map = this.map;
    map.setLevel(map.getLevel() - 1);
  },
  zoomOut() {
    const map = this.map;
    map.setLevel(map.getLevel() + 1);
  },
  changeSize(size) {
    const container = document.getElementById("map");
    container.style.width = `${size * 2}px`;
    container.style.height = `${size}px`;
    this.map.relayout();
  },
  displayMarker(markerPositions) {
    // 주소-좌표 변환 객체를 생성합니다
    // console.log(this.map);
    this.markers = [];
    this.initMap();
    console.log("displayMarker 호출");
    console.log(this.gugun);

    if (markerPositions.length > 0) {
      // 마커 이미지의 이미지 주소입니다
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      var geocoder = new kakao.maps.services.Geocoder();
      var bounds = new kakao.maps.LatLngBounds();

      markerPositions.forEach((mark) => {
        mark.address =
          this.sido +
          " " +
          this.gugun +
          " " +
          mark["법정동"].trim() +
          " " +
          mark["지번"];
        // var apt = mark["아파트"];
        // console.log(apt);
        var coords;

        geocoder.addressSearch(mark.address, (result, status) => {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            // console.log("result: " + result[0]);
            coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            // mark.coords = coords;
            // console.log(coords);
            bounds.extend(coords);

            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(24, 35);

            // 마커 이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

            // console.log(i + " : " + mark["아파트"]);
            // 결과값으로 받은 위치를 마커로 표시합니다
            // console.log(coords);
            var marker = new kakao.maps.Marker({
              map: this.map,
              position: coords,
              title: mark["아파트"], // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
              image: markerImage, // 마커 이미지
              clickable: true,
            });
            this.markers.push(marker);
            // marker.setMap(this.map);

            // var iwContent = mark["아파트"], // 인포윈도우에 표시할 내용
            //   iwRemoveable = true;

            // 인포윈도우를 생성합니다
            // var infowindow = new kakao.maps.InfoWindow({
            //   content: iwContent,
            //   removable: iwRemoveable,
            // });
            var content = this.setContent(mark);
            var infoBox = new kakao.maps.CustomOverlay({
              content: content,
              position: coords,
            }); //커스텀 오버레이 생성

            kakao.maps.event.addListener(
              marker,
              "click",
              this.makeOverListener(this.map, marker, infoBox)
            );
            // kakao.maps.event.addListener(
            //   marker,
            //   "mouseout",
            //   this.makeOutListener(infowindow)
            // );
          }
        });
      });
      setTimeout(this.set, 1000, bounds);
      console.log(this.markers);
    }
  },
  setContent(data) {
    var content =
      '<div class="wrap">' +
      '    <div class="info">' +
      '        <div class="title">' +
      data["아파트"] +
      '            <div class="close" @click="closeOverlay()" title="닫기"></div>' +
      "        </div>" +
      '        <div class="body">' +
      '            <div class="img">' +
      '                <img src="https://cfile181.uf.daum.net/image/250649365602043421936D" width="73" height="70">' +
      "           </div>" +
      '            <div class="desc">' +
      '                <div class="ellipsis">' +
      data.address +
      "</div>" +
      '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
      '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
      "            </div>" +
      "        </div>" +
      "    </div>" +
      "</div>";
    return content;
  },

  // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
  makeOverListener(map, marker, infowindow) {
    return function () {
      infowindow.setPosition(marker.getPosition());
      infowindow.setMap(map);
      // infowindow.open(map, marker);
    };
  },

  // 인포윈도우를 닫는 클로저를 만드는 함수입니다
  makeOutListener(infowindow) {
    return function () {
      infowindow.close();
    };
  },
  set(bounds) {
    this.map.setBounds(bounds);
  },
  displayInfoWindow() {
    if (this.infowindow && this.infowindow.getMap()) {
      //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
      this.map.setCenter(this.infowindow.getPosition());
      return;
    }

    var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
      iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

    this.infowindow = new kakao.maps.InfoWindow({
      map: this.map, // 인포윈도우가 표시될 지도
      position: iwPosition,
      content: iwContent,
      removable: iwRemoveable,
    });

    this.map.panTo(iwPosition);
  },
};
</script>
<style scoped>
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
  background-size: cover; 
  background-position: center top;
}

</style>