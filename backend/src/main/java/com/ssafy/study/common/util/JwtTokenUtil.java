package com.ssafy.study.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

/**
 * jwt 토큰 유틸 정의.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static String secretKey = "secretKey-authorization-jwt-manage-token-study-with-us";
    private static Long tokenValidTime = 3000 * 60 * 1000L;       // 토큰 유효 시간 나중에 바꾸기 : 지금 1초 * 60 * 300 = 300분, 5시간
//    private static Long tokenValidTime = 30 * 1000L;       // 만료되는지 보기 위한 30초따리 하루살이 토큰
    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate redisTemplate;


    // 객체 초기화, secretKey Base64로 인코딩.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /** 토큰 생성 */
    public String createToken(@NotNull String id) {

        // 1. 초기화 후 비밀키 인코딩
        init();
        // 2. token에 넣을 현재 날짜+시간
        Date now = new Date();
        // 3. Byte형으로 비밀키 파싱
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(id)
                .claim("id", id)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                //.signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }


    /** JWT 토큰에서 인증 정보 조회 */
    public Authentication getAuthentication(String token) {
        logger.debug("getAuthentication 진입");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserInfo(String token) {
        logger.debug("getUserInfo : 들어온 토큰 = " + token);
        try {
            // parser가 > 비밀키를 가지고 > claim을 파싱해서 > 그 안의 body에서 > subject에 해당하는 걸 뽑아냄 > subject : id 넣어둠
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (Exception e){
            log.info("잘못된 토큰입니다.");
        }
        return null;
    }

    // Request의 Header에서 token 값을 가져옴. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            System.out.println(claims.getBody());
//            ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
//            if (logoutValueOperations.get(token) != null) {
//                logger.debug("로그아웃된 토큰입니다.");
//                return false;
//            }
            log.info("유효한 JWT 토큰입니다!");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

//    public boolean verifyToken(String token) {
//        if (token == null) return false;
//        try {
//            String result2 = JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
//                    .build()
//                    .verify(token.replace("Bearer", ""))
//                    .getSubject();
//            //result
//            logger.debug(result2);
//            return true;
//        } catch (Exception e) {
//            logger.error("token값 인증 오류" + e.getMessage());
//            return false;
//        }
//    }

}
