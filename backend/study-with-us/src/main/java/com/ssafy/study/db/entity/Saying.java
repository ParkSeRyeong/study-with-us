package com.ssafy.study.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@DynamicInsert      // 값을 입력 안 하는 경우 + default값이 설정되어있는 경우 default값으로 db에 반영!
@AllArgsConstructor
@RequiredArgsConstructor
public class Saying {
    @Id         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 설정
    @Column(name = "saying_pk")
    private int saying_pk;

    @NotNull
    private String quote;
}
