package com.ssafy.study.api.service.service;

import com.ssafy.study.api.response.MyStudyRes;

import java.sql.Time;

public interface StudyService {
    MyStudyRes getStudyInfo(String token);
    void saveTodayStudy(String token, MyStudyRes info);
    Time addTime(Time prev, Time update);
}
