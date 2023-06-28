package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private int identity;
	private String id;
	private String pwd;			// VIEW 에서 password 로 사용됨
	private String nickname;
	private String point;
	private String role;
	
	// 프로필 사진이 저장된 경로
	private String imgPath;
}






