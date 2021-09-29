package com.ssafy.study.api.service;

import com.ssafy.study.api.response.DiaryRes;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.DiaryService;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.Daily_Todo;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.entity.User_Alarm;
import com.ssafy.study.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final JwtTokenUtil jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DailyStudyRepository dailyStudyRepository;
    @Autowired
    private DailyTodoRepository dailyTodoRepository;
    @Autowired
    private DailyOtherRepository dailyOtherRepository;
    @Autowired
    private UserAlarmRepository userAlarmRepository;

    @Transactional
    public User getUser(String token) {
        String id = jwtTokenProvider.getUserInfo(token);
        User user = userRepository.findUserByUserid(id)
                .orElseThrow(() -> new IllegalArgumentException("ID not exisiting"));
        return user;
    }

    public DiaryRes getDailyDiary() {
        Date now = new Date(System.currentTimeMillis());
        System.out.println("now : " + now);
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(cal.DATE, -1);
        String today = sdf.format(cal.getTime());
        System.out.println(today+" / "+Date.valueOf(today));

        return null;
    }


}
