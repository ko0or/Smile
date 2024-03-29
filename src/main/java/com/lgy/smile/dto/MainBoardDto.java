package com.lgy.smile.dto;

import lombok.Data;

@Data
public class MainBoardDto {

	private int identity;
	private String content;
	private String created;
	private String likes;
	private int user;
	
	private int likeCount;
	private int commentCount;
	
	private String nickname;
	private String imgPath;
}
