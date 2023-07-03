package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoomDto {
	private int identity;
	private int board;
	private int seller;
	private int buyer;
	private int price;
}
