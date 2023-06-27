package com.lgy.smile.service;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.service.CommentService;

import lombok.extern.slf4j.Slf4j;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.MainBoardMapperInterface;
import com.lgy.smile.dao.MainCommentMapperInterface;
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.MainCommentDto;
import com.lgy.smile.dto.UserDto;

@Slf4j
@Service
public class MainCommentService implements MainCommentMapperInterface {
	
	/* ☆ 마이바티스 ☆ */
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 댓글 생성,변경,삭제 서비스  ☆ */ 
	@Autowired private CommentService commentService;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	
	
	
	

	@Override
	public ArrayList<MainCommentDto> list(HashMap<String, String> params) {
		
		//=> ★ 댓글 목록 보여주기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		return dao.list(params);
		
	}

	@Override
	public int authorIdentityCheck(HashMap<String, String> params, HttpSession session) {
		
		//=> ★ 댓글 작성자 PK값 (identity컬럼) 반환하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		return dao.authorIdentityCheck(params);
		
	}
	




	@Override
	public void write(HashMap<String, String> params, HttpSession session) {

		//=> ★ 댓글 새로 작성하기
		params.put("created", devUtils.getDate());
		params.put("user", devUtils.getUserIdentityToString(session));
		
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		if ( devUtils.isLogin(session) == true) {
			//=> ★ 로그인상태일때만 동작
			dao.write(params);
		}
	}




	@Override
	public void modify(HashMap<String, String> params, HttpSession session) {

		//=> ★ 댓글 내용 수정하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		
		int userIdentity = devUtils.getUserInfo(session).getIdentity();
		int commentAuthorUserIdentity = dao.authorIdentityCheck(params);
		if ( userIdentity == commentAuthorUserIdentity ) {
			
			dao.modify(params);
			log.info("@ => 로그인된 유저와 댓글 작성자가 일치하여 댓글을 수정했습니다.");
			return;
		}
		log.info("@ => 수정 실패! 댓글 작성자 본인이 아닙니다.");
	}

	
	
	


	@Override
	public void deleteByIdentity(HashMap<String, String> params, HttpSession session) {

		//=> ★ 등록된 댓글 삭제하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		
		int userIdentity = devUtils.getUserInfo(session).getIdentity();
		int commentAuthorUserIdentity = dao.authorIdentityCheck(params);
		if ( userIdentity == commentAuthorUserIdentity ) {
			
			dao.deleteByIdentity(params);
			return;
		}
	}
	
	@Override
	public void deleteByGroup(HashMap<String, String> params, HttpSession session) {
		
		//=> ★ 등록된 댓글 삭제하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		
		int userIdentity = devUtils.getUserInfo(session).getIdentity();
		int commentAuthorUserIdentity = dao.authorIdentityCheck(params);
		if ( userIdentity == commentAuthorUserIdentity ) {
			dao.deleteByGroup(params);
			return;
		}
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public MainCommentDto commentInfo(HashMap<String, String> params, HttpSession session) {
		
		//=> ★ (대댓글을 작성하기 위해 필요) 기존 댓글의 그룹번호, 그리고 인덱스(깊이) 정보를 가져와주는 기능
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		if ( params.get("replyTargetIdentity") == null ) { params.put("replyTargetIdentity", params.get("identity")); }
		return dao.commentInfo(params);
	}
	
	
	
	@Override
	public void replyPush(HashMap<String, String> params, HttpSession session) {
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		if ( devUtils.isLogin(session) == true ) {
			//=> ★ 로그인상태일때만 동작
			dao.replyPush(params);
		}		
	}

	
	
	@Override
	public void replyWrite(HashMap<String, String> params, HttpSession session) {
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		if ( devUtils.isLogin(session) == true ) {
			//=> ★ 로그인상태일때만 동작
			
			params.put("created", devUtils.getDate());
			params.put("user", devUtils.getUserIdentityToString(session));
			
			MainCommentDto dto = dao.commentInfo(params);
			
			params.put("group", devUtils.intToString(dto.getGroup()) );
			params.put("index", devUtils.intToString(dto.getIndex()) );
			params.put("target_user", devUtils.intToString(dto.getUser()) );
			

			if ( dto.getIndex() == 0 ) {
				//=> ☆ 그냥 댓글에 대댓글 작성할때
				params.replace("index", devUtils.intToString(dao.replyLastIndex(params)) ); 
				dao.replyWrite(params);
				
			} else {
				//=> ☆ 대댓글에 대댓글을 작성하는 경우
				dao.replyPush(params);
				dao.replyWrite(params);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* ☆ 매퍼 오버라이딩 + 오버로딩 */
	@Override public void write(HashMap<String, String> params) 	{ 		/* TODO Auto-generated method stub */	 	}
	@Override public void modify(HashMap<String, String> params) 	{	 	/* TODO Auto-generated method stub */		}
	@Override public void deleteByIdentity(HashMap<String, String> params) 	{ 		/* TODO Auto-generated method stub */		}
	@Override public void deleteByGroup(HashMap<String, String> params) 	{ 		/* TODO Auto-generated method stub */		}
	@Override public int authorIdentityCheck(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return -1; }
	@Override public MainCommentDto commentInfo(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return null; }
	@Override public void replyWrite(HashMap<String, String> params) { /* TODO Auto-generated method stub */ }	
	@Override public void replyPush(HashMap<String, String> params) { /* TODO Auto-generated method stub */ }	
	@Override public int replyLastIndex(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return -1; }	
}
