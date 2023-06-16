package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingDto {
	private int identity;
	private int board;
	private int seller;
	private int chattingroom;
	private String sender;
	private String sendtime;
	private String msg;
	private String receiver;
}
