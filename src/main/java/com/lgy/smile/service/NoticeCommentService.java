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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeCommentService implements NoticeCommentMapperinterface {

	@Autowired SqlSession sqlSession;
	@Autowired DevUtils devUtils;
	
//	@Override
//	public int getCount(int comment) {
//		
//		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
//		return dao.getCount("com.lgy.smile.dao.mapperNoticeCommentDao.getCount",comment);
//		
//	}

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
	
		int loginuser=devUtils.getUserInfo(session).getIdentity();
		ArrayList<NoticeCommentDto> commentuser= dao.contentViewcomment(param);
		//if (loginuser == dao.contentViewcomment(param)) {
			
			dao.modifycomment(param);
		//}
		
		
	}

	@Override
	public void deletecomment(HashMap<String, String> param, HttpSession session) {
			
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.deletecomment(param);
		
	}

	
	@Override
	public ArrayList<NoticeCommentDto> replaycomment(HashMap<String, String> param, HttpSession session) {
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		param.put("nickname", String.valueOf(devUtils.getUserInfo(session).getNickname()) );
		
		ArrayList<NoticeCommentDto> dtos = dao.replaycomment(param, session);
		param.put("user",  String.valueOf( dtos.get(0).getUser() ) );
		
		return dao.replaycomment(param, session);
	
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

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}

	

	


