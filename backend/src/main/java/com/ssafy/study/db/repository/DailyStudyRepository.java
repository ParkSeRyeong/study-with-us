package com.ssafy.study.db.repository;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DailyStudyRepository extends JpaRepository<Daily_Study, Long> {

     List<Daily_Study> findByUser(User user);
     Daily_Study findByUserAndDay(User user, Date day);
     List<Daily_Study> findByDay(Date day);

     // jpql은 as 별칭이 필수.
     @Query(value = "SELECT d FROM Daily_Study as d where d.day between :startDate and :endDate and d.user = :user order by d.focustime desc")
     List<Daily_Study> getWeekStudyInfo(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("user") User user);

     @Query(value = "SELECT max(focustime) FROM daily_study where day between :startDate and :endDate and userid = :userid order by focustime desc", nativeQuery = true)
     Time getMaxFocusTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userid") String userid);

     @Query(value = "SELECT sum(focustime) FROM daily_study where day between :startDate and :endDate order by focustime desc", nativeQuery = true)
     BigDecimal getTotalFocusTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

     @Query(value = "SELECT sum(othertime) FROM daily_study where day between :startDate and :endDate order by focustime desc", nativeQuery = true)
     BigDecimal getTotalOtherTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

//    @Query("SELECT distinct m FROM Daily_Study m join fetch m.user")
//    List<Daily_Study> findAllByUser();
}

