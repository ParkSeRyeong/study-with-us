package com.ssafy.study.api.controller;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.UserLoginPostRes;
import com.ssafy.study.api.response.UserRes;
import com.ssafy.study.api.service.service.UserService;
import com.ssafy.study.common.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@Api("User Controller API V1")
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public BaseResponseBody signUp(@RequestBody @ApiParam(value = "회원가입 정보", required = true) UserReq registerInfo){
         userService.signUp(registerInfo);
        return BaseResponseBody.of(200, "Success");
    }


    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public UserLoginPostRes login(@RequestBody @ApiParam(value = "로그인 정보", required = true) UserReq registerInfo) {
        logger.debug("login method 진입");
        String token = userService.login(registerInfo);
        System.out.println(token);
        return UserLoginPostRes.of(200, "Success", token);
    }


    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public BaseResponseBody logout(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        logger.debug("logout method 진입");
        /**
         *  구현해야함!!!
         * */
        return BaseResponseBody.of(200,"Success");
    }

    @ApiOperation(value = "아이디 중복 확인")
    @GetMapping(value = "/idDuplciateCheck/{userid}")
    public BaseResponseBody idDuplicateCheck(@ApiParam(value="확인할 아이디", required = true) @PathVariable("userid") String userid) {
        if(!userService.idDuplicateCheck(userid)) {
            return BaseResponseBody.of(200, "Success");
        }else{
            logger.info("아이디 중복!");
            return BaseResponseBody.of(401, "Duplicated");
        }
    }

    @ApiOperation(value = "사용자 정보 가져오기")
    @GetMapping("/userinfo")
    public UserRes getUserInfo(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        logger.debug("userinfo method 진입");
        return userService.getUserInfo(token);
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/userinfo")
    public BaseResponseBody withdrawalUser(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        logger.debug("withdrawalUser method 진입");
        userService.withdrawalUser(token);
        return BaseResponseBody.of(200, "Success");
    }


    @ApiOperation(value = "jwt 유효성 검증")
    @PostMapping("/jwtValidate")
    public BaseResponseBody jwtValidateTest(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        logger.debug("jwt 유효성 검증 테스트 메서드");
        if (userService.jwt_test(token)) {
            return UserLoginPostRes.of(200, "Success");
        } else {
            return BaseResponseBody.of(403,"Forbidden");
        }
    }
}
