package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.lgy.smile.dto.NotificationDto;

public interface NotificationMapperInterface {
	
	public int count(HashMap<String, String> params);
	
	public ArrayList<NotificationDto> list(HashMap<String, String> params);
	public ArrayList<NotificationDto> list(HashMap<String, String> params, HttpSession session);
	
	public void create(HashMap<String, String> params);
	public void delete(HashMap<String, String> params);
	
	
}
