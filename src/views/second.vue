<template>
  <div class="section2" aria-setsize="200" align="center">
    <carousel
      :autoplay="true"
      :nav="false"
      :dots="true"
      :loop="true"
      :items="1"
      :center="true"
    >
      <div class="sc2-con">
        <router-link :to="{ name: 'Home' }" class="link">
          <div>
            <h3><b-icon icon="house-fill" font-scale="1" />홈 페이지</h3>
            <br /><br />
            <img src="/assets/sitelogo.png" />
          </div>
        </router-link>
      </div>
      <div class="sc2-con">
        <router-link :to="{ name: 'Board' }" class="link">
          <div>
            <h3>
              <b-icon icon="exclamation-square" font-scale="1" />공지 사항
            </h3>
            <br /><br />
            <img src="/assets/sitelogo.png" />
          </div>
        </router-link>
      </div>
      <div class="sc2-con">
        <router-link :to="{ name: 'Instargram' }" class="link">
          <div>
            <h3><b-icon icon="star-fill" font-scale="1" />사이트 소개</h3>
            <br /><br />
            <img src="/assets/sitelogo.png" />
          </div>
        </router-link>
      </div>
      <div class="sc2-con">
        <router-link :to="{ name: 'House' }" class="link">
          <div>
            <h3><b-icon icon="building" font-scale="1" />아파트 정보</h3>
            <br /><br />
            <img src="/assets/sitelogo.png" />
          </div>
          <br /><br /><br /><br /><br /><br />
        </router-link>
      </div>
    </carousel>
  </div>
</template>

<script>
import carousel from "vue-owl-carousel";

export default {
  components: { carousel },
};
</script>

<style>
.section2 {
  width: 1700px;
  height: 800px;
}

.sc2-con {
  width: 30%;
  height: 100%;
  opacity: 1;
}
</style>
