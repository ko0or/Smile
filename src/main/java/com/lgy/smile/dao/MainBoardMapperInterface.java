package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.UserDto;

public interface MainBoardMapperInterface {
	
	public ArrayList<MainBoardDto> list();
	public ArrayList<MainBoardDto> list(HashMap<String, String> params);
	
	public boolean write(HashMap<String, String> params);
	public boolean write(HashMap<String, String> params, HttpSession session);
	
	public boolean modify (HashMap<String, String> params);
	public boolean modify (HashMap<String, String> params, HttpSession session);

	public boolean delete(HashMap<String, String> params);
	public boolean delete(HashMap<String, String> params, HttpSession session);
	
	public MainBoardDto  content_view(HashMap<String, String> params);
	public MainBoardDto  content_view(HashMap<String, String> params, HttpSession session);
	
	public void like_toggle(HashMap<String, String> params);
	public void like_toggle(HashMap<String, String> params, HttpSession session);
}
