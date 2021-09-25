package com.ssafy.study.api.controller;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.UserServiceImpl;
import com.ssafy.study.api.service.service.MainService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
@Api("Main Controller API V1")
@CrossOrigin("*")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    MainService mainService;

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "메인페이지 진입")
    @GetMapping("/")
    public MyStudyRes getTodayInfo(@RequestHeader(value = "Authorization") String token){
        // 총 공부시간, 순공부시간, todolist, 명언 가져오기
        MyStudyRes res = mainService.getTodayInfo(token);
        return res;
    }
}
