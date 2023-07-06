package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.ChattingDto;

public interface ChattingInterface {
	public ArrayList<ChattingDto> list();
	public ArrayList<ChattingDto> contentView(int chattingroom);
	public ChattingDto lastContent(int chattingroom);
	public void write(HashMap<String, String> params);
	
	public String getImgPath(HashMap<String, String> params);
	
	public ChattingDto countCheck(HashMap<String, String> params);
	public void tradeStatusUpdate(HashMap<String, String> params);
}
