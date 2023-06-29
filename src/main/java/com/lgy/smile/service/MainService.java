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
	
	/* ☆ 마이바티스 ☆ */
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	
	
	@Override
	public boolean write(HashMap<String, String> params, HttpSession session) {

		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);		
		UserDto user = devUtils.getUserInfo(session);
		
		if ( user == null ) {
			log.info("☆ 글 작성 실패 => 비로그인상태");
			return false;
		}
		
		// 로그인상태라면 ..  세션으로부터 받은 정보를 추가해서 DB에 저장 -☆
		params.put("date",  devUtils.getDate() );
		params.put("userPK", String.valueOf( user.getIdentity() ) );
		
		dao.write(params);
		return true;
	}


	@Override
	public MainBoardDto content_view(HashMap<String, String> params, HttpSession session) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		MainBoardDto dto = dao.content_view(params);
		
		if ( user.getIdentity() == dto.getUser() ) {
			return dto;	
		}
		
		return null;
	}
	

	@Override
	public ArrayList<MainBoardDto> list(HashMap<String, String> params) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		
		return dao.list(params);
		
	}



	@Override
	public boolean delete(HashMap<String, String> params, HttpSession session) {

		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);

		if ( user == null ) {
			return false;
		}
		
		// 1. 게시글 번호로 SELECT 조회
		MainBoardDto dto = dao.content_view(params);
		// 2. 조회된 결과의 user 값을 확인
		int userPK = dto.getUser();
		
		// 3. 현재 로그인중인 회원의 식별번호 (유저테이블의 pk번호) 확인
		user.getIdentity();
		
		// 4. 현재 로그인중인 회원 식별번호가 게시글 조회시 나온 user 외래키와 서로 일치하는지 확인
		if ( user.getIdentity() == userPK) { 		
			// 현재 로그인중인 회원번호가,  삭제하고자하는 게시글 테이블의 pk 번호와 일치하다면 삭제
			dao.delete(params);
			return true;
		}
		
		return false;
	}



	@Override
	public boolean modify(HashMap<String, String> params, HttpSession session) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		
		dao.modify(params);
		return true;
	}


	@Override
	public void like_toggle(HashMap<String, String> params, HttpSession session) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session));
		log.info("@@@좋아요 토글 테스트 시작 ==> " + params.toString());
		dao.like_toggle(params);
		
	}



	
	
	
	
	

	@Override
	public boolean write(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean modify(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean delete(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public MainBoardDto content_view(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void like_toggle(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<MainBoardDto> list() {
		// TODO Auto-generated method stub
		return null;
	}




	
	

	
	
	
	
}
