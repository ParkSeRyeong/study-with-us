package com.ssafy.study.api.service.service;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.response.UserRes;

import java.util.HashMap;

public interface MainService {
    MyStudyRes getTodayInfo(String token);
    void updateTodo(String token, HashMap<String, Boolean> todo_list);
}
