package com.ssafy.study.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Entity     // 이 클래스는 DB 클래스와 링크될 클래스이다!
@Getter
@Builder
@RequiredArgsConstructor
public class User {

    @Id     // PK임을 나타냄.
  //  @GeneratedValue(strategy = GenerationType.AUTO)     // PK 생성규칙 설정.
    @Column(name = "userid")
    private String userid;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @NotNull
    @Column(unique = true)
    private String name;

    private String phone;

    @ManyToOne(fetch = LAZY)        // 일대다 관계에서 user는 "다" -> group은 "일". 즉 얘가 주인
    @JoinColumn(name = "userid")    // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private Group_Study group_study;

    @OneToOne(mappedBy = "user", fetch = LAZY)      // user_alarm에서 'user'로 선언해놨으니, mappedBy=user임.
    private User_Alarm user_alarm;

// @Column(unique = true, name = "user_id")
// 테이블의 컬럼. 굳이 선언 안해도 알아서 컬럼이 되긴 함.
// 이렇게 @Column을 써주는 경우는 기본값 이외에 추가 옵션이 있는 경우에!

}
