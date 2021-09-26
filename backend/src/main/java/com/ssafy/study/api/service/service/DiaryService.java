package com.ssafy.study.api.service.service;

import com.ssafy.study.api.response.DiaryRes;
import com.ssafy.study.api.response.MyStudyRes;

import java.util.List;

public interface DiaryService {
    List<DiaryRes> getDailyDiary(String token, String today);
    void nowTesting();
}
