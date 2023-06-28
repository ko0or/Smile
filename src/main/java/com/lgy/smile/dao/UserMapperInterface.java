package com.lgy.smile.dao;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.dto.UserDto;

public interface UserMapperInterface {
	
	// 로그인 처리 (id 값을 기준으로 DB조회 -> DTO 리턴)
	public UserDto login(HashMap<String, String> params);
	
	// 회원가입 처리 (파라미터 값을 받아서 생성 쿼리 실행) 
	public void register(HashMap<String, String> params);

	// 회원가입 처리 (id 값을 기준으로 DB조회 -> DTO 리턴) => 사실상 login 과 중복
	public UserDto isDuplicated(HashMap<String, String> params);
	
	// 회원가입 처리 (id 기준으로 암호화된 비밀번호로 업데이트) 
	public void tempPwd(HashMap<String, String> params);
	
	// 포인트 충전 처리 (id 기준으로 포인트 금액 업데이트)
	public void pointUp(HashMap<String, String> params);
	
	// 포인트 가져오기 (identity 값을 기준으로 DB조회 -> point 를 String 으로 리턴)
	public String getPoint(HashMap<String, String> params);
	public String getPoint(HashMap<String, String> params, HttpSession session);
	
	// 회원정보 수정 처리 (id 기준으로 비밀번호와 닉네임 업데이트)
	public void modify(HashMap<String, String> params);
	public void modify(HashMap<String, String> params, HttpSession session);
	
	// 회원정보 삭제 처리 (id 기준으로 해당 행 삭제)
	public void delete(HashMap<String, String> params);
	public void delete(HashMap<String, String> params, HttpSession session);
	
	// 프로필 사진 업로드 처리 (id 기준으로 이미지 업로드)
	public boolean write(@RequestParam HashMap<String, String> params);
	public boolean write(@RequestParam HashMap<String, String> params, MultipartFile[] uploadFile, HttpSession session);
	
	// 프로필 업로드 후 프로필 이미지 경로 가져오기
	public String getProfilePath(HashMap<String, String> params);
	public String getProfilePath(HashMap<String, String> params, HttpSession session);
}






