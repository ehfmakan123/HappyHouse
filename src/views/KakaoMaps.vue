<template>
  <div id="app">
    <base-header class="bg-image pb-6 pb-8 pt-5 pt-md-8">
      <br /><br />
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
      <br /><br />
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
          <!-- <b-card no-body class="border-0">
            <div id="map-custom" class="map-canvas" style="height: 600px"></div>
          </b-card> -->

          <div class="map_wrap">
            <div
              id="map"
              style="
                width: 100%;
                height: 100%;
                position: relative;
                overflow: hidden;
              "
            ></div>
            <!-- <ul id="category">
              <li id="BK9" data-order="0">
                <span class="category_bg bank"></span>
                은행
              </li>
              <li id="MT1" data-order="1">
                <span class="category_bg mart"></span>
                마트
              </li>
              <li id="PM9" data-order="2">
                <span class="category_bg pharmacy"></span>
                약국
              </li>
              <li id="OL7" data-order="3">
                <span class="category_bg oil"></span>
                주유소
              </li>
              <li id="CE7" data-order="4">
                <span class="category_bg cafe"></span>
                카페
              </li>
              <li id="CS2" data-order="5">
                <span class="category_bg store"></span>
                편의점
              </li>
            </ul> -->
          </div>
        </b-col>
      </b-row>
      <br /><br />

      <b-row>
        <b-col
          ><div id="roadview" style="width: 100%; height: 480px"></div
        ></b-col>
        <b-col xl="5" v-if="house != null">
          <card type="default" header-classes="bg-transparent">
            <b-row align-v="center" slot="header">
              <b-col>
                <h5 class="text-light text-uppercase ls-1 mb-1">
                  거래가 변화 추이
                </h5>
                <h5 id="aptname" class="h3 text-white mb-0">
                  {{ house.아파트 }}<b-button size="sm" @click="clickLike"><b-icon :icon="heart" font-scale="1" variant="danger"></b-icon></b-button>
                </h5>
              </b-col>
            </b-row>
            <line-chart
              :height="350"
              ref="bigChart"
              :chart-data="bigLineChart.chartData"
              :extra-options="bigLineChart.extraOptions"
            >
            </line-chart>
          </card>
        </b-col>
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
// Charts
import * as chartConfigs from "@/components/Charts/config";
import LineChart from "@/components/Charts/LineChart";
import BarChart from "@/components/Charts/BarChart";
import { mapState, mapActions, mapMutations } from "vuex";
import { listLike, insertLike, deleteLike, aptLikeCheck } from "@/api/member";
const houseStore = "houseStore";

export default {
  components: {
    LineChart,
    BarChart,
  },
  data() {
    return {
      bigLineChart: {
        activeIndex: 0,
        chartData: {
          datasets: [
            {
              label: "거래 금액 (만원 단위)",
              data: [],
            },
          ],
          labels: [],
        },
        extraOptions: chartConfigs.blueChartOptions,
      },
      housechart: [],
      sidoCode: null,
      gugunCode: null,
      house: null,
      map: null,
      center: null,
      level: 3,
      placeOverlay: null,
      contentNode: null,
      markers: [],
      nowmark: null,
      catemarkers: [],
      currCategory: "", // 현재 선택된 카테고리를 가지고 있을 변수입니다
      ps: null,
      infowindow: null,
      heart: "heart",
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
    ...mapState("memberStore", ["userInfo"]),
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
    this.CLEAR_GUGUN_LIST();
    this.getSido();
    console.log(this.sidos, "sidos");
    // this.$store.commit("houseStore/SET_SIDO", null, { root: true });
  },
  mounted() {
    // this.initBigChart(0);
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
      if (
        this.houses != undefined &&
        this.houses != null &&
        this.houses.length > 0
      ) {
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
      const container = document.getElementById("map");
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

      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      this.catemarkers = []; // 마커를 담을 배열입니다
      this.currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다
      // var overlay = new kakao.maps.CustomOverlay({
      //     position: marker.getPosition(),
      //     map: map,
      //     content: content,
      //     clickable: true,
      //   });

      // 장소 검색 객체를 생성합니다
      this.ps = new kakao.maps.services.Places(this.map);

      // 지도에 idle 이벤트를 등록합니다
      // kakao.maps.event.addListener(this.map, "idle", this.searchPlaces());

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(
        this.contentNode,
        "mousedown",
        kakao.maps.event.preventMap
      );
      this.addEventHandle(
        this.contentNode,
        "touchstart",
        kakao.maps.event.preventMap
      );

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },

    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document
        .getElementById(this.currCategory)
        .getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = addMarker(
          new kakao.maps.LatLng(places[i].y, places[i].x),
          order
        );

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, "click", () => {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png"; // 마커 이미지 url, 스프라이트 이미지를 씁니다
      var imageSize = new kakao.maps.Size(27, 28); // 마커 이미지의 크기
      var imgOptions = {
        spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
        spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      };
      var markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imgOptions
      );
      var marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.catemarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.catemarkers.length; i++) {
        this.catemarkers[i].setMap(null);
      }
      this.catemarkers = [];
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },

    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      var category = document.getElementById("category");
      var children = category.children;

      for (var i = 0; i < children.length; i++) {
        children[i].onclick = this.onClickCategory;
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory() {
      var id = this.id;
      var className = this.className;

      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(this);
        this.searchPlaces();
      }
    },

    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = document.getElementById("category");
      var children = category.children;
      var i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
    },
    displayMarker(markerPositions) {
      // 주소-좌표 변환 객체를 생성합니다
      // console.log(this.map);
      this.markers = [];
      // this.initMap();
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
              mark.lat = result[0].y;
              mark.lng = result[0].x;
              // console.log('lat:'+mark.lat);
              // console.log('lng:'+mark.lng);
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
    // 마커 클릭 시 실행되는 함수(오버레이, 로드뷰, 차트 띄움)
    makeOverListener(map, marker, data) {
      return () => {
        this.center = map.getCenter();
        this.level = map.getLevel();
        this.house = data;
        console.log(this.house,'house');
        this.housechart = [];
        this.bigLineChart.chartData = {
          datasets: [
            {
              label: "거래 금액 (만원 단위)",
              data: [],
            },
          ],
          labels: [],
        };
        console.log(this.center, this.level);

        aptLikeCheck({
            userid: this.userInfo.userid,
            aptaddress: this.house.address,
          },
          ({ data }) => {
            if (data === 1) {
              this.heart = "heart-fill";
            } else {
              this.heart = "heart";
            }
          },
          (error) => {
            console.log(error);
          }
        );


        var sum = 0;
        var size = 0;
        this.houses.forEach((house) => {
          if (house.address === data.address) {
            var price = parseInt(house.거래금액.replace(",", "").trim());
            this.housechart.push([house.일, price]);
            sum += price;
            size += house.전용면적;
          }
        });
        var avg = parseInt(sum / this.housechart.length);
        console.log(avg);

        this.housechart.sort((a, b) => {
          a[0] < b[0] ? -1 : a[0] > b[0] ? 1 : 0;
        });

        console.log(this.housechart);

        this.housechart.forEach((val) => {
          this.bigLineChart.chartData.labels.push(val[0] + "일");
          this.bigLineChart.chartData.datasets[0].data.push(val[1]);
        });
        console.log(data.address);
        var content =
          '<div class="placeinfo_wrap"><div class="placeinfo">' +
          '   <a class="title" >' +
          data.아파트 +
          "</a>" +
          +"    <span>" +
          data.address +
          "</span>" +
          "    <span>" +
          "평균 거래가 : " +
          avg +
          " 만원" +
          "</span>" +
          "    <span>" +
          "건축년도 : " +
          data.건축년도 +
          " 년" +
          "</span>" +
          "    <span>" +
          "평균 전용면적 : " +
          size / this.housechart.length +
          " m2" +
          "</span>" +
          "    <span>" +
          "평균 평수 : " +
          (size * 0.3025) / this.housechart.length +
          " 평" +
          "</span>" +
          "</div>" +
          '<div class="after"></div></div>';

        // 커스텀 오버레이를 생성합니다
        var overlay = new kakao.maps.CustomOverlay({
          position: marker.getPosition(),
          map: map,
          content: content,
          clickable: true,
        });

        overlay.setMap(map);

        kakao.maps.event.addListener(map, "click", () => {
          overlay.setMap(null);
          // map.setCenter(this.center);
          map.setLevel(this.level);
        });

        var rvcontainer = document.getElementById("roadview");
        // var tag = `<b-modal id="aptModal" v-model="isShow" size="lg" title="아파트 상세보기">modal content</b-modal>`;
        var rv = new kakao.maps.Roadview(rvcontainer);
        var rc = new kakao.maps.RoadviewClient();
        var rvResetValue = {};
        rc.getNearestPanoId(marker.getPosition(), 100, (panoId) => {
          if (panoId != null) {
            rv.setPanoId(panoId, marker.getPosition()); //좌표에 근접한 panoId를 통해 로드뷰를 실행합니다.
            rvResetValue.panoId = panoId;
          } else {
            alert("주변에 확인 가능한 로드뷰 카메라가 없습니다.");
          }
        });
        // this.isShow = true;
        // console.log(this.isShow, "roadview");
        map.setLevel(3);
        map.setCenter(marker.getPosition());
      };
    },
    clickLike() {
      if (this.heart == "heart") {
        if (confirm("해당 아파트를 관심목록으로 등록하시겠습니까?")) {
          insertLike(
            {
              userid: this.userInfo.userid,
              aptname: this.house.아파트,
              aptaddress: this.house.address,
              lat: parseFloat(this.house.lat),
              lng: parseFloat(this.house.lng),
            },
            ({ data }) => {
              let msg = "관심 등록 중 문제가 발생했습니다.";
              if (data === 1) {
                msg = "등록이 완료되었습니다.";
                this.heart = "heart-fill";
              }
              alert(msg);
            },
            (error) => {
              console.log(error);
            }
          );
        }
      } else {
        if (confirm("해당 아파트를 관심목록에서 삭제하시겠습니까?")) {
          deleteLike(
            {
              userid: this.userInfo.userid,
              aptaddress: this.house.address,
            },
            ({ data }) => {
              let msg = "관심 삭제 중 문제가 발생했습니다.";
              if (data === 1) {
                msg = "삭제가 완료되었습니다.";
                this.heart = "heart";
              }
              alert(msg);
            },
            (error) => {
              console.log(error);
            }
          );
        }
      }
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
    // initBigChart(index) {
    //   let chartData = {
    //     datasets: [
    //       {
    //         label: 'Performance',
    //         data: this.bigLineChart.allData[index]
    //       }
    //     ],
    //     labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //   };
    //   this.bigLineChart.chartData = chartData;
    //   this.bigLineChart.activeIndex = index;
    // }
  },
};
</script>

<style scoped>
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
  background-image: url(/assets/house.png);
  position: absolute;
  opacity: 0.9;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: cover;
  background-position: center top;
}
</style>
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
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 600px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
