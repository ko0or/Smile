package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.NoticeCommentDto;


public interface NoticeCommentMapperinterface {
	public int getCount();
	
	public void writecomment(@RequestParam HashMap<String, String> param);
	public void writecomment(HashMap<String, String> param, HttpSession session);
	
	public ArrayList<NoticeCommentDto> contentViewcomment(@RequestParam HashMap<String, String> param);
	
	public void modifycomment(@RequestParam HashMap<String, String> param);
	public void modifycomment(@RequestParam HashMap<String, String> param, HttpSession session);

	public void deletecommentByIdentity(HashMap<String, String> param);
	public void deletecommentByIdentity(HashMap<String, String> param, HttpSession session);
	public void deletecommentByGroup(HashMap<String, String> param);
	public void deletecommentByGroup(HashMap<String, String> param, HttpSession session);
	
//	댓글과 댓글 사이에 대댓글을 쓰기위해 공간을 비워 주기 위해 update문을 쓴 매퍼
	public void orignalindexcomment(@RequestParam HashMap<String, String> param);
//	대댓글을 쓰기 위한 매퍼
	public void replaycomment(@RequestParam HashMap<String, String> param);
	public void replaycomment(@RequestParam HashMap<String, String> param, HttpSession session);
	
//	대댓글을 쓰려면 해당 정보를 불러오기 위한 매퍼
	public NoticeCommentDto commentInfo(@RequestParam HashMap<String, String> param);
	
//	대댓글을 쓰면 그 댓글은 해당 댓글의 맨 밑에 가게 하기위한 매퍼
	public int commentnew(@RequestParam HashMap<String, String> param);
	
	
	


}
