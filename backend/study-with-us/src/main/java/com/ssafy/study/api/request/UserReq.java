package com.ssafy.study.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserRegisterPostRequest")
public class UserReq {

	@ApiModelProperty(name="ID", example="test")
	private String userid;

	@ApiModelProperty(name="PW", example="1234")
	private String password;

	@ApiModelProperty(name="닉네임", example="test")
	private String nickname;

	@ApiModelProperty(name="이름", example= "테스트")
	private String name;

	@ApiModelProperty(name="휴대폰 번호", example="010-0000-0000")
	private String phone;

}
