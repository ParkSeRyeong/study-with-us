package com.ssafy.study.db.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity     // 이 클래스는 DB 클래스와 링크될 클래스이다!
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails{

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
    private String name;

    private String phone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)      // user_alarm에서 'user'로 선언해놨으니, mappedBy=user임.
    private User_Alarm userAlarm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)     // group_study가 주인.  group_study에서 선언한 [user]를 mappedBy에 써주는 것.
    private List<Group_Study> groupStudy = new ArrayList<>();    // NPE 방지를 위해 ArrayList를 생성해주는게 관례.

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Group_Member> groupMember = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Daily_Study> dailyStudy = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


// @Column(unique = true, name = "user_id")
// 테이블의 컬럼. 굳이 선언 안해도 알아서 컬럼이 되긴 함.
// 이렇게 @Column을 써주는 경우는 기본값 이외에 추가 옵션이 있는 경우에!

}
