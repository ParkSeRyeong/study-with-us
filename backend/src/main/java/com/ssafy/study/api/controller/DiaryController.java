package com.ssafy.study.api.controller;

import com.ssafy.study.api.request.TodoReq;
import com.ssafy.study.api.response.DiaryRes;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.UserServiceImpl;
import com.ssafy.study.api.service.service.DiaryService;
import com.ssafy.study.api.service.service.MainService;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
@Api("Diary Controller API V1")
@CrossOrigin("*")
public class DiaryController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    DiaryService diaryService;
    @Autowired
    MainService mainService;

    @ApiOperation(value = "일간 공부 다이어리 : 3일치")
    @PostMapping("/daily")
    public List<DiaryRes> getTodayInfo(HttpServletRequest request, @RequestBody String day) {
        final String token = request.getHeader("Authorization");
        List<DiaryRes> list = diaryService.getDailyDiary(token, day);
        for(DiaryRes dr : list) {
            System.out.println(dr);
        }
        return list;
    }


    @PostMapping("/test")
    public BaseResponseBody testing(HttpServletRequest request, @RequestBody @ApiParam(value = "공부 완료 후 갱신", required = true) MyStudyRes updateInfo) {
        final String token = request.getHeader("Authorization");
        diaryService.nowTesting();
        return BaseResponseBody.of(200, "Success");
    }

    @ApiOperation(value = "todo 체크/해제")
    @PostMapping("/toggleTodo")
    public BaseResponseBody toggleTodo(HttpServletRequest request, @RequestBody TodoReq updateTodo){
        final String token = request.getHeader("Authorization");
        mainService.toggleTodo(token, updateTodo);
        return BaseResponseBody.of(200, "Success");
    }

}
