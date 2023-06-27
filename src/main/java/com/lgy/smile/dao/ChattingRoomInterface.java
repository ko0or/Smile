package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.lgy.smile.dto.ChattingRoomDto;

public interface ChattingRoomInterface {
	public ArrayList<ChattingRoomDto> list();
	public ArrayList<ChattingRoomDto> sellerList(HashMap<String, String> params);
	public ChattingRoomDto contentView(HashMap<String, String> params);
	public int write(HashMap<String, String> params);
	public void delete(HashMap<String, String> params);
}
