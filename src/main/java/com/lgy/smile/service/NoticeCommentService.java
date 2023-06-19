package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.NoticeCommentMapperinterface;
import com.lgy.smile.dto.NoticeCommentDto;
import com.lgy.smile.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeCommentService implements NoticeCommentMapperinterface {

	@Autowired SqlSession sqlSession;
	@Autowired DevUtils devUtils;
	
	@Override
	public int getCount() {
		
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		return dao.getCount();
		
	}

	@Override
	public void writecomment(HashMap<String, String> param, HttpSession session) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		
		param.put("created", devUtils.getDate());
		param.put("user", String.valueOf(devUtils.getUserInfo(session).getIdentity()) );
		log.info("@# => " + param.toString());
		
		dao.writecomment(param);
		
	}

	@Override
	public ArrayList<NoticeCommentDto> contentViewcomment(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		return dao.contentViewcomment(param);
	}

	@Override
	public void modifycomment(HashMap<String, String> param, HttpSession session) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.modifycomment(param);
		
	}

	@Override
	public void deletecomment(HashMap<String, String> param, HttpSession session) {
			
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.deletecomment(param);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void modifycomment(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletecomment(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writecomment(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		
	}


}
