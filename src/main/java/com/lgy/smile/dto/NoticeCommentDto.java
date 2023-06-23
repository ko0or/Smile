package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Getter
public class NoticeCommentDto {
	private int identity;
	private String content;
	private String created;
	private String nickname;
	private int user;
	private int board;
	private int reply_by_user;
}
