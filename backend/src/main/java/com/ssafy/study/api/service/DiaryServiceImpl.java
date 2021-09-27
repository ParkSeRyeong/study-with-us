package com.ssafy.study.api.service;

import com.ssafy.study.api.response.DailyRes;
import com.ssafy.study.api.response.WeeklyRes;
import com.ssafy.study.api.service.service.DiaryService;
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
    public DailyRes getDiaryByDay(User user, String day) {
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

            DailyRes res = DailyRes.builder()
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


    public List<DailyRes> getDailyDiary(String token, String today) {
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
        cal.set(year, month - 1, day);

        cal.add(Calendar.DATE, -1);
        String yesterday = sdf.format(cal.getTime());   // 어제

        cal.add(Calendar.DATE, 2);
        String tomorrow = sdf.format(cal.getTime());    // 내일
        System.out.println(yesterday + " / " + today + " / " + tomorrow);

        // 2. 토큰에서 유저정보 뽑아온 후 각가에 맞는 DailyRes 생성 후 list에 add
        User user = getUser(token);
        List<DailyRes> dailyDiary = new ArrayList<>();
        dailyDiary.add(getDiaryByDay(user, yesterday));
        dailyDiary.add(getDiaryByDay(user, today));
        dailyDiary.add(getDiaryByDay(user, tomorrow));

        return dailyDiary;
    }


    public List<WeeklyRes> getWeeklyDiary(String inputDate, String token) {
        User user = getUser(token);
        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        String[] input = inputDate.split("-");
        int year = Integer.valueOf(input[0]);
        int month = Integer.valueOf(input[1]);
        int date = Integer.valueOf(input[2]);

        cal.set(year, month - 1, date);
        Date today = Date.valueOf(ymd.format(cal.getTime()));

        int now_month = cal.get(cal.MONTH) + 1;
        int now_week = cal.get(cal.WEEK_OF_MONTH);
        int now_day = cal.get(cal.DAY_OF_WEEK);
        StringBuilder sb = new StringBuilder();
        sb.append(now_month).append("월 ").append(now_week).append("째주");

        cal.set(cal.DAY_OF_WEEK, 1);
        Date first_day = Date.valueOf(ymd.format(cal.getTime()));
        // List<WeeklyRes> list = new ArrayList<>();

        Calendar focus = new GregorianCalendar();
        focus.set(0, 0, 0, 0, 0, 0);
        Calendar other = new GregorianCalendar();
        other.set(0, 0, 0, 0, 0, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);

        List<Daily_Study> list = dailyStudyRepository.getWeekStudyInfo(first_day, today, user);
//        Time aa = dailyStudyRepository.getMaxStudyInfo(first_day, today);
//        System.out.println("*****에이설마*** " + aa.toString());
        HashMap<String, WeeklyRes> date_list = new HashMap<>();

        double ftime = 0.0;
        double otime = 0.0;

        boolean isFirst = true;
        for (Daily_Study ds : list) {

            // 지옥의 하드코딩 시작
            String[] focust = hms.format(ds.getFocustime()).split(":");
            String[] othert = hms.format(ds.getOthertime()).split(":");
            int[] focus_times = Arrays.asList(focust).stream().mapToInt(Integer::parseInt).toArray();
            int[] other_times = Arrays.asList(othert).stream().mapToInt(Integer::parseInt).toArray();

            double cur_focus = focus_times[2] + focus_times[1] * 60 + focus_times[0] * 3600;
            double cur_other = other_times[2] + other_times[1] * 60 + other_times[0] * 3600;

            if (isFirst) {
                ftime = cur_focus;
                otime = cur_other;
                isFirst = !isFirst;
            }

            focus.add(focus.SECOND, focus_times[2]);
            focus.add(focus.MINUTE, focus_times[1]);
            focus.add(focus.HOUR, focus_times[0]);
            other.add(other.SECOND, other_times[2]);
            other.add(other.MINUTE, other_times[1]);
            other.add(other.HOUR, other_times[0]);
        }

        String total_focustime = hms.format(focus.getTime());
        String total_othertime = hms.format(other.getTime());

        // 전체 시간 구했으니 퍼센테이지 구합시다
        for (Daily_Study ds : list) {
            System.out.println("----------------------------");
            String[] focust = hms.format(ds.getFocustime()).split(":");
            String[] othert = hms.format(ds.getOthertime()).split(":");
            int[] focus_times = Arrays.asList(focust).stream().mapToInt(Integer::parseInt).toArray();
            int[] other_times = Arrays.asList(othert).stream().mapToInt(Integer::parseInt).toArray();

            double cur_focus = focus_times[2] + focus_times[1] * 60 + focus_times[0] * 3600;
            double cur_other = other_times[2] + other_times[1] * 60 + other_times[0] * 3600;
            System.out.println(cur_focus + " / " + cur_other);

            int cur_focus_percentage = (int) ((cur_focus / ftime) * 100.0);
            int cur_other_percentage = (int) ((cur_other / ftime) * 100.0);

            System.out.println(cur_focus_percentage + " / " + cur_other_percentage);
            WeeklyRes res = WeeklyRes.builder()
                    .totalFocusTime(total_focustime)
                    .totalOtherTime(total_othertime)
                    .dayAndWeek(sb.toString())
                    .day(ymd.format(ds.getDay()))
                    .focusPercent(cur_focus_percentage)
                    .otherPercent(cur_other_percentage)
                    .build();
            date_list.put(ymd.format(ds.getDay()), res);
        }

        // 찐반환
        List<WeeklyRes> res = new ArrayList<>();
        // 다시 오늘부터
        cal.set(year, month - 1, date);
        cal.getTime();  // 얘를 한 번 호출 하고 안 하고에 따라 에러가 난다..... 왜...?
        cal.set(cal.DAY_OF_WEEK, 1);

        for (int i = 0; i < 7; i++) {
            if (i != 0)
                cal.add(cal.DATE, 1);
            System.out.println("현재 날짜 : " + ymd.format(cal.getTime()));
            WeeklyRes isStudy = date_list.get(ymd.format(cal.getTime()));
            if (isStudy == null) {
                WeeklyRes notStudy = WeeklyRes.builder()
                        .totalFocusTime(total_focustime)
                        .totalOtherTime(total_othertime)
                        .dayAndWeek(sb.toString())
                        .day(ymd.format(cal.getTime()))
                        .focusPercent(0)
                        .otherPercent(0)
                        .build();

                res.add(notStudy);
            } else {
                res.add(isStudy);
            }
        }

        System.out.println("----------완성----------");
        for (WeeklyRes wr : res) {
            System.out.println(wr.toString());
        }

        return res;
    }
}
