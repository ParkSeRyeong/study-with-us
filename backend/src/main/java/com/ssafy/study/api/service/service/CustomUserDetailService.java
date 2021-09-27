package com.ssafy.study.api.service.service;

import com.ssafy.study.api.controller.UserController;
import com.ssafy.study.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername 진입");
        return userRepository.findUserByUserid(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                );
    }
}
