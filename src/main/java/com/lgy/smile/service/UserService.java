package com.lgy.smile.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.UserMapperInterface;
import com.lgy.smile.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserService implements UserMapperInterface {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserDto login(@RequestParam HashMap<String, String> params) {
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		UserDto dto = userDao.login( params );
		
		return dto;
	}

	
	
	@Override
	public void modify(HashMap<String, String> params, HttpSession session) {
		log.info("UserService ===> modify start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		
		log.info("UserService ===> modify ===> userDao ===> " + userDao);
		
		UserDto user = new DevUtils().getUserInfo(session);
		log.info("UserService ===> modify ===> user ===> " + user);
		
		params.put("id", user.getId() );
		log.info( params.toString() );
		
		userDao.modify(params);
		
		log.info("UserService ===> modify end");
	}

	@Override
	public void modify(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	
	// ☆ 유저 정보조회 기능 필요 ( pk로 검색 )
	// public 유저DTO findByPK(int userPK) { ... return 유저dto  }
	
	
	
	
	// ☆ 유저 정보조회 기능 필요 ( 닉네임으로 검색 )
	// public 유저DTO findByNickname(String nickName) { ... return 유저dto  }	
	
	
	
	
	// ☆ "중복여부체크" 눌렀을때,  해당 이메일ID 와 닉네임이 사용중인지 확인후 리턴하는 메소드 필요
	// public boolean isDuplicated(Model model) {  ... 조건문 if-else로 return 분기처리 true/false }
	@Override
	public UserDto isDuplicated(HashMap<String, String> params) {
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		UserDto dto = userDao.isDuplicated(params);
		
		return dto;
	}


	
	// ☆ user(유저) 회원가입을 처리하는 메소드 필요	
	// 위에서 만든 isDuplicated 메소드가 false 일때 진행 ex->  if ( isDuplicated(model) == true ) { ... }
	// dao 객체 호출해서 insert 쿼리진행
	@Override
	public void register(HashMap<String, String> params) {
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		userDao.register(params);
	}

	@Override
	public void delete(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void delete(HashMap<String, String> params, HttpSession session) {
		log.info("UserService ===> delete ===> start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
//		UserDto user = (UserDto) session.getAttribute("userInfo");
//		log.info("user ===> "+user);
		
//		String id = params.get("id");
//		String pwd = params.get("password");
//		log.info("id ===> "+id);
//		log.info("pwd ===> "+pwd);
		
		userDao.delete(params);
		log.info("UserService ===> delete ===> end");
	}

	
/*
	  	☆ 로그인 버튼 눌렀을때 AJAX 방식으로 처리하는 컨트롤러 필요
  		＃ 예시
  		
  		ResponseEntity<Void> 로그인체크 () {
		
			DAO객체 dao참조변수 = new DAO객체();
		
			if ( dao참조변수.findByAccount(받은아디, 받은비번) == true ) {
				return new ResponseEntity<>(HttpStatus.OK); // HTTP Status 200 (성공)
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // HTTP Status 400 (잘못된 요청)
			}
	  	}
	  	
 */
	
}
