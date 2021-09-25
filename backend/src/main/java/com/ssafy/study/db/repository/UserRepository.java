package com.ssafy.study.db.repository;

import com.ssafy.study.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
   // 왜 UserId가 안되는가...?
   Optional<User> findUserByUserid(String id);
//   Optional<UserDetails> findByUserId(String id);
}

