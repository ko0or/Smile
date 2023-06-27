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
	private String target_user_nickname;//"OO님에게 답장"의 OO
	private int user;
	private int board;
	private int group;//댓글
	private int index;//대댓글
	private int target_user;//대댓글을쓸 대상
}
