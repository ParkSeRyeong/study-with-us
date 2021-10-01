package com.ssafy.study.api.service.service;

import com.ssafy.study.api.response.DailyRes;
import com.ssafy.study.api.response.MonthlyRes;
import com.ssafy.study.api.response.WeeklyRes;

import java.util.List;

public interface DiaryService {
    List<DailyRes> getDailyDiary(String token, String today);
    List<WeeklyRes> getWeeklyDiary(String inputDate, String token);
    MonthlyRes getMonthlyDiary(String inputDate, String token);


}
