package com.ssafy.study.db.repository;

import com.ssafy.study.db.entity.Saying;
import com.ssafy.study.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SayingRepository extends JpaRepository<Saying, Long> {
   // 랜덤으로 명언 하나 뽑아오는 쿼리
   @Query(value = "select quote from saying order by rand() limit 1", nativeQuery = true)
   String getRandomQuote();
}

