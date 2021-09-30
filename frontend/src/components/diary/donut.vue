<template>
  <div>
    <apexchart width="350" type="donut" :options="options" :series="options.series"></apexchart>
  </div>
</template>

<script>

export default {
  name: 'DonutExample',
  data: function() {
    let n = 22;
    return {
      options: {
        legend: { //하단 설정
          position: 'bottom',
        },
        dataLabels: {
          enabled: false
        },
        expandOnClick: false,
        series: [10,40,50],
        labels:['good','bye','hi'],
        plotOptions: {
          pie: {
            startAngle: 0,
            expandOnClick: true,
            offsetX: 0,
            offsetY: 0,
            customScale: 1,
            dataLabels: {
              offset: 0,
              minAngleToShowLabel: 10,
            },
            donut: {
              size: "65%",
              background: "transparent",
              labels: {
                show: true,
                name: {
                  show: true,
                  label: `안녕`,
                  fontSize: "22px",
                  fontFamily: "Helvetica, Arial, sans-serif",
                  fontWeight: 600,
                  color: undefined,
                  offsetY: -10,
                  formatter: function () {
                    return '총 공부시간';
                  },
                },
                total: {
                  show: true,
                  showAlways: false,
                  fontSize: "22px",
                  fontFamily: "Helvetica, Arial, sans-serif",
                  fontWeight: 600,
                  color: "#373d3f",
                  formatter: function () {
                    return `${n}`;
                  },
                },
              },
            },
          },
        },
      },

    }

  }
}
</script>