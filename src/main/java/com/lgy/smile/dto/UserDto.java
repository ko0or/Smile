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
	private String pwd;
	private String nickname;
	private int point;
	private String role;
	
}