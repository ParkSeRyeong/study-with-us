package com.ssafy.study.api.service.service;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.UserRes;

public interface UserService {
    void signUp(UserReq info);
    String login(UserReq info);
    boolean idDuplicateCheck(String id);
    UserRes getUserInfo(String token);
    void withdrawalUser(String token);

    boolean jwt_test(String token);
}
