package com.ssafy.study.api.controller;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.response.TestRes;
import com.ssafy.study.api.service.DiaryServiceImpl;
import com.ssafy.study.api.service.UserServiceImpl;
import com.ssafy.study.api.service.service.MainService;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;

@RestController
@RequestMapping("/api/study")
@Api("Study Controller API V1")
@CrossOrigin("*")
public class StudyController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    StudyService studyService;

    @ApiOperation(value = "공부페이지 진입")
    @GetMapping("/stop")
    public MyStudyRes getTodayInfo(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        MyStudyRes res = studyService.getStudyInfo(token);
        res.setStatusCode(200);
        res.setMessage("OK");
        return res;
    }

    @ApiOperation(value = "공부 완료 후 현재 정보 갱신")
    @PostMapping("/stop")
    public BaseResponseBody getTodayInfo(HttpServletRequest request, @RequestBody @ApiParam(value = "공부 완료 후 갱신", required = true) MyStudyRes updateInfo) {
        final String token = request.getHeader("Authorization");
        studyService.saveTodayStudy(token, updateInfo);
        return BaseResponseBody.of(200, "Success");
    }

    @ApiOperation(value = "시간 테스트")
    @PostMapping("/")
    public void timeTest(TestRes res) {
        Time a = Time.valueOf(res.getA());
        Time b = Time.valueOf(res.getB());
        studyService.addTime(a, b);
    }
}
