<template>
  <div>
    <TitleBar>&nbsp;&nbsp;DIARY</TitleBar>
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
import TitleBar from "@/components/TitleBar";
export default {
  components: {
    MonthlyCalendar,
    MonthlyStudyInfo,
    BottomMenu,
    DiaryNav,
    TitleBar,
  },
};
</script>

<style></style>
