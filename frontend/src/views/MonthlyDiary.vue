<template>
  <div>
    <DiaryNav />
    <MonthlyCalendar />
    <MonthlyStudyInfo />
  </div>

  <BottomMenu />
</template>

<script>
import MonthlyCalendar from "@/components/MonthlyDiary/MonthlyCalendar";
import MonthlyStudyInfo from "@/components/MonthlyDiary/MonthlyStudyInfo";
import DiaryNav from "@/components/DiaryNav";
import BottomMenu from "@/components/BottomMenu";
export default {
  components: {
    MonthlyCalendar,
    MonthlyStudyInfo,
    BottomMenu,
    DiaryNav,
  },
};
</script>

<style></style>
