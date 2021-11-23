<template>
  <div>
    <div class="map_wrap" align="center">
      <h2>카카오맵 지도 표시</h2>
      <div class="category">
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
            주우차장
          </li>
        </ul>
      </div>
      <div
        id="map"
        style="width: 100%; height: 100%; position: relative; overflow: hidden"
      ></div>

      <!-- 지도 확대, 축소 컨트롤 div 입니다 -->
    </div>
    <br />
    <!-- <div align="center">
      <button @click="displayInfoWindow">infowindow</button>
      <button @click="displayMarker([])">marker set 3 (empty)</button>
      <button @click="displayMarker(markerPositions2)">marker set 2</button>
      <button @click="displayMarker(markerPositions1)">marker set 1</button>
      <button @click="changeSize(600)">지도 보기</button>
      <button @click="changeSize(0)">지도 숨기기</button>
    </div> -->
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
      const container = document.getElementById("map");
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
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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
.category,
.category * {
  margin: 0;
  padding: 0;
  color: #000;
}
.category {
  position: absolute;
  overflow: hidden;
  top: -1px;
  left: -1px;
  width: 155px;
  height: 50px;
  z-index: 0px;
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
.wrap {
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
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
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
