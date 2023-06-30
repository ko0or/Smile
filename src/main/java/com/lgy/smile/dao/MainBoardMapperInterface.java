package com.lgy.smile.dao;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.lgy.smile.dto.MainBoardDto;

public interface MainBoardMapperInterface {

	//=> ★ 게시글 목록 보여주기
	public ArrayList<MainBoardDto> list();
	public ArrayList<MainBoardDto> list(HashMap<String, String> params);

	//=> ★ 게시글 작성 처리
	public boolean write(HashMap<String, String> params);
	public boolean write(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 게시글 수정 처리
	public boolean modify (HashMap<String, String> params);
	public boolean modify (HashMap<String, String> params, HttpSession session);

	//=> ★ 게시글 삭제 처리
	public boolean delete(HashMap<String, String> params);
	public boolean delete(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 게시글 수정 화면 ( 기존 작성된 내용 찾아서 보여주기 ) 
	public MainBoardDto  content_view(HashMap<String, String> params);
	public MainBoardDto  content_view(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 게시글 좋아요 ( 토글방식 )
	public void like_toggle(HashMap<String, String> params);
	public void like_toggle(HashMap<String, String> params, HttpSession session);
}
