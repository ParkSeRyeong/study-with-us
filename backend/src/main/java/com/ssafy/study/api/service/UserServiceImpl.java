package com.ssafy.study.api.service;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.response.UserRes;
import com.ssafy.study.api.service.service.UserService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.entity.User_Alarm;
import com.ssafy.study.db.repository.UserAlarmRepository;
import com.ssafy.study.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAlarmRepository userAlarmRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenProvider;

    @Override
    public void signUp(UserReq info) {
        User user= User.builder()
                .userid(info.getUserid())
                .password(passwordEncoder.encode(info.getPassword()))
                .nickname(info.getNickname())
                .name(info.getName())
                .phone(info.getPhone())
                .build();
        userRepository.save(user);
        User_Alarm user_alarm= User_Alarm.builder()
                .user(user)
                .build();
        userAlarmRepository.save(user_alarm);
    }

    @Override
    public String login(UserReq info) {
        User user=userRepository.findUserByUserid(info.getUserid())
                .orElseThrow(() -> new IllegalArgumentException("ID is not existing"));

        if(!passwordEncoder.matches(info.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Wrong Password");
        }
        /* jwt 파싱 확인 코드 */
//        logger.debug("로그인 정보 확인 : "+user.getUserid());
//        // 1. 토큰 생성
//        String token = jwtTokenProvider.createToken(user.getUserid());
//
//        // 2. validation 확인
//        System.out.println("isvalidate: " + jwtTokenProvider.validateToken(token));
//
//        // 3. userinfo 뽑아보기
//        System.out.println(jwtTokenProvider.getUserInfo(token));
        return jwtTokenProvider.createToken(user.getUserid());
    }

    @Override
    public boolean idDuplicateCheck(String id) {
        if(userRepository.findUserByUserid(id) != null)   return true;      // id를 찾아봤는데 null이 아니다 = 이미 있는 사용자 아이디
        return false;
    }

    @Override
    public UserRes getUserInfo(String token) {
        String userid = jwtTokenProvider.getUserInfo(token);
        User user = userRepository.findUserByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("User Not Existing"));

        // pwd는 뱉어주지 x
        UserRes info = UserRes.builder()
                .userid(user.getUserid())
                .nickname(user.getNickname())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
        return info;
    }

    @Override
    public void withdrawalUser(String token) {
        // invalidate 어떻게 할래?
        String id = jwtTokenProvider.getUserInfo(token);
        /**
         *
         * 로그아웃 먼저 시켜야함!!!!!!!!!!! 코드 추가하기!!!!!!!!!!!!!!!
         *
         * */
        User user=userRepository.findUserByUserid(id)
                .orElseThrow(() -> new IllegalArgumentException("ID is not existing"));
        userRepository.delete(user);
        userRepository.flush();     // insert, update, delete를 하면서 쌓인 영속성 컨텍스트 내용을 DB에 반영! 영속성 컨텍스트가 비워지지는 않는다.
    }

    @Override
    public boolean jwt_test(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
