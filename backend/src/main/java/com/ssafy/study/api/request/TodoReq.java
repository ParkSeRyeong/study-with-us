package com.ssafy.study.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 투두 토글
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TodoRequest")
public class TodoReq {

	@ApiModelProperty(name="날짜", example = "2021-09-26")
	private String day;

	@ApiModelProperty(name="todo", example="알고리즘 문제 풀기")
	private String todo;

	@ApiModelProperty(name="체크여부", example="true")
	private boolean done;

}
