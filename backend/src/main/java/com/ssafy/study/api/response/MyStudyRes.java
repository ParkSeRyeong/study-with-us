package com.ssafy.study.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Main, study page response")
@ToString
public class MyStudyRes {
    /**
     {
     "alltime" : "99:99:99",
     "focustime" : "99:99:99",
     "to-do" : [[aaa,0],[bbb,1],[ccc,0]],
     "screen" : 0,
     "sound" : 0,
     "sentence" : 1
     "saying" : "Sdfsdfsdfsdfsdf"
     }
     * */
    @ApiModelProperty(name="allTime", example="07:17:17")
    private String alltime;

    @ApiModelProperty(name="focusTime", example="04:06:17")
    private String focustime;

    @ApiModelProperty(name="sleepTime", example="02:00:18")
    private String sleeptime;

    @ApiModelProperty(name="phoneTime", example="02:05:59")
    private String phonetime;

    @ApiModelProperty(name="to-do")
    private HashMap<String, Boolean> todo;

    @ApiModelProperty(name="saying")
    private String quote;

    @ApiModelProperty(name="screen", example="true")
    private boolean screen;

    @ApiModelProperty(name="sound", example="true")
    private boolean sound;

    @ApiModelProperty(name="msg", example="true")
    private boolean msg;

}
