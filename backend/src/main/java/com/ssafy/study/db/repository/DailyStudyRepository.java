package com.ssafy.study.db.repository;

import com.ssafy.study.api.response.MyStudyRes;
import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DailyStudyRepository extends JpaRepository<Daily_Study, Long> {

     List<Daily_Study> findByUser(User user);
     Daily_Study findByUserAndDay(User user, Date day);
     List<Daily_Study> findByDay(Date day);

//    @Query("SELECT distinct m FROM Daily_Study m join fetch m.user")
//    List<Daily_Study> findAllByUser();
}

