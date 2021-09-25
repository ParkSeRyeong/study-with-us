package com.ssafy.study.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JwtTokenUtil jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // CORS에서 OPTIONS 메서드를 미리 날려 요청권한이 있는지 확인하기 때문에, 이 경우엔 JWT 검증 스킵.
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}else {
            // 1. request Parameter에서  'Authorization'으로 넘어온 녀석을 찾아봄.
            String token = request.getHeader("Authorization");

            // 2. 토큰이 있다면?
            if (token != null && token.length() > 0) {
                logger.debug("token 검증 interceptor 진입!");
                if(!jwtTokenProvider.validateToken(token)){
                    throw new Exception("유효하지 않은 인증 토큰입니다.");
                }
                return true;
            }
            // 3. 토큰이 없다면?
            else {
                throw new RuntimeException("인증 토큰이 없습니다");
            }
        }
    }
}

