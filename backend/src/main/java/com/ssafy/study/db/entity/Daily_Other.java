package com.ssafy.study.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Daily_Other {
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "other_pk")
    private int other_pk;

    @NotNull
    private Time sleeptime;

    @NotNull
    private Time phonetime;

    @OneToOne(fetch = LAZY)        // 얘가 주인 (FK 여기있음)
    @JoinColumn(name = "daily_pk")    // 연관관계의 주인은 mappedBy X, JoinColumn 사용
    private Daily_Study dailyStudy;

    // db에 insert 전에 null인 값은 초기값으로 변경
    @PrePersist
    public void prePersist(){
        this.sleeptime = this.sleeptime == null? Time.valueOf("00:00:00"):this.sleeptime;
        this.phonetime = this.phonetime == null? Time.valueOf("00:00:00"):this.phonetime;
    }

    public void update(){

    }
}
