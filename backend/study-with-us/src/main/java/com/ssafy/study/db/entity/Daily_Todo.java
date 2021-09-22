package com.ssafy.study.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Daily_Todo {
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "todo_pk")
    private int todo_pk;

    @NotNull
    private String todo;

    private boolean check;

    @ManyToOne(fetch = LAZY)        // 얘가 주인 (FK 여기있음)
    @JoinColumn(name = "daily_pk")    // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private Daily_Study dailyStudy;

}
