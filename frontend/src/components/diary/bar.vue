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
        data: [13, 50, 41, 37, 22, 43, 21]
      }, {
        name: '딴짓 시간',
        data: [12, 50, 33, 52, 13, 43, 32]
      }],
      chartOptions: {
        chart: {
          type: 'bar',
          height: 350,
          stacked: true,
        },
        plotOptions: {
          bar: {
            horizontal: true,
          },
        },
        xaxis: {
          categories: ['일','월','화','수','목','금','토'],
          labels: {
            formatter: function (val) {
              return val + "분"
            }
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