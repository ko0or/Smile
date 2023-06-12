package com.lgy.smile.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userDto {
	
	private int pk;
	private String username;
	private String nickname;
	private String id;
	private String pw;
	private String tel;
	private String email;
	private int point;
	private String created;
	
}
