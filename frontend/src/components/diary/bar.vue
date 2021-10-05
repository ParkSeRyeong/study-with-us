<template>
  <div id="chart">
    <apexchart type="bar" height="350" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script>
export default {
  name: "bar",
  data: function () {
    return {
      series: [{
        name: '순 공부 시간',
        data: this.$store.state.weeklydiary.studyInfo,
      }, {
        name: '딴짓 시간',
        data: this.$store.state.weeklydiary.otherInfo,
      }],
      chartOptions: {
        chart: {
          type: 'bar',
          height: 350,
          stacked: true,
          toolbar:{
            show:false,
          }
        },
        plotOptions: {
          bar: {
            horizontal: true,
          },
        },
        xaxis: {
          show: false,
          categories: ['일','월','화','수','목','금','토'],
          labels: {
            show: false,
          },
          axisBorder:{
            show:false,
          }
        },
        yaxis:{
          axisBorder: {
            show:false,
          }
        },
        fill: {
          opacity: 1
        },
        legend: {
          position: 'bottom',
          horizontalAlign: 'left',
          offsetX: 50
        }
      },
    }
  }
}
</script>

<style scoped>

</style>