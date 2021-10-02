package com.ssafy.study.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Test response")
@ToString
public class TestRes {

    @ApiModelProperty(name="a", example="02:13:17")
    private String a;

    @ApiModelProperty(name="b", example="04:06:17")
    private String b;

}
