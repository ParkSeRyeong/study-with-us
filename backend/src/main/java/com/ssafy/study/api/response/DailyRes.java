package com.ssafy.study.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Main, study page response")
@ToString
public class DailyRes {

    @ApiModelProperty(name="allTime", example="07:17:17")
    private String alltime;

    @ApiModelProperty(name="focusTime", example="04:06:17")
    private String focustime;

    @ApiModelProperty(name="otherTime", example="03:11:00")
    private String othertime;

    @ApiModelProperty(name="sleepTime", example="03:11:00")
    private String sleeptime;

    @ApiModelProperty(name="phoneTime", example="03:11:00")
    private String phonetime;

    @ApiModelProperty(name="to-do")
    private HashMap<String, Boolean> todo;

}
