package com.ssafy.study.db.repository;

import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.entity.User_Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAlarmRepository extends JpaRepository<User_Alarm, Long> {
   // Optional<User> findByUserId(String id);
}
