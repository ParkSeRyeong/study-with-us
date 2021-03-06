package com.ssafy.study.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Group_Member {
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "member_pk")
    private int member_pk;

    @ManyToOne(fetch = LAZY)        // 일대다 관계에서 member는 "다" -> group은 "일". 즉 얘가 주인
    @JoinColumn(name = "group_pk")    // 연관관계의 주인은 mappedBy 안 씀. JoinColumn 사용
    private Group_Study group_study;

    @ManyToOne(fetch = LAZY)    // 얘가 주인
    @JoinColumn(name = "userid")
    private User user;

    @NotNull
    private Date joinday;
}
