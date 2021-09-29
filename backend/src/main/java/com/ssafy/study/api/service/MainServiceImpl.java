package com.ssafy.study.api.service;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.MainService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.Daily_Other;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.Daily_Todo;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

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
    private DailyOtherRepository dailyOtherRepository;


    @Autowired
    private JwtTokenUtil jwtTokenProvider;

    public Date getToday() {
        Date now = new Date(System.currentTimeMillis());
        return now;
    }

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
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, getToday());

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
                //  System.out.println("------ todo list 출력 ------");
                for (Daily_Todo dt : todo_list) {
                    // System.out.println(dt.getTodo() + " / " + dt.isDone());
                    todo_items.put(dt.getTodo(), dt.isDone());
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

    @Override
    @Transactional
    public void updateTodo(String token, HashMap<String, Boolean> todo_list) {
        User user = getUser(token);
        Date now = new Date(System.currentTimeMillis());

        // 오늘의 daily_study entity가 없으면 : 새로 생성
        if (dailyStudyRepository.findByUserAndDay(user, now) == null) {
            Daily_Study new_daily = Daily_Study.builder()
                    .day(now)
                    .user(user)
                    .build();
            dailyStudyRepository.save(new_daily);
            logger.info("new daily_study save complete");

            Daily_Other new_other = Daily_Other.builder()
                    .dailyStudy(new_daily)
                    .build();
            dailyOtherRepository.save(new_other);
            logger.info("new daily_other save complete");
        }

        // 오늘의 Daily_Study 엔티티 = today
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, now);
        logger.info(today.getDaily_pk() + " / " + today.getDay());

        // todo_list 의 todo와 체크여부done을 iterator로 돌리면서 todo엔티티에 저장.
        Iterator<String> it = todo_list.keySet().iterator();
        while (it.hasNext()) {
            String todo = it.next();
            Boolean isDone = todo_list.get(todo);

            System.out.println(todo + " / " + isDone);

            // 업데이트로 들어온 to-do 리스트에서 하나하나 뽑아 넣는다
            Daily_Todo new_todo = Daily_Todo.builder()
                    .todo(todo)
                    .done(isDone)
                    .dailyStudy(today)
                    .build();
            dailyTodoRepository.save(new_todo);
        }

    }

    @Transactional
    public void deleteTodayTodo(User user, Date day) {
        // 오늘 날짜에 해당하는 유저 투두 가져와서
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, getToday());

        // 걍... 다 날리기
        dailyTodoRepository.deleteByDailyStudy(today);
    }
}
