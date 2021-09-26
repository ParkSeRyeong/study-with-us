package com.ssafy.study.api.service;

import com.ssafy.study.api.response.DiaryRes;
import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.api.service.service.DiaryService;
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
import java.text.SimpleDateFormat;
import java.util.*;

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

    // null 처리
    public DiaryRes getDiaryByDay(User user, String day) {
        // 1. 오늘 날짜 date형으로 다시 변환
        Date today = Date.valueOf(day);

        // 2. 주어진 날짜/유저에 맞는 Daily_Study 엔티티
        Daily_Study today_study = dailyStudyRepository.findByUserAndDay(user, today);

        // 3. 그 날의 daily_study가 존재한다면!
        if (today_study != null) {
            Daily_Other today_other = dailyOtherRepository.findByDailyStudy(today_study);
            List<Daily_Todo> today_todo = dailyTodoRepository.findByDailyStudy(today_study);
            HashMap<String, Boolean> todo_items = new HashMap<>();

            for (Daily_Todo dt : today_todo) {
                todo_items.put(dt.getTodo(), dt.isDone());
            }

            DiaryRes res = DiaryRes.builder()
                    .alltime(today_study.getAlltime().toString())
                    .focustime(today_study.getFocustime().toString())
                    .othertime(today_study.getOthertime().toString())
                    .sleeptime(today_other.getSleeptime().toString())
                    .phonetime(today_other.getPhonetime().toString())
                    .todo(todo_items).build();

            return res;
        } else {
            return null;
        }
    }


    public List<DiaryRes> getDailyDiary(String token, String today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 1. 들어온 날짜 / 어제 / 내일 날짜 String으로 변환
        // 1-1. 오늘 날짜 쪼개기
        String[] days = today.split("-");
        int year = Integer.valueOf(days[0]);
        int month = Integer.valueOf(days[1]);
        int day = Integer.valueOf(days[2]);
        System.out.println(year + " " + month + " " + day);

        Calendar cal = Calendar.getInstance();
        // month는 0부터 시작한다고 ^^^^^^^^^^^ 9넣으면 10이 들어간다 ^^^^^^^^
        cal.set(year, month-1, day);

        cal.add(Calendar.DATE, -1);
        String yesterday = sdf.format(cal.getTime());   // 어제

        cal.add(Calendar.DATE, 2);
        String tomorrow = sdf.format(cal.getTime());    // 내일
        System.out.println(yesterday + " / " + today + " / " + tomorrow);

        // 2. 토큰에서 유저정보 뽑아온 후 각가에 맞는 DiaryRes 생성 후 list에 add
        User user = getUser(token);
        List<DiaryRes> dailyDiary = new ArrayList<>();
        dailyDiary.add(getDiaryByDay(user, yesterday));
        dailyDiary.add(getDiaryByDay(user, today));
        dailyDiary.add(getDiaryByDay(user, tomorrow));

        return dailyDiary;
    }

    public void nowTesting() {
        List<String> list = new ArrayList<>();
        System.out.println("초기 list 사이즈 : " + list.size());
        list.add(null);
        list.add(null);
        System.out.println("null 두번 추가 후 list 사이즈 : " + list.size());
        list.add("hello!");
        for (String s : list) {
            System.out.println(s == null ? "없음" : s);
        }
    }


}
