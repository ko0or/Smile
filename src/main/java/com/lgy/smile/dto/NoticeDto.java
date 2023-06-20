package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
	private int identity;
	private String title;
	private String content;
	private String created;
	private int view;
	private String author;
	private int user;
	
	private String nickname;
	private int board;
	
	
	private int comments;
}
