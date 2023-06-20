package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.MainCommentDto;
import com.lgy.smile.dto.UserDto;

public interface MainCommentMapperInterface {
	
	//=> ★ 댓글 목록 보여주기
	public ArrayList<MainCommentDto> list(HashMap<String, String> params);
	
	
	//=> ★ 댓글 새로 작성하기
	public void write(HashMap<String, String> params);
	public void write(HashMap<String, String> params, HttpSession session);
	
	
	//=> ★ 댓글 내용 수정하기
	public void modify(HashMap<String, String> params);
	public void modify(HashMap<String, String> params, HttpSession session);
	
	
	//=> ★ 등록된 댓글 삭제하기
	public void delete(HashMap<String, String> params);
	public void delete(HashMap<String, String> params, HttpSession session);
	
}
