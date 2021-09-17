package com.ssafy.study.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@RequiredArgsConstructor
public class Group_Study{
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "group_pk")
    private int group_pk;

//    @NotNull
//    private String userid;

    @NotNull
    private int number;

    @NotNull
    private String title;

    private String notice;
    private String introduce;

    // default 값 설정 -> DynamicInsert로 해결
    @NotNull
    private String studyroom;

    @OneToMany(mappedBy = "group_study", cascade = CascadeType.ALL)     // user가 주인. user에서 선언한 group_study를 mappedBy에 써주는 것.
    private List<User> users = new ArrayList<>();    // NPE 방지를 위해 ArrayList를 생성해주는게 관례.

    @OneToMany(mappedBy = "group_study", cascade = CascadeType.ALL)     // group_study가 주인.
    private List<Group_Member> members = new ArrayList<>();    // NPE 방지를 위해 ArrayList를 생성해주는게 관례.

}
