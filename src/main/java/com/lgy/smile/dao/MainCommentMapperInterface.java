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
	
	
	//=> ★ 댓글 작성자 PK값 (identity컬럼) 반환하기
	public int authorIdentityCheck(HashMap<String, String> params);
	public int authorIdentityCheck(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 댓글 정보 DTO 반환하기
	public MainCommentDto commentInfo(HashMap<String, String> params);
	public MainCommentDto commentInfo(HashMap<String, String> params, HttpSession session);
	
	
	//=> ★ 댓글 새로 작성하기
	public void write(HashMap<String, String> params);
	public void write(HashMap<String, String> params, HttpSession session);	
	
	
	//=> ★ 댓글 내용 수정하기
	public void modify(HashMap<String, String> params);
	public void modify(HashMap<String, String> params, HttpSession session);	
	
	
	//=> ★ 등록된 댓글 삭제하기
	public void delete(HashMap<String, String> params);
	public void delete(HashMap<String, String> params, HttpSession session);

	
	
	
	
	//=> ★ 대댓글 관련 (push: 기존 index 한칸씩 뒤로 밀어내기)
	public void replyPush(HashMap<String, String> params);
	public void replyPush(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 대댓글 관련 (write: 밀어내고 생긴 틈 사이로 대댓글 넣어주기)
	public void replyWrite(HashMap<String, String> params);
	public void replyWrite(HashMap<String, String> params, HttpSession session);
	
	//=> ★ 대댓글 관련 (lastIndex: 해당 그룹의 index 번호중 가장 마지막 번호 가져오기)	
	public int replyLastIndex(HashMap<String, String> params);

}
