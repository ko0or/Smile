package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.NoticeDto;

public interface NoticeMapperinterface {
	public ArrayList<NoticeDto> list(@RequestParam HashMap<String, String> params);
	public void write(@RequestParam HashMap<String, String> param);
	public NoticeDto contentView(@RequestParam HashMap<String, String> param);
	public NoticeDto contentView(@RequestParam HashMap<String, String> param, HttpSession session);
	public void modify(@RequestParam HashMap<String, String> param);
	public void delete(@RequestParam HashMap<String, String> param);
	public void viewUp(@RequestParam HashMap<String, String> param);
	
	public int getCount();
	public int getCount(HashMap<String, String> param);//검색기능 후 페이징처리를 하기위해
	
	public int confirmedCheck(@RequestParam HashMap<String, String> param);//새 게시글 확인여부
	public void confirmedUpdate(@RequestParam HashMap<String, String> param);//게시글 눌럿을때 유저 pk등록
	
}
