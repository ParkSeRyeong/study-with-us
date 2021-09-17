package com.ssafy.study.api.service;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.api.service.service.UserService;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.entity.User_Alarm;
import com.ssafy.study.db.repository.UserAlarmRepository;
import com.ssafy.study.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAlarmRepository userAlarmRepository;

    @Override
    public void signUp(UserReq info) {
        User user= User.builder()
                .userid(info.getUserid())
                .password(info.getPassword())
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
}
