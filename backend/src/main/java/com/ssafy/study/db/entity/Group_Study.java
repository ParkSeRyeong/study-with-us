package com.ssafy.study.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Group_Study{
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "group_pk")
    private int group_pk;

    @NotNull
    private int max_number;

    @NotNull
    private String title;

    private String notice;
    private String introduce;

    // default 값 설정 -> DynamicInsert로 해결
    @NotNull
    private String studyroom;

    @ManyToOne(fetch = LAZY)        // 일대다 관계에서 group_study는 "다" -> user는 "일". 즉 얘가 주인. FK 여기있음
    @JoinColumn(name = "userid")    // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private User user;

    @OneToMany(mappedBy = "group_study", cascade = CascadeType.ALL)     // group_study가 주인.
    private List<Group_Member> member = new ArrayList<>();    // NPE 방지를 위해 ArrayList를 생성해주는게 관례.

}
