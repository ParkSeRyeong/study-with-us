package com.ssafy.study.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Daily_Study {
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "daily_pk")
    private int daily_pk;

    @NotNull
    private Date day;

    @NotNull
    private Time alltime;

    @NotNull
    private Time focustime;

    @NotNull
    private Time othertime;

    @ManyToOne(fetch = LAZY)        // 얘가 주인 (FK 여기있음)
    @JoinColumn(name = "userid")    // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private User user;

    @OneToOne(mappedBy = "dailyStudy", fetch = LAZY)      // daily_other가 주인
    private Daily_Other dailyOther;

    @OneToMany(mappedBy = "dailyStudy", fetch = LAZY)
    private List<Daily_Todo> dailyTodo = new ArrayList<>();

}
