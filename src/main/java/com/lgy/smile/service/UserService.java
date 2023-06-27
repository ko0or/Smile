package com.lgy.smile.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.UserMapperInterface;
import com.lgy.smile.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserService implements UserMapperInterface {

	@Autowired private SqlSession sqlSession;
	@Autowired private DevUtils devUtils;
	
//=========================== 회원 로그인 ===================================================== >
	
	// 로그인 처리를 위해 DB정보 조회 (SELECT 쿼리 실행)
	@Override
	public UserDto login(@RequestParam HashMap<String, String> params) {
		log.info("UserService ===> login ===> start");
		
		// UserMapperInterface 사용하여 DAO 객체 생성 (MyBatis 를 통해 DB 연결)
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		
		// login.jsp 의 폼태크에서 받은 id 와 password 값을 매개변수로 DAO 객체의 login 메소드 호출(리턴값 DTO)
		UserDto dto = userDao.login(params);
		
		log.info("UserService ===> login ===> end");
		return dto;
	}


//============== 회원가입 처리 ================================================================== >
	
	// 이메일 중복확인 (SELECT 쿼리 실행)
	@Override
	public UserDto isDuplicated(HashMap<String, String> params) {
		log.info("UserService ===> isDuplicated ===> start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		UserDto dto = userDao.isDuplicated(params);
		
		log.info("UserService ===> isDuplicated ===> end");
		return dto;
	}
	
	// 회원가입 처리 (INSERT 쿼리 실행)
	@Override
	public void register(HashMap<String, String> params) {
		log.info("UserService ===> register ===> start");
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		userDao.register(params);
		log.info("UserService ===> register ===> end");
	}

	
//========================= 카카오 간편 로그인 ======================================================= >
	
	
//=================== 임시 비밀번호 발급 ============================================================= >
	
	// 임시 비밀번호로 DB 정보 업데이트
	@Override
	public void tempPwd(HashMap<String, String> params) {
		log.info("UserService ===> tempPwd ===> start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		userDao.tempPwd(params);
		
		log.info("UserService ===> tempPwd ===> end");
	}
	
	
//=================== 회원정보 조회 및 수정 ============================================================= >


	@Override
	public void modify(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void modify(HashMap<String, String> params, HttpSession session) {
		log.info("UserService ===> modify ===> start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		UserDto user = new DevUtils().getUserInfo(session);
		params.put("id", user.getId() );
		log.info( params.toString() );
		userDao.modify(params);
		
		log.info("UserService ===> modify ===> end");
	}

	
//=================== 포인트 충전 =========================================================================== >

	// 입력된 포인트 금액(amount)을 쿼리로 DB에 업데이트
	@Override
	public void pointUp(HashMap<String, String> params) {
		log.info("UserService ===> pointUp ===> start");
		
		String id = params.get("id");			// 기준이 되는 이메일 주소
		String point = params.get("point");		// 추가할 포인트 금액
		log.info("id ===> " +id);
		log.info("point ===> " +point);
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		userDao.pointUp(params);
		
		log.info("UserService ===> pointUp ===> end");
	}
	
	@Override
	public String getPoint(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// identity 기준으로 현재 포인트 가져오기
	@Override
	public String getPoint(HashMap<String, String> params, HttpSession session) {
		log.info("UserService ===> getPoint ===> start");

		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		params.put("identity", devUtils.getUserIdentityToString(session));
		String point = userDao.getPoint(params);
		
		log.info("UserService ===> getPoint ===> end");
		return point;
	}

	
//========================= 회원 탈퇴처리 ====================================================================== >
	
	@Override
	public void delete(HashMap<String, String> params) {
		// TODO Auto-generated method stub
	}	

	// 회원탈퇴 처리
	@Override
	public void delete(HashMap<String, String> params, HttpSession session) {
		log.info("UserService ===> delete ===> start");
		
		UserMapperInterface userDao = sqlSession.getMapper(UserMapperInterface.class);
		userDao.delete(params);
		
		log.info("UserService ===> delete ===> end");
	}
}
