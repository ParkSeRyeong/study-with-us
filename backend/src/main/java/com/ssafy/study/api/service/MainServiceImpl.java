package com.ssafy.study.api.service;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.MainService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.Daily_Todo;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.repository.DailyStudyRepository;
import com.ssafy.study.db.repository.DailyTodoRepository;
import com.ssafy.study.db.repository.SayingRepository;
import com.ssafy.study.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SayingRepository sayingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DailyStudyRepository dailyStudyRepository;
    @Autowired
    private DailyTodoRepository dailyTodoRepository;


    @Autowired
    private JwtTokenUtil jwtTokenProvider;

    @Transactional
    public User getUser(String token) {
        String id = jwtTokenProvider.getUserInfo(token);
        User user = userRepository.findUserByUserid(id)
                .orElseThrow(() -> new IllegalArgumentException("ID not exisiting"));
        return user;
    }

    @Override
    @Transactional
    public MyStudyRes getTodayInfo(String token) {
        // 0. 랜덤 명언 하나 뽑기
        String quote = sayingRepository.getRandomQuote();

        // 1. token에서 유저정보 가져옴.
        User user = getUser(token);

        // 2. 가져온 유저의 >>오늘<< daily_study 정보 가져옴
        Date now = new Date(System.currentTimeMillis());
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, now);

        MyStudyRes res = null;

        // 3. 오늘 공부를 안 해서 daily_study가 없다면?
        if (today == null) {
            System.out.println("오늘의 daily 존재 x");
            // 초기값 뱉어주기. Daily_Study 따로 생성 x
            res = MyStudyRes.builder()
                    .alltime("00:00:00")
                    .focustime("00:00:00")
                    .quote(quote).build();
        }
        // 4. 오늘 공부를 해서 daily_study가 있다면 : to_do가 있는지 보자
        else {
            List<Daily_Todo> todo_list = dailyTodoRepository.findByDailyStudy(today);
            if (todo_list.size() > 0) {
                HashMap<String, Boolean> todo_items = new HashMap<>();
                System.out.println("------ todo list 출력 ------");
                for (Daily_Todo dt : todo_list) {
                    System.out.println(dt.getTodo()+" / "+dt.isCheck());
                    todo_items.put(dt.getTodo(), dt.isCheck());
                }
                res = MyStudyRes.builder()
                        .alltime(today.getAlltime().toString())
                        .focustime(today.getFocustime().toString())
                        .todo(todo_items)
                        .quote(quote)
                        .build();
            } else {
                 res = MyStudyRes.builder()
                        .alltime(today.getAlltime().toString())
                        .focustime(today.getFocustime().toString())
                         .quote(quote)
                         .build();
            }
        }
        return res;
    }
}
