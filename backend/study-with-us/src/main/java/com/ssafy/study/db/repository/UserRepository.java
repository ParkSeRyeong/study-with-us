package com.ssafy.study.db.repository;

import com.ssafy.study.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   // Optional<User> findByUserId(String id);
}
