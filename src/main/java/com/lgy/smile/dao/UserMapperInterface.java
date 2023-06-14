package com.lgy.smile.dao;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.UserDto;

public interface UserMapperInterface {
	
	public UserDto login(@RequestParam HashMap<String, String> params);
	
}
