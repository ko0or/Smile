package com.lgy.smile.dao;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import com.lgy.smile.dto.UserDto;

public interface UserMapperInterface {
	
	public UserDto login(HashMap<String, String> params);
	public void modify(HashMap<String, String> params);
	public void modify(HashMap<String, String> params, HttpSession session);
	public void tempPwd(HashMap<String, String> params);
	
	public UserDto isDuplicated(HashMap<String, String> params);
	
	public void register(HashMap<String, String> params);
	
	public void delete(HashMap<String, String> params);
	public void delete(HashMap<String, String> params, HttpSession session);
	
	public void pointUp(HashMap<String, String> params);
	
	
	public String getPoint(HashMap<String, String> params);
	public String getPoint(HashMap<String, String> params, HttpSession session);
}






