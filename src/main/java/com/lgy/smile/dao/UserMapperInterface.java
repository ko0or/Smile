package com.lgy.smile.dao;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.UserDto;

public interface UserMapperInterface {
	
	public UserDto login(HashMap<String, String> params);
	
	public void modify(HashMap<String, String> params);
	public void modify(HashMap<String, String> params, HttpSession session);
	
}






