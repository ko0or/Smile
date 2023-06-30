package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.lgy.smile.dto.NotificationDto;

public interface NotificationMapperInterface {
	
	//=> ★ 알람 생성
	public void create(HashMap<String, String> params);
	
	//=> ★ 알람 삭제
	public void delete(HashMap<String, String> params);
	
	//=> ★ 알람 갯수 
	public int count(HashMap<String, String> params);

	//=> ★ 알람 목록
	public ArrayList<NotificationDto> list(HashMap<String, String> params);
	public ArrayList<NotificationDto> list(HashMap<String, String> params, HttpSession session);
}
