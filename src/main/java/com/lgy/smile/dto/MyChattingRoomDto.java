package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyChattingRoomDto {
	private int roomId;
	private int board;
	private int seller;
	private int buyer;
	private String title;
	private int userId;
	private String nickname;
	private String imgPath;
}
