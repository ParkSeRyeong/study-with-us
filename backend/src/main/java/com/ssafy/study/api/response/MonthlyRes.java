package com.ssafy.study.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ApiModel("Monthly Res")
@ToString
public class MonthlyRes {

    @ApiModelProperty(name="전체 집중 시간", example="04:06:17")
    private String totalFocusTime;

    @ApiModelProperty(name="전체 딴짓 시간", example="03:11:00")
    private String totalOtherTime;

    @ApiModelProperty(name="날짜", example="2021-09-26")
    private int month;

    @ApiModelProperty(name="집중 시간 퍼센테이지에 따른 색 코드", example="1")
    private List<Integer> dailyColor;
}
