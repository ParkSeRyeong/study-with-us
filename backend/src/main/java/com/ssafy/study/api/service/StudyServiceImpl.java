package com.ssafy.study.api.service;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.StudyService;
import com.ssafy.study.common.util.JwtTokenUtil;
import com.ssafy.study.db.entity.*;
import com.ssafy.study.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
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
    @Autowired
    private DailyOtherRepository dailyOtherRepository;

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
        System.out.println(" ====================== getStudyInfo 진입 ==========================");
        // alltime, focustime 뽑을 daily_study
        User user = getUser(token);
        Date now = getToday();
        if (dailyStudyRepository.findByUserAndDay(user, now) == null) {
            Daily_Study new_daily = Daily_Study.builder()
                    .day(now)
                    .user(user)
                    .build();
            dailyStudyRepository.save(new_daily);
            logger.info("new daily_study save complete.");

            Daily_Other new_other = Daily_Other.builder()
                    .dailyStudy(new_daily)
                    .build();
            dailyOtherRepository.save(new_other);
            logger.info("new daily_other save complete.");
        }

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
        Date now = getToday();
        // 1. alltime, focustime 업데이트
        User user = getUser(token);
        // Daily_Study today = dailyStudyRepository.findByUserAndDay(user, getToday());        // 근데 없으면? 새로 저장해야지

        // 2. 근데 오늘 Daily_Study 엔티티가 없다면? >> 새로 생성
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

        // 오늘 날짜에 해당하는 daily_study / daily_other 엔티티 받아오기
        Daily_Study today_study = dailyStudyRepository.findByUserAndDay(user, now);
        Daily_Other today_other = dailyOtherRepository.findByDailyStudy(today_study);

        // 1. daily_study 테이블의 alltime / focustime / othertime 더해서 추가하기
        Time updateAlltime = addTime(today_study.getAlltime(), Time.valueOf(info.getAlltime()));
        Time updateFocustime = addTime(today_study.getFocustime(), Time.valueOf(info.getFocustime()));
        Time totalOthertime = addTime(Time.valueOf(info.getSleeptime()), Time.valueOf(info.getPhonetime()));
        Time updateOthertime = addTime(today_study.getOthertime(), totalOthertime);

        today_study.updateTodayStudy(updateAlltime, updateFocustime, updateOthertime);
        dailyStudyRepository.save(today_study);

        // 2. daily_other 테이블의 sleeptime / phonetime 더해서 추가하기
        Time updateSleeptime = addTime(today_other.getSleeptime(), Time.valueOf(info.getSleeptime()));
        Time updatePhonetime = addTime(today_other.getPhonetime(), Time.valueOf(info.getPhonetime()));

        today_other.updateTodayOther(updateSleeptime, updatePhonetime);
        dailyOtherRepository.save(today_other);

        logger.debug("스터디 시간 업데이트 완료");

        // 2. to-do 업데이트
        List<Daily_Todo> todo_list = dailyTodoRepository.findByDailyStudy(today_study);
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

    @Override
    public Time addTime(Time prev, Time update) {
        int[] p = Arrays.stream(String.valueOf(prev).split(":")).mapToInt(Integer::parseInt).toArray();
        int[] u = Arrays.stream(String.valueOf(update).split(":")).mapToInt(Integer::parseInt).toArray();

        System.out.println("------------------------------");
        System.out.println(Arrays.toString(p));
        System.out.println(Arrays.toString(u));
        int sec = p[2] + u[2];
        int min = (p[1] + u[1]);
        int hour = (p[0] + u[0]);

        // 일단 전체 시간을 total에 저장 후!
        int total = sec + min * 60 + hour * 3600;

        // 다시 시간/분/초 단위로 나눈다
        // 왜? 50분과 20분을 더하면 -> 70분이 되어, 1시간 10분으로 바꿔여하니까
        int h = total / 3600;
        total -= (3600 * h);
        int m = total / 60;
        total -= (60 * m);
        int s = total;

        StringBuilder sb = new StringBuilder();
        // 한자릿수일 때는 0을 붙여서 : 4:3:25 -> 04:03:25
        sb.append(h / 10 == 0 ? "0" + h : h).append(":").append(m / 10 == 0 ? "0" + m : m).append(":").append(s / 10 == 0 ? "0" + s : s);
        Time res = Time.valueOf(sb.toString());
        return res;
    }
}
