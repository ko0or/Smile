package com.lgy.smile.dto;

import lombok.Data;

@Data
public class MainCommentDto {
	
	private int identity;
	private String nickname;
	private String content;
	private String created;
	
	private int user;
	private int board;
	private int group;
	private int index;
	private int target_user;
	
	private String target_user_nickname;
	private String imgPath;
}
