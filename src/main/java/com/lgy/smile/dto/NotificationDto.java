package com.lgy.smile.dto;
import lombok.Data;

@Data
public class NotificationDto {
	
	private int identity;
	private String msg;
	private String created;
	private int user;
	private String nickname;
	private String url_path;
	private int sender;
	private String senderNickname;
	
	
	// 채팅방 알람용
	private int count;
	private int notificationIdentity;
	private int boardIdentity;
	private int buyerIdentity;	
	
	

}

