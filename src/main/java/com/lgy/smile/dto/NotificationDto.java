package com.lgy.smile.dto;
import lombok.Data;

@Data
public class NotificationDto {
	
	private int identity;
	private String msg;
	private String created;
	private int user;
}

