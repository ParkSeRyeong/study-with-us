package com.ssafy.study.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ApiModel("Monthly Res")
@ToString
public class WeeklyRes {

    @ApiModelProperty(name="전체 집중 시간", example="04:06:17")
    private String totalFocusTime;

    @ApiModelProperty(name="전체 딴짓 시간", example="03:11:00")
    private String totalOtherTime;

    private String dayAndWeek;

    @ApiModelProperty(name="날짜", example="2021-09-26")
    private String day;

    @ApiModelProperty(name="집중 시간 퍼센테이지", example="70%")
    private int focusPercent;

    @ApiModelProperty(name="딴짓 시간 퍼센테이지", example="30%")
    private int otherPercent;

}
