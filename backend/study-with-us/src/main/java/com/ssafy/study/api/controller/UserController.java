package com.ssafy.study.api.controller;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.service.service.UserService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api("User Controller API V1")
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value = "회원 가입", notes = "회원가입 한다.")
    @PostMapping("/signup")
    public BaseResponseBody signUp(@RequestBody @ApiParam(value = "회원가입 정보", required = true) UserReq registerInfo) {
        logger.debug("signup method 진입");
        userService.signUp(registerInfo);
        return BaseResponseBody.of(200, "Success");
    }
}
