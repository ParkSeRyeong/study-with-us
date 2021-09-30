package com.ssafy.study.api.service;

import com.ssafy.study.api.response.DailyRes;
import com.ssafy.study.api.response.MonthlyRes;
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
import java.math.BigDecimal;
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

    public String switchString2Date(String date) {
        // ex) 01:03:20 -> 10320
        String h = "0", m = "0", s = "0";
        if (date.length() > 4) {
            // length =5 -> 0 12 34 -> 0 ~ 1/ 1 ~ 3 / 3 ~
            h = date.substring(0, date.length() - 4);
            m = date.substring(date.length() - 4, date.length() - 2);
            s = date.substring(date.length() - 2);
        }
        // ex( 00:04:02 -> 402
        else if (date.length() > 2) {
            // length=3 -> 0 12 -> 0~1 / 1~
            m = date.substring(0, date.length() - 2);
            s = date.substring(date.length() - 2);
        }
        // ex) 00:00:04 -> 4
        else {
            s = date;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(h).append(":").append(m).append(":").append(s);
        return sb.toString();
    }

    public int getTotalTime(String date) {
        String [] time = date.split(":");
        int hour = Integer.valueOf(time[0]);
        int min = Integer.valueOf(time[1]);
        int second = Integer.valueOf(time[2]);
        System.out.println(hour + " : " + min + " : " + second);

        return hour * 3600 + min * 60 + second;
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
        StringBuilder sb = new StringBuilder();
        sb.append(now_month).append("월 ").append(now_week).append("째주");

        cal.set(cal.DAY_OF_WEEK, 1);
        Date first_day = Date.valueOf(ymd.format(cal.getTime()));

        // Daily_study에서 목록이랑 전체 집중/딴짓 시간, 맥스 집중시간 가져옴
        List<Daily_Study> list = dailyStudyRepository.getWeekStudyInfo(first_day, today, user);
        BigDecimal getTotalFocus = dailyStudyRepository.getTotalFocusTime(first_day, today);
        BigDecimal getTotalOther = dailyStudyRepository.getTotalOtherTime(first_day, today);
        Time getMaxFocus = dailyStudyRepository.getMaxFocusTime(first_day, today, user.getUserid());
        System.out.println(hms.format(getMaxFocus));

        // res에 저장할 string형 전체 focus/other time
        String total_focustime = switchString2Date(String.valueOf(getTotalFocus.intValue()));
        String total_othertime = switchString2Date(String.valueOf(getTotalOther.intValue()));

        // double형 맥스 focustime : milisec로 변환 for 퍼센트 계산
        double ftime = getTotalTime(hms.format(getMaxFocus));

        // hash에 담아둔당
        HashMap<String, WeeklyRes> date_list = new HashMap<>();
        for (Daily_Study ds : list) {
            String[] focust = hms.format(ds.getFocustime()).split(":");
            String[] othert = hms.format(ds.getOthertime()).split(":");
            int[] focus_times = Arrays.asList(focust).stream().mapToInt(Integer::parseInt).toArray();
            int[] other_times = Arrays.asList(othert).stream().mapToInt(Integer::parseInt).toArray();

            double cur_focus = focus_times[2] + focus_times[1] * 60 + focus_times[0] * 3600;
            double cur_other = other_times[2] + other_times[1] * 60 + other_times[0] * 3600;
            System.out.println(cur_focus + " / " + cur_other);

            int cur_focus_percentage = (int) ((cur_focus / ftime) * 100.0);
            int cur_other_percentage = (int) ((cur_other / ftime) * 100.0);

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
        // 다시 오늘부터 하루씩 지나가면서
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

    public MonthlyRes getMonthlyDiary(String inputDate, String token) {
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

        cal.set(year, month-1, 1);
        Date first_day = Date.valueOf(ymd.format(cal.getTime()));
        cal.set(year, month-1, cal.getActualMaximum(cal.DAY_OF_MONTH));
        Date last_day = Date.valueOf(ymd.format(cal.getTime()));

        // Daily_study에서 목록이랑 전체 집중/딴짓 시간, 맥스 집중시간 가져옴
        List<Daily_Study> list = dailyStudyRepository.getWeekStudyInfo(first_day, last_day, user);
        BigDecimal getTotalFocus = dailyStudyRepository.getTotalFocusTime(first_day, last_day);
        BigDecimal getTotalOther = dailyStudyRepository.getTotalOtherTime(first_day, last_day);
        Time getMaxFocus = dailyStudyRepository.getMaxFocusTime(first_day, last_day, user.getUserid());
        System.out.println(hms.format(getMaxFocus));

        // res에 저장할 string형 전체 focus/other time
        String total_focustime = switchString2Date(String.valueOf(getTotalFocus.intValue()));
        String total_othertime = switchString2Date(String.valueOf(getTotalOther.intValue()));

        // double형 맥스 focustime : milisec로 변환 for 퍼센트 계산
        double ftime = getTotalTime(hms.format(getMaxFocus));

        // hash에 담아둔당
        HashMap<String, Integer> date_list = new HashMap<>();
        for (Daily_Study ds : list) {
            String[] focust = hms.format(ds.getFocustime()).split(":");
            String[] othert = hms.format(ds.getOthertime()).split(":");
            int[] focus_times = Arrays.asList(focust).stream().mapToInt(Integer::parseInt).toArray();
            int[] other_times = Arrays.asList(othert).stream().mapToInt(Integer::parseInt).toArray();

            double cur_focus = focus_times[2] + focus_times[1] * 60 + focus_times[0] * 3600;
            double cur_other = other_times[2] + other_times[1] * 60 + other_times[0] * 3600;
            System.out.println(cur_focus + " / " + cur_other);

            int cur_focus_percentage = (int) ((cur_focus / ftime) * 100.0);
            int cur_other_percentage = (int) ((cur_other / ftime) * 100.0);

            int dailyColor = 0;
            if (cur_focus_percentage < 25) dailyColor = 1;
            else if (cur_focus_percentage < 50) dailyColor = 2;
            else if (cur_focus_percentage < 75) dailyColor = 3;
            else dailyColor = 4;

            date_list.put(ymd.format(ds.getDay()), dailyColor);
        }

        // 찐반환
        List<Integer> dailyColor = new ArrayList<>();
        // 다시 오늘부터 하루씩 지나가면서
        cal.set(year, month - 1, 1);
        cal.getTime();  // 얘를 한 번 호출 하고 안 하고에 따라 값이 이상하게 나온다..... 왜...?


        for (int i = 0; i < cal.getActualMaximum(cal.DAY_OF_MONTH); i++) {
            if (i != 0)
                cal.add(cal.DATE, 1);
            System.out.println("현재 날짜 : " + ymd.format(cal.getTime()));
            int daily_color = date_list.get(ymd.format(cal.getTime()))==null ? 0 : date_list.get(ymd.format(cal.getTime()));
            dailyColor.add(daily_color);
        }
        MonthlyRes mr = MonthlyRes.builder()
                .totalFocusTime(total_focustime)
                .totalOtherTime(total_othertime)
                .month(month)
                .dailyColor(dailyColor).build();

        System.out.println("----------완성----------");
        System.out.println(mr.getDailyColor().toString());

        return mr;
    }
}
