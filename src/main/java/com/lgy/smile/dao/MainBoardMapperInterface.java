package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.UserDto;

public interface MainBoardMapperInterface {
	
	public void write(@RequestParam HashMap<String, String> params);
	public MainBoardDto  content_view(@RequestParam HashMap<String, String> params);
	public ArrayList<MainBoardDto> list();
	public void modify (@RequestParam HashMap<String, String> params);
	public void delete(@RequestParam HashMap<String, String> params);
}
