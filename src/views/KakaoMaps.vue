<template>
  <div id="app">
    <base-header class="bg-image pb-6 pb-8 pt-5 pt-md-8">
      <br/><br/>
      <!-- Card stats -->
      <!-- <b-modal id="aptModal" v-model="isShow" size="lg" title="아파트 상세보기">modal content</b-modal> -->
      <b-row class="mt-4 mb-4 text-center">
        <b-col
          class="sm-3 h1"
          style="font-family: fantasy; font-weight: bold; color: white"
        >
          원하는 지역의 매물 정보를 찾아보세요!
        </b-col>
      </b-row>
      <br/><br/>
      <b-row class="mt-4 mb-4 text-center">
        <b-col class="sm-3">
          <b-form-select
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList()"
          ></b-form-select>
        </b-col>
        <b-col class="sm-3">
          <b-form-select
            v-model="gugunCode"
            :options="guguns"
            @change="aptList()"
          ></b-form-select>
        </b-col>
      </b-row>
    </base-header>

    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <div id="map-custom" class="map-canvas" style="height: 600px"></div>
            <!-- <div class="category">
              <ul>
                <li id="coffeeMenu" @click="changeMarker('coffee')">
                  <span class="ico_comm ico_coffee"></span>
                  커피숍
                </li>
                <li id="storeMenu" @click="changeMarker('store')">
                  <span class="ico_comm ico_store"></span>
                  편의점
                </li>
                <li id="carparkMenu" @click="changeMarker('carpark')">
                  <span class="ico_comm ico_carpark"></span>
                  주차장
                </li>
              </ul>
            </div> -->
          </b-card>
        </b-col>
      </b-row>
      <br /><br />

      <b-row>
        <!-- <b-col>
          <stats-card title="Total traffic"
                      type="gradient-red"
                      sub-title="350,897"
                      icon="ni ni-active-40"
                      class="mb-4">

            <template slot="footer">
              <span class="text-success mr-2">3.48%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col> -->
        <b-col><div id="roadview" style="width: 100%; height: 300px"></div></b-col>
        <b-col></b-col>
      </b-row>
    </b-container>

    <!-- <b-container v-if="houses && houses.length != 0" class="bv-example-row mt-3">
      <house-list-row
        v-for="(house, index) in houses"
        :key="index"
        :house="house"
      />row mt-3">
      <b-row>
        <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container> -->
    <!-- </b-container>
    <b-container v-else class="bv-example-row mt-3">
      <b-row>
        <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container> -->
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      map: null,
      center: null,
      level: 3,
      markers: [],
      infowindow: null,
      // sido: null,
      // gugun: null,
      markerImageSrc:
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png", // 마커이미지의 주소입니다. 스프라이트 이미지 입니다
      coffeeMarkers: [], // 커피숍 마커 객체를 가지고 있을 배열입니다
      storeMarkers: [], // 편의점 마커 객체를 가지고 있을 배열입니다
      carparkMarkers: [], // 주차장 마커 객체를 가지고 있을 배열입니다
      // isShow: false,
    };
  },
  computed: {
    ...mapState(houseStore, ["houses", "sidos", "guguns", "sido", "gugun"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    console.log(this.sido, "created sido");
    this.$store.commit("houseStore/SET_HOUSE_LIST", [], { root: true });
    this.$store.commit("houseStore/SET_DETAIL_HOUSE", null, { root: true });
    // this.$store.commit("houseStore/SET_SIDO", null, { root: true });
    this.CLEAR_SIDO_LIST();
    this.getSido();
    console.log(this.sidos, "sidos");
    // this.$store.commit("houseStore/SET_SIDO", null, { root: true });
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap());
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6331aee7aadedb9f97782a69e73678a9&libraries=services";
      console.log("mounted");
      document.head.appendChild(script);
    }
  },
  watch: {
    // sidoCode(val) {
    //   console.log("sido watch 호출");
    //   this.sidoCode = val;
    //   this.gugunList();
    // },
    // gugunCode(val) {
    //   console.log("gugun watch 호출");
    //   this.gugunCode = val;
    //   this.aptList();
    // },
    houses(val) {
      if (this.houses != undefined && this.houses != null && this.houses.length > 0) {
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
      this.$store.commit("houseStore/SET_GUGUN", null, { root: true });
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
      this.getSidoName(this.sidoCode);
      // sidoname(
      //   { sidoCode: this.sidoCode },
      //   ({ data }) => {
      //     console.log(data, "data");
      //     this.$store.commit("houseStore/SET_SIDO", data, { root: true });
      //     this.sido = data;
      //   },
      //   (error) => {
      //     console.log(error);
      //   }
      // );
      // this.sido = val;

      console.log(this.sido, "sido");
    },
    // this.sido(this.sidoCode);
    aptList() {
      console.log("aptList 호출");
      this.$store.commit("houseStore/SET_DETAIL_HOUSE", null, { root: true });
      if (this.gugunCode) {
        this.getHouseList(this.gugunCode);
        this.getGugunName(this.gugunCode);
        // gugunname(
        //   {gugunCode:this.gugunCode},
        //   ({ data }) => {
        //     this.$store.commit("houseStore/SET_GUGUN", data, { root: true });
        //   },
        //   (error) => {
        //     console.log(error);
        //   }
        // );
      }
      console.log(this.houses);
    },
    initMap() {
      console.log("initMap");
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
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

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
              // var content = this.setContent(mark);
              // var infoBox = new kakao.maps.CustomOverlay({
              //   content: content,
              //   position: coords,
              // }); //커스텀 오버레이 생성

              kakao.maps.event.addListener(
                marker,
                "click",
                this.makeOverListener(this.map, marker, mark)
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
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    makeOverListener(map, marker, data) {
      return () => {
        // infowindow.setPosition(marker.getPosition());
        // infowindow.setMap(map);
        // infowindow.open(map, marker);
        this.center = map.getCenter();
        this.level = map.getLevel();
        console.log(this.center, this.level);

        var sum = 0;
        var cnt = 0;
        this.houses.forEach((house) => {
          if (house.address === data.address) {
            sum += parseInt(house.거래금액.replace(',', '').trim());
            cnt++;
          }
        });
        var avg = parseInt(sum / cnt);
        console.log(avg);

        var content =
          '<div class="wrap">' +
          '    <div class="info">' +
        '        <div class="title">' +
        '<span>' + data["아파트"] + '</span><span>' + '<b-button class="title" style="z-index:100;" size="sm"><b-icon icon="heart-fill" font-scale="1" variant="danger"></b-icon></b-button></span>' +
          '        <div class="body">' +
          '            <div class="desc">' +
        data.address +
          "            </div>" +
          '            <div class="desc">' +
        '평균 거래가 : ' + avg + '만원' + 
          "            </div>" +
          '            <div class="desc">' +
        '건축년도 : ' + data.건축년도 + '년' + 
          "            </div>" +
          "        </div>" +
          "    </div>" +
          "</div>";
        // // 커스텀 오버레이가 표시될 위치입니다
        // var position = new kakao.maps.LatLng(33.450701, 126.570667);

        // 커스텀 오버레이를 생성합니다
        var overlay = new kakao.maps.CustomOverlay({
          position: marker.getPosition(),
          map: map,
          content: content,
        });

        overlay.setMap(map);

        kakao.maps.event.addListener(map, "click", function () {
          overlay.setMap(null);
          // map.setCenter(this.center);
          map.setLevel(this.level);
        });
        // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
        function closeOverlay() {
          overlay.setMap(null);
        }

        var rvcontainer = document.getElementById("roadview");
        // var tag = `<b-modal id="aptModal" v-model="isShow" size="lg" title="아파트 상세보기">modal content</b-modal>`;
        var rv = new kakao.maps.Roadview(rvcontainer);
        var rc = new kakao.maps.RoadviewClient();
        var rvResetValue = {};
        rc.getNearestPanoId(marker.getPosition(), 100, (panoId) => {
          rv.setPanoId(panoId, marker.getPosition()); //좌표에 근접한 panoId를 통해 로드뷰를 실행합니다.
          rvResetValue.panoId = panoId;
        });
        // this.isShow = true;
        // console.log(this.isShow, "roadview");
        map.setLevel(3);
        map.setCenter(marker.getPosition());
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
  },
};
</script>

<style>
#map {
  width: 600px;
  height: 600px;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
#map_wrap {
  position: relative;
  overflow: hidden;
}
.category * {
  margin: 0;
  padding: 0;
  color: #000;
}
.category {
  position: absolute;
  overflow: hidden;
  top: 0px;
  left: 0px;
  width: 155px;
  height: 50px;
  z-index: 10;
  border: 1px solid black;
  font-family: "Malgun Gothic", "맑은 고딕", sans-serif;
  font-size: 12px;
  text-align: center;
  background-color: darkseagreen;
}
.category .menu_selected {
  background: #ff5f4a;
  color: #fff;
  border-left: 1px solid #915b2f;
  border-right: 1px solid #915b2f;
  margin: 0 -1px;
}
.category li {
  list-style: none;
  float: left;
  width: 50px;
  height: 45px;
  padding-top: 5px;
  cursor: pointer;
}
.category .ico_comm {
  display: block;
  margin: 0 auto 0px;
  width: 22px;
  height: 25px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png")
    no-repeat;
}
.category .ico_coffee {
  background-position: -10px 0;
}
.category .ico_store {
  background-position: -10px -36px;
}
.category .ico_carpark {
  background-position: -10px -72px;
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
  background-image: url("/assets/house.png");
  position: absolute;
  opacity: 0.7;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: cover;
  background-position: center top;
}
.wrap {
  /* position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left; */
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  /* z-index: 100; */
  width: 286px;
  height: 130px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
/* .wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
} */
.info .title {
  padding: 5px 0 0 10px;
  height: 35px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .body {
  padding: 10px 0 0 0;
  position: relative;
  overflow: hidden;
}
.info .desc {
  /* position: relative;
  height: 75px; */
  font-size: 16px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
