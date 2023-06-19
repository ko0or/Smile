package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto {
	
	private int identity;
	private String title;
	private String content;
	private String created;
	private String contacted;
	private String imgPath;
	private String status;
	private String address;	
	private String price;
	private int user;
	
}


