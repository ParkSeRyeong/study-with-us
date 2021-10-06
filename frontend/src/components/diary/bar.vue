<template>
  <div id="chart">
    <apexchart type="bar" height="300" :options="chartOptions" :series="series"></apexchart>
    <MonthlyStudyInfo style="margin-top: 20px"/>
  </div>
</template>

<script>
import MonthlyStudyInfo from "../../components/MonthlyDiary/MonthlyStudyInfo";
export default {
  name: "bar",
  components:{MonthlyStudyInfo},
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
        tooltip: {
          enabled: false,
        },
        dataLabels:{
          enabled: false,
          formatter: function (val) {
            return val + '%'
          },
        },
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
          categories: this.$store.state.weeklydiary.studyDay,
          labels: {
            show: false,
          },
          axisBorder:{
            show:false,
          }
        },
        yaxis:{
          labels: {
            style: {
              fontSize: '15px',
            },
          },
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