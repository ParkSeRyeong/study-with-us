<template>
  <div>
    <apexchart width="350" type="donut" :options="options" :series="series" ></apexchart>
  </div>
</template>

<script>

export default {
  name: 'DonutExample',
  data: function() {
    return {
      series: this.$store.state.daily_diary.Studytime,
      options: {
        legend: { //하단 설정
          position: 'bottom',
        },
        dataLabels: {
          enabled: false
        },
        tooltip:{
          enabled:false,
        },
        labels:['집중 시간','졸음 시간','핸드폰 시간'],
        plotOptions: {
          pie: {
            startAngle: 0,
            expandOnClick: false,
            offsetX: 0,
            offsetY: 0,
            customScale: 1,
            dataLabels: {
              offset: 1,
              minAngleToShowLabel: 10,
            },
            donut: {
              size: "65%",
              background: "transparent",
              labels: {
                show: true,
                name: {
                  show: true,
                  fontSize: "22px",
                  fontFamily: "Helvetica, Arial, sans-serif",
                  fontWeight: 600,
                  color: undefined,
                  offsetY: -10,
                  formatter: function (val) {
                    if(val=="Total") return `총 공부 시간`
                    else return val
                  }
                },
                value: {
                  show: true,
                  fontSize: "18px",
                  fontFamily: "Helvetica, Arial, sans-serif",
                  fontWeight: 400,
                  color: undefined,
                  offsetY: 16,
                  formatter: function (val) {
                    var hour = val/3600;
                    val = val%3600;
                    let h =``
                    let m = ``
                    let s = ``
                    if(hour < 10) h = `0${parseInt(hour)}`
                    else h = `${parseInt(hour)}`
                    if(val/60 < 10) m = `0${parseInt(val/60)}`
                    else m = `${parseInt(val/60)}`
                    if(val%60 < 10) s = `0${val%60}`
                    else s = `${val%60}`
                    return `${h}:${m}:${s}`;
                  },
                },
                total: {
                  show: true,
                  showAlways: false,
                  fontSize: "22px",
                  fontFamily: "Helvetica, Arial, sans-serif",
                  fontWeight: 600,
                  color: "#373d3f",
                  formatter: function (w) {
                    let sum = w.globals.seriesTotals.reduce((a, b) => {
                      return a + b
                    }, 0)
                    var hour = sum/3600;
                    sum = sum%3600;
                    let h =``
                    let m = ``
                    let s = ``
                    if(hour < 10) h = `0${parseInt(hour)}`
                    else h = `${parseInt(hour)}`
                    if(sum/60 < 10) m = `0${parseInt(sum/60)}`
                    else m = `${parseInt(sum/60)}`
                    if(sum%60 < 10) s = `0${sum%60}`
                    else s = `${sum%60}`
                    return `${h}:${m}:${s}`;
                  }
                },
              },
            },
          },
        },
      },
    }
  },
}
</script>