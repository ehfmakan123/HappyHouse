<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
        <b-col xl="3" md="6">
          <stats-card
            title="Total traffic"
            type="gradient-red"
            sub-title="350,897"
            icon="ni ni-active-40"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">3.48%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Total traffic"
            type="gradient-orange"
            sub-title="2,356"
            icon="ni ni-chart-pie-35"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">12.18%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Sales"
            type="gradient-green"
            sub-title="924"
            icon="ni ni-money-coins"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-danger mr-2">5.72%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Performance"
            type="gradient-info"
            sub-title="49,65%"
            icon="ni ni-chart-bar-32"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">54.8%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
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
import { mapState } from "vuex";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      infowindow: null,
      // sidoname: null,
      // gugunname: null,
      markerImageSrc:
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png", // 마커이미지의 주소입니다. 스프라이트 이미지 입니다
      coffeeMarkers: [], // 커피숍 마커 객체를 가지고 있을 배열입니다
      storeMarkers: [], // 편의점 마커 객체를 가지고 있을 배열입니다
      carparkMarkers: [], // 주차장 마커 객체를 가지고 있을 배열입니다
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6331aee7aadedb9f97782a69e73678a9&libraries=services";
      console.log("mounted");
      document.head.appendChild(script);
    }
  },
  computed: {
    ...mapState("houseStore", ["sido"]),
    ...mapState("houseStore", ["gugun"]),
  },
  watch: {
    sido(val) {
      console.log("sido watch 호출");
      this.sido = val;
    },
    gugun(val) {
      console.log("gugun watch 호출");
      this.gugun = val;
    },
  },
  methods: {
    initMap() {
      const container = document.getElementById("map-custom");
      container.style.width = `90%`;
      container.style.height = `500px`;
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
    // initMap(google) {
    //   let map,
    //     lat = 40.748817,
    //     lng = -73.985428,
    //     color = "#5e72e4";
    //   map = document.getElementById("map-custom");

    //   let myLatlng = new google.maps.LatLng(lat, lng);
    //   let mapOptions = {
    //     zoom: 12,
    //     scrollwheel: false,
    //     center: myLatlng,
    //     mapTypeId: google.maps.MapTypeId.ROADMAP,
    //     styles: [
    //       {
    //         featureType: "administrative",
    //         elementType: "labels.text.fill",
    //         stylers: [{ color: "#444444" }],
    //       },
    //       {
    //         featureType: "landscape",
    //         elementType: "all",
    //         stylers: [{ color: "#f2f2f2" }],
    //       },
    //       {
    //         featureType: "poi",
    //         elementType: "all",
    //         stylers: [{ visibility: "off" }],
    //       },
    //       {
    //         featureType: "road",
    //         elementType: "all",
    //         stylers: [{ saturation: -100 }, { lightness: 45 }],
    //       },
    //       {
    //         featureType: "road.highway",
    //         elementType: "all",
    //         stylers: [{ visibility: "simplified" }],
    //       },
    //       {
    //         featureType: "road.arterial",
    //         elementType: "labels.icon",
    //         stylers: [{ visibility: "off" }],
    //       },
    //       {
    //         featureType: "transit",
    //         elementType: "all",
    //         stylers: [{ visibility: "off" }],
    //       },
    //       {
    //         featureType: "water",
    //         elementType: "all",
    //         stylers: [{ color: color }, { visibility: "on" }],
    //       },
    //     ],
    //   };

    //   map = new google.maps.Map(map, mapOptions);

    //   let marker = new google.maps.Marker({
    //     position: myLatlng,
    //     map: map,
    //     animation: google.maps.Animation.DROP,
    //     title: "Hello World!",
    //   });

    //   let contentString =
    //     '<div class="info-window-content"><h2>Argon Dashboard PRO</h2>' +
    //     "<p>A beautiful premium dashboard for Bootstrap 4.</p></div>";

    //   let infowindow = new google.maps.InfoWindow({
    //     content: contentString,
    //   });

    //   google.maps.event.addListener(marker, "click", function () {
    //     infowindow.open(map, marker);
    //   });
    // },
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

    // this.sido = this.$store.dispatch("houseStore/sido", { root: true });
    // this.gugun = this.$store.dispatch("houseStore/gugun", {
    //   root: true,
    // });
    // Promise.all(
    //   this.$store.dispatch("houseStore/gugun", {
    //     root: true,
    //   })
    // ).then((result) => (this.gugun = result));
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

        // 인포윈도우를 마커위에 표시합니다
        // console.log(infowindow);
        // infowindow.open(this.map, marker);

        // 마커에 클릭이벤트를 등록합니다
        // kakao.maps.event.addListener(marker, "click", function () {
        //   // 마커 위에 인포윈도우를 표시합니다
        //   infowindow.open(this.map, marker);
        // });

        // 지도 중심좌표를 접속위치로 변경합니다
        // this.map.setCenter(coords);
        // 인포윈도우로 장소에 대한 설명을 표시합니다
        // var infowindow = new kakao.maps.InfoWindow({
        //   content:
        //     '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>',
        // });
        // infowindow.open(map, marker);

        // // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        // map.setCenter(coords);
      });
      // console.log(bounds, "bounds");
      // set(bounds);
      setTimeout(this.set, 1000, bounds);
      console.log(this.markers);

      // markerPositions.forEach(function (marker) {
      //   geocoder.addressSearch(marker.address, function (result, status) {
      //     console.log(marker);
      //     // 정상적으로 검색이 완료됐으면
      //     if (status === kakao.maps.services.Status.OK) {
      //       // console.log("result: " + result[0]);
      //       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
      //       marker.coords = coords;
      //       console.log(marker);

      //       // 마커 이미지의 이미지 크기 입니다
      //       var imageSize = new kakao.maps.Size(24, 35);

      //       // 마커 이미지를 생성합니다
      //       var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      //       // 결과값으로 받은 위치를 마커로 표시합니다
      //       var marker = new kakao.maps.Marker({
      //         map: this.map,
      //         position: coords,
      //         title: marker["아파트"], // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      //         image: markerImage, // 마커 이미지
      //       });

      //       // 인포윈도우로 장소에 대한 설명을 표시합니다
      //       // var infowindow = new kakao.maps.InfoWindow({
      //       //   content:
      //       //     '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>',
      //       // });
      //       // infowindow.open(map, marker);

      //       // // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      //       // map.setCenter(coords);
      //     }
      //   });
      // });
    }

    // 주소로 좌표를 검색합니다
    // geocoder.addressSearch(
    //   "제주특별자치도 제주시 첨단로 242",
    //   function (result, status) {
    //     // 정상적으로 검색이 완료됐으면
    //     if (status === kakao.maps.services.Status.OK) {
    //       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

    //       // 결과값으로 받은 위치를 마커로 표시합니다
    //       var marker = new kakao.maps.Marker({
    //         map: map,
    //         position: coords,
    //       });

    //       // 인포윈도우로 장소에 대한 설명을 표시합니다
    //       var infowindow = new kakao.maps.InfoWindow({
    //         content:
    //           '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>',
    //       });
    //       infowindow.open(map, marker);

    //       // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    //       map.setCenter(coords);
    //     }
    //   }
    // );

    // if (this.markers.length > 0) {
    //   this.markers.forEach((marker) => marker.setMap(null));
    // }

    // const positions = markerPositions.map(
    //   (position) => new kakao.maps.LatLng(...position)
    // );

    // if (positions.length > 0) {
    //   this.markers = positions.map(
    //     (position) =>
    //       new kakao.maps.Marker({
    //         map: this.map,
    //         position,
    //       })
    //   );

    //   const bounds = positions.reduce(
    //     (bounds, latlng) => bounds.extend(latlng),
    //     new kakao.maps.LatLngBounds()
    //   );

    //   this.map.setBounds(bounds);
    // }
  },
  // closeOverlay() {
  //   overlay.setMap(null);
  // },
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
  // mounted() {
  //   GoogleMapsLoader.load((google) => {
  //     this.initMap(google);
  //   });
  // },
};
</script>
