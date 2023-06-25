package com.lgy.smile.service;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.controller.NotificationController;
import com.lgy.smile.dao.NotificationMapperInterface;
import com.lgy.smile.dto.NotificationDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService implements NotificationMapperInterface {
	
	/* ☆ 마이바티스 ☆ */
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;
	
	
	
	
	
	/* ★ 알람 갯수 가져오기 @Override */
	public int count(HashMap<String, String> params, HttpSession session) {
		
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session)); 
		
		return dao.count(params);
	}	
	
	
	
	/* ★ 알람 목록 가져오기 @Override */
	public ArrayList<NotificationDto> list(HashMap<String, String> params, HttpSession session) {		
		
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session)); 
		
		return dao.list(params);
	}
	
	
	
	/* ★ 알람 만들기 @Override */
	public void create(HashMap<String, String> params) {
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		
		dao.create(params);
	}

	
	
	/* ★ 알람 삭제하기 @Override */
	public void delete(HashMap<String, String> params) {
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		dao.delete(params);
	}



	
	/* ☆ 매퍼 호출용 */
	@Override public ArrayList<NotificationDto> list(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return null; }
	@Override public int count(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return -1; }

}
