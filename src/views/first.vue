<template>
  <div class="wrapper">
    <div id="typography">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="tim-typo">
            <h1>
              <br />
              <span class="tim-note">이런 사람들을 위한<br /></span>사이트
              입니다.
            </h1>
            <br /><br /><br /><br /><br />
          </div>

          <div class="tim-typo">
            <span class="tim-note">#1</span>
            <p class="text-primary">친구들과 함께 살 집구하는 사람 ㅋㅋ</p>
          </div>
          <div class="tim-typo">
            <span class="tim-note">#2</span>
            <p class="text-muted">그냥 집구하는 사람 ㅋㅋ</p>
          </div>
          <div class="tim-typo">
            <span class="tim-note">#3</span>
            <p class="text-info">그냥 삽질하는 중인 사람 ㅎㅎ;</p>
          </div>
          <div class="tim-typo">
            <span class="tim-note">#4</span>
            <p class="text-success">싸피 서울 12반 화이팅 하는 사람</p>
          </div>
          <div class="tim-typo">
            <span class="tim-note">#5</span>
            <p class="text-warning">점심 식사 맛있게 할 사람</p>
          </div>
          <div class="tim-typo">
            <span class="tim-note">#6</span>
            <p class="text-danger">
              <b-icon icon="heart-fill"></b-icon>교수님 사랑해요<b-icon
                icon="heart-fill"
              ></b-icon>
            </p>
          </div>
          <div class="tim-typo text-white">
            <span class="tim-note"> #7</span>
            <p>
              그냥 숫자 7 맞추고 싶었던 사람 <br /><br /><br />이게 클래스가
              묶어지네 ㅋㅋㅋ
            </p>
          </div>
          <br /><br /><br />
          <b-container fluid class="p-4 bg-dark">
            <div class="text-white">
              <h2><span class="tim-note">다양한 집 목록들...</span></h2>
              <br /><br />
              여러분이 친구와 함께 살아갈 최적의 장소를 고르세요!
              <br /><br />
              밑에는 아파트 사진을 뿌릴 예정입니다.
              <br /><br /><br /><br />
            </div>
            <b-row>
              <b-col>
                <b-img
                  thumbnail
                  fluid
                  src="https://picsum.photos/250/250/?image=54"
                  alt="Image 1"
                ></b-img>
              </b-col>
              <b-col>
                <b-img
                  thumbnail
                  fluid
                  src="https://picsum.photos/250/250/?image=58"
                  alt="Image 2"
                >
                </b-img>
              </b-col>
              <b-col>
                <b-img
                  thumbnail
                  fluid
                  src="https://picsum.photos/250/250/?image=59"
                  alt="Image 3"
                ></b-img>
              </b-col>
              <b-col>
                <b-img
                  thumbnail
                  fluid
                  src="assets/sitelogo.png"
                  width="263"
                  alt="Image 4"
                ></b-img>
              </b-col>
            </b-row>
            <div class="tim-typo">
              <h2>
                <br /><br />
                <div class="text-warning">
                  <h1>
                    <span class="tim-note">저희 사이트와 함께</span><br />
                  </h1>
                  <br />
                  <h2><small>집을 찾아봅시다</small></h2>
                  <br />
                  <br />
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼가요??</h6>
                  <h6>화살표 이미지를 넣어볼까요??</h6>
                  <br />
                  <router-link to="/House">
                    <h1>CLICK HERE!!!!!</h1>
                  </router-link>
                  <br />
                  <br />
                </div>
              </h2>
            </div>
          </b-container>
        </div>
      </div>
    </div>
    <div class="space-50"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      image: require("/public/assets/happyhouse200px.png"),
      responsive: false,
    };
  },
  methods: {
    onResponsiveInverted() {
      let script = 600;
      if (window.innerWidth < script) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    },
  },
  mounted() {
    this.onResponsiveInverted();
    window.addEventListener("resize", this.onResponsiveInverted);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.onResponsiveInverted);
  },
};
</script>

<style lang="css"></style>
