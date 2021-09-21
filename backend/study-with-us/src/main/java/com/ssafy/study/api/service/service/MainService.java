package com.ssafy.study.api.service.service;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.response.UserRes;

public interface MainService {
    MyStudyRes getTodayInfo(String token);
}
