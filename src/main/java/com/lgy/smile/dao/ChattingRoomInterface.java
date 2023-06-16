package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.ChattingRoomDto;

public interface ChattingRoomInterface {
	public ArrayList<ChattingRoomDto> list();
	public ChattingRoomDto contentView(@RequestParam HashMap<String, String> params);
	public void write(@RequestParam HashMap<String, String> params);
}
