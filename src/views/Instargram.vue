<template>
  <b-container class="bv-example-row mt-3 text-center">
    <br /><br /><br /><br />
    <h3 class="underline-green">
      <b-icon icon="instagram"></b-icon> Introduce
    </h3>
    <b-row>
      <b-col></b-col>
      <b-col cols="10">
        <template #header>Introduce</template>

        <template #lead> 소개용 페이지 </template>

        <hr class="my-4" />

        <p>우리는 이런 사이트 입니다.</p>

        <div class="mt-4">
          <b-card
            img-src="https://placekitten.com/300/300"
            img-alt="Card image"
            img-left
            class="mb-3"
          >
            <b-card-text> 개발자 소개 </b-card-text>
          </b-card>
          <br /><br /><br />
          <b-card
            img-src="https://placekitten.com/300/300"
            img-alt="Card image"
            img-left
            class="mb-3"
          >
            <b-card-text> 개발자 소개 </b-card-text>
          </b-card>
        </div>

        <br /><br /><br /><br /><br />
        <div align="right">
          <b-card
            no-body
            class="overflow-hidden"
            style="max-width: 540px"
            align="right"
          >
            <b-row no-gutters>
              <b-col md="6">
                <b-card-img
                  src="https://picsum.photos/400/400/?image=20"
                  alt="Image"
                  class="rounded-0"
                ></b-card-img>
              </b-col>
              <b-col md="6">
                <div align=" center">
                  <b-card-body title=" 기능소개 ">
                    <b-card-text> 유저관리 </b-card-text>
                  </b-card-body>
                </div>
              </b-col>
            </b-row>
          </b-card>
        </div>
        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
        <b-card no-body class="overflow-hidden" style="max-width: 540px">
          <b-row no-gutters>
            <b-col md="6">
              <b-card-body title="기능소개 2">
                <b-card-text> 게시글 관리 </b-card-text>
              </b-card-body>
            </b-col>

            <b-col md="6">
              <b-card-img
                src="https://picsum.photos/400/400/?image=20"
                alt="Image"
                class="rounded-0"
              ></b-card-img>
            </b-col>
          </b-row>
        </b-card>

        <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
        <div align="right">
          <b-card
            no-body
            class="overflow-hidden"
            style="max-width: 540px"
            align="right"
          >
            <b-row no-gutters>
              <b-col md="6">
                <b-card-img
                  src="https://picsum.photos/400/400/?image=20"
                  alt="Image"
                  class="rounded-0"
                ></b-card-img>
              </b-col>
              <b-col md="6">
                <div align=" center">
                  <b-card-body title="기능소개 3">
                    <b-card-text> 지도 보여주기 </b-card-text>
                  </b-card-body>
                </div>
              </b-col>
            </b-row>
          </b-card>
        </div>
      </b-col>
      <b-col></b-col>
    </b-row>

    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
  </b-container>
</template>

<script>
export default {
  name: "Instargram",
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style scoped>
.underline-green {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(56, 233, 40, 0.3) 30%
  );
}
</style>
