package com.lgy.smile.dto;

import lombok.Data;

@Data
public class MainCommentDto {
	
	private int identity;
	private String content;
	private String created;
	private int user;
	private int board;
	
	private String nickname;
}
