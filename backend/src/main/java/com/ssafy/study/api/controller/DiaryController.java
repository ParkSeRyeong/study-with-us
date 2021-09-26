package com.ssafy.study.api.controller;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.UserServiceImpl;
import com.ssafy.study.api.service.service.DiaryService;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diary")
@Api("Diary Controller API V1")
@CrossOrigin("*")
public class DiaryController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    DiaryService diaryService;

   // @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "공부페이지 진입")
    @GetMapping("/sidebar")
    public MyStudyRes getTodayInfo() {
        diaryService.getDailyDiary();
        return null;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "공부 완료 후 현재 정보 갱신")
    @PostMapping("/stop")
    public BaseResponseBody getTodayInfo(@RequestHeader(value = "Authorization") String token, @RequestBody @ApiParam(value = "공부 완료 후 갱신", required = true) MyStudyRes updateInfo) {

        return BaseResponseBody.of(200, "Success");
    }

}
