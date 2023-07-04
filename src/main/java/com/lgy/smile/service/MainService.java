package com.lgy.smile.service;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.MainBoardMapperInterface;
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.UserDto;

@Slf4j
@Service
public class MainService implements MainBoardMapperInterface {
	
	@Autowired private SqlSession sqlSession;
	@Autowired private DevUtils devUtils;
	
//========================================================================================
	
	
	/* ★ 게시글 작성  */ @Override
	public boolean write(HashMap<String, String> params, HttpSession session) {

		//=> ☆ 객체 생성 (유저 정보 포함해서)
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);		
		UserDto user = devUtils.getUserInfo(session);
		
		//=> ☆ 로그인 아닐경우 진행 x
		if ( user == null ) { return false; }
		
		//=> ☆로그인된 상태라면, 현재 날짜와 로그인된 유저 정보를 저장하고 
		params.put("date",  devUtils.getDate() );
		params.put("userPK", String.valueOf( user.getIdentity() ) );
		
		//=> ☆ 저장된 정보로 게시글 작성
		dao.write(params);
		return true;
	}


	/* ★ 게시글 목록  */ @Override
	public ArrayList<MainBoardDto> list(HashMap<String, String> params) {
		
		//=> ☆ 게시글 목록 가져오기 
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);		
		return dao.list(params);
		
	}


	/* ★ 게시글 화면 보여주기 (기존 작성된 내용) */ @Override
	public MainBoardDto content_view(HashMap<String, String> params, HttpSession session) {
		
		//=> ☆ 객체 생성 (유저 정보, 게시글 정보) 포함해서
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		MainBoardDto dto = dao.content_view(params);
		
		//=> ☆ 게시글 작성자와,  현재 로그인된 유저가 일치할때만 보여주고
		if ( user.getIdentity() == dto.getUser() ) { return dto;	 }
		
		//=> ☆ 서로 다를경우엔 안보여줌
		return null;
	}
	
	/* ★ 게시글 수정  */ @Override
	public boolean modify(HashMap<String, String> params, HttpSession session) {
		
		//=> ☆ 객체 생성 (유저 정보, 게시글 정보) 포함해서
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		MainBoardDto dto = dao.content_view(params);
		
		//=> ☆ 로그인 상태라면,  로그인 유저 정보와  게시글 작성자 정보를 가져오고
		int loginUserIdentity = user.getIdentity();
		int authorUserIdentity = dto.getUser(); 
		
		//=> ☆ 가져온 정보가 서로 일치할때만 수정되도록 처리
		if ( loginUserIdentity  == authorUserIdentity ) { 		
			dao.modify(params);
			return true;
		}
		
		//=> ☆ 일치하지않다면, 수정 x
		return false;
	}
	
	
	/* ★ 게시글 삭제  */ @Override
	public boolean delete(HashMap<String, String> params, HttpSession session) {

		//=> ☆ 객체 생성 (유저 정보, 게시글 정보) 포함해서
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		MainBoardDto dto = dao.content_view(params);

		//=> ☆ 비로그인상태라면 진행 x
		if ( user == null ) { return false; }
		
		//=> ☆ 로그인 상태라면,  로그인 유저 정보와  게시글 작성자 정보를 가져오고
		int loginUserIdentity = user.getIdentity();
		int authorUserIdentity = dto.getUser(); 
		
		//=> ☆ 가져온 정보가 서로 일치할때만 삭제되도록 처리
		if ( loginUserIdentity  == authorUserIdentity ) { 		
			dao.delete(params);
			return true;
		}

		//=> ☆ 일치하지않다면, 삭제 x
		return false;
	}




	
	
	
	


	/* ★ 게시글 좋아요 (토글)  */ @Override
	public void like_toggle(HashMap<String, String> params, HttpSession session) {
		
		//=> ☆ 객체 생성 (유저 정보) 포함해서
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session));

		//=> ☆ 로그인 상태일때만 토글되도록 처리
		if ( devUtils.isLogin(session) == true ) { dao.like_toggle(params); }
	}



	
	
	
	
	
	
	
	
	
	
/* ======================================================================================== 
		
	#. 메소드 오버로딩용 (매퍼 연동)
		 
======================================================================================== */
@Override  public boolean write(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return false; }
@Override public boolean modify(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return false; }
@Override public boolean delete(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return false; }
@Override public MainBoardDto content_view(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return null; }
@Override public void like_toggle(HashMap<String, String> params) { /* TODO Auto-generated method stub */  }
@Override public ArrayList<MainBoardDto> list() { /* TODO Auto-generated method stub */ return null; }	
}
