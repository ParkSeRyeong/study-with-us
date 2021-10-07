import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueApexCharts from "vue3-apexcharts";
import loader from "vue-ui-preloader";

createApp(App)
  .use(store)
  .use(router)
  .use(VueApexCharts)
  .use(loader)
  .mount("#app");

// import Vue from 'vue'
// import VCalendar from 'v-calendar'
// import Calendar from 'v-calendar/lib/components/calendar.umd'
// import DatePicker from 'v-calendar/lib/components/date-picker.umd'

// Vue.use(VCalendar, {
//     componentPrefix: 'vc',  // Use <vc-calendar /> instead of <v-calendar />
//   });

// // Register components in your 'main.js'
// Vue.component('calendar', Calendar)
// Vue.component('date-picker', DatePicker)
