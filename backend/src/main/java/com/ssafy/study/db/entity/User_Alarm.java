package com.ssafy.study.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity     // 이 클래스는 DB 클래스와 링크될 클래스이다!
@Getter
@Builder
@DynamicInsert      // 입력 안 할시에 default값으로 생성.
@AllArgsConstructor
@RequiredArgsConstructor
public class User_Alarm {

    @Id     // PK임을 나타냄.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // PK 생성규칙 설정. AutoIncrement 설정
    @Column(name = "alarm_pk")
    private int alarm_pk;

    private Boolean screen;
    private Boolean sound;
    private Boolean msg;


    @OneToOne(fetch = LAZY)          // 일대일 관계에서 FK를 가지고 있는 애가 주인. 즉 얘가 주인
    @JoinColumn(name = "userid")     // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private User user;               // ex) 얘가 private User member; 였으면?
                                    // User entity의 OneToOne 도 mappedBy=member임

    public void updateAlarm(boolean screen, boolean sound, boolean msg) {
        this.screen = screen;
        this.sound = sound;
        this.msg = msg;
    }

}
