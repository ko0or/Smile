package com.lgy.smile.common;
import lombok.Data;

@Data
public class EmailSenderConfig {
	private String naverId, naverPw;
	private String host, auth, ssl, port;
	
}
