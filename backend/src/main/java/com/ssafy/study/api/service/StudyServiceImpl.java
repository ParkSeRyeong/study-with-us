package com.ssafy.study.api.service;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.Daily_Todo;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.entity.User_Alarm;
import com.ssafy.study.db.repository.DailyStudyRepository;
import com.ssafy.study.db.repository.DailyTodoRepository;
import com.ssafy.study.db.repository.UserAlarmRepository;
import com.ssafy.study.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final JwtTokenUtil jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DailyStudyRepository dailyStudyRepository;
    @Autowired
    private DailyTodoRepository dailyTodoRepository;
    @Autowired
    private UserAlarmRepository userAlarmRepository;

    @Transactional
    public User getUser(String token) {
        String id = jwtTokenProvider.getUserInfo(token);
        User user = userRepository.findUserByUserid(id)
                .orElseThrow(() -> new IllegalArgumentException("ID not exisiting"));
        return user;
    }

    public Date getToday() {
        Date now = new Date(System.currentTimeMillis());
        return now;
    }

    @Override
    public MyStudyRes getStudyInfo(String token) {
        // alltime, focustime 뽑을 daily_study
        User user = getUser(token);
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, getToday());

        // to_do 뽑을 daily_todo
        List<Daily_Todo> todo_list = dailyTodoRepository.findByDailyStudy(today);
        HashMap<String, Boolean> todo_items = new HashMap<>();
        for (Daily_Todo dt : todo_list) {
            todo_items.put(dt.getTodo(), dt.isDone());
        }

        // screen, sound, msg 뽑을 user_alarm
        User_Alarm alarm = userAlarmRepository.findByUser(user);

        MyStudyRes res = MyStudyRes.builder()
                .alltime(today.getAlltime().toString())
                .focustime(today.getFocustime().toString())
                .todo(todo_items)
                .screen(alarm.getScreen())
                .sound(alarm.getSound())
                .msg(alarm.getMsg())
                .build();

        return res;
    }

    @Override
    public void saveTodayStudy(String token, MyStudyRes info) {
        /**
         * {
         * "alltime" : "99:99:99",
         * "focustime" : "99:99:99",
         * "to_do" : [[aaa,0],[bbb,1],[ccc,0]],
         * "screen" : 0,
         * "sound" : 0,
         * "msg" : 1
         * }
         * */
        // 1. alltime, focustime 업데이트
        User user = getUser(token);
        Daily_Study today = dailyStudyRepository.findByUserAndDay(user, getToday());
        today.updateTodayStudy(Time.valueOf(info.getAlltime()), Time.valueOf(info.getFocustime()));
        dailyStudyRepository.save(today);
        logger.debug("스터디 시간 업데이트 완료");

        // 2. to-do 업데이트
        List<Daily_Todo> todo_list = dailyTodoRepository.findByDailyStudy(today);
        HashMap<String, Boolean> update_todo = info.getTodo();

        for (Daily_Todo dt : todo_list) {
            String todo = dt.getTodo();
            Boolean done = dt.isDone();
            Boolean update_status = update_todo.get(todo);

            // 업데이트된 상태가 db의 상태와 다르다면?
            if (update_status != done) {
                logger.info("현재 db : " + done + " / 업데이트된 상태 : " + update_status);
                dt.updateTodo(update_status);
                dailyTodoRepository.save(dt);
                logger.info("todo 업데이트 완료");
            }
        }

        User_Alarm alarm = userAlarmRepository.findByUser(user);
        // screen이나 sound나 msg 중 하나라도 상태가 바뀐게 있을 때에만 업데이트.
        // 불필요한 db 커넥션 최소화...?
        if (alarm.getScreen() != info.isScreen() || alarm.getSound() != info.isSound() || alarm.getMsg() != info.isMsg()) {
            alarm.updateAlarm(info.isScreen(), info.isSound(), info.isMsg());
            userAlarmRepository.save(alarm);
        }

    }
}
