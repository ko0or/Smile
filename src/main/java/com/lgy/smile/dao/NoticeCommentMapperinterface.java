package com.lgy.smile.dao;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.NoticeCommentDto;


public interface NoticeCommentMapperinterface {
	public int getCount();
	public void writecomment(@RequestParam HashMap<String, String> param);
	public NoticeCommentDto contentViewcomment(@RequestParam HashMap<String, String> param);
	public void modifycomment(@RequestParam HashMap<String, String> param);
	//public void deletecomment(@RequestParam HashMap<String, String> param);
	public void deletecomment(HashMap<String, String> param, HttpSession session);

}
