<template>
  <div>

    <!-- <base-header class="bg-image pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      Card stats
      <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    </base-header> -->
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <b-carousel-slide
        caption="First slide"
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        <h1 class="text-white">Haapy House에 오신 걸 환영합니다.</h1>
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

      <!-- Slides with img slot -->
      <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
      <b-carousel-slide>
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://picsum.photos/1024/480/?image=55"
            alt="image slot"
          >
        </template>
      </b-carousel-slide>

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <!-- <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunt
          a tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum.
        </p>
      </b-carousel-slide> -->
    </b-carousel>
    <br/><br/><br/><br/><br/><br/>
    <!--Charts-->
    <!-- <b-container fluid class="mt--7">
      <b-row>
        <b-col xl="8" class="mb-5 mb-xl-0">
          <card type="default" header-classes="bg-transparent">
            <b-row align-v="center" slot="header">
              <b-col>
                <h6 class="text-light text-uppercase ls-1 mb-1">Overview</h6>
                <h5 class="h3 text-white mb-0">Sales value</h5>
              </b-col>
              <b-col>
                <b-nav class="nav-pills justify-content-end">
                  <b-nav-item
                       class="mr-2 mr-md-0"
                       :active="bigLineChart.activeIndex === 0"
                       link-classes="py-2 px-3"
                       @click.prevent="initBigChart(0)">
                      <span class="d-none d-md-block">Month</span>
                      <span class="d-md-none">M</span>
                  </b-nav-item>
                  <b-nav-item
                    link-classes="py-2 px-3"
                    :active="bigLineChart.activeIndex === 1"
                    @click.prevent="initBigChart(1)"
                  >
                    <span class="d-none d-md-block">Week</span>
                    <span class="d-md-none">W</span>
                  </b-nav-item>
                </b-nav>
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

        <b-col xl="4" class="mb-5 mb-xl-0">
          <card header-classes="bg-transparent">
            <b-row align-v="center" slot="header">
              <b-col>
                <h6 class="text-uppercase text-muted ls-1 mb-1">Performance</h6>
                <h5 class="h3 mb-0">Total orders</h5>
              </b-col>
            </b-row>

            <bar-chart
              :height="350"
              ref="barChart"
              :chart-data="redBarChart.chartData"
            >
            </bar-chart>
          </card>
        </b-col>
      </b-row>

      <b-row class="mt-5">
        <b-col xl="8" class="mb-5 mb-xl-0">
          <page-visits-table></page-visits-table>
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <social-traffic-table></social-traffic-table>
        </b-col>
      </b-row>
    </b-container> -->

  </div>
</template>
<script>
  // Charts
  // import * as chartConfigs from '@/components/Charts/config';
  // import LineChart from '@/components/Charts/LineChart';
  // import BarChart from '@/components/Charts/BarChart';

  // // Components
  // import BaseProgress from '@/components/BaseProgress';
  // import StatsCard from '@/components/Cards/StatsCard';

  // Tables
  // import SocialTrafficTable from './Dashboard/SocialTrafficTable';
  // import PageVisitsTable from './Dashboard/PageVisitsTable';

  // export default {
    // components: {
    //   LineChart,
    //   BarChart,
    //   BaseProgress,
    //   StatsCard,
      // PageVisitsTable,
      // SocialTrafficTable
    // },
    // data() {
    //   return {
    //     bigLineChart: {
    //       allData: [
    //         [0, 20, 10, 30, 15, 40, 20, 60, 60],
    //         [0, 20, 5, 25, 10, 30, 15, 40, 40]
    //       ],
    //       activeIndex: 0,
    //       chartData: {
    //         datasets: [
    //           {
    //             label: 'Performance',
    //             data: [0, 20, 10, 30, 15, 40, 20, 60, 60],
    //           }
    //         ],
    //         labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //       },
    //       extraOptions: chartConfigs.blueChartOptions,
    //     },
    //     redBarChart: {
    //       chartData: {
    //         labels: ['Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //         datasets: [{
    //           label: 'Sales',
    //           data: [25, 20, 30, 22, 17, 29]
    //         }]
    //       },
    //       extraOptions: chartConfigs.blueChartOptions
    //     }
    //   };
    // },
  //   methods: {
  //     initBigChart(index) {
  //       let chartData = {
  //         datasets: [
  //           {
  //             label: 'Performance',
  //             data: this.bigLineChart.allData[index]
  //           }
  //         ],
  //         labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
  //       };
  //       this.bigLineChart.chartData = chartData;
  //       this.bigLineChart.activeIndex = index;
  //     }
  //   },
  //   mounted() {
  //     this.initBigChart(0);
  //   }
  // };
</script>
<style>
.el-table .cell{
  padding-left: 0px;
  padding-right: 0px;
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
  /* opacity: 0.7; */
  top: 0;
  left: 0;
  z-index: -1;
  min-height: 400px;
  background-size: cover; 
  background-position: center top;
}

</style>

