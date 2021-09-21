package com.ssafy.study.db.repository;

import com.ssafy.study.db.entity.Daily_Study;
import com.ssafy.study.db.entity.Daily_Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface DailyTodoRepository extends JpaRepository<Daily_Todo, Long> {
    List<Daily_Todo> findByDailyStudy(Daily_Study daily_study);
}

