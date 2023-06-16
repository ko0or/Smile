package com.lgy.smile.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.dao.NoticeCommentMapperinterface;
import com.lgy.smile.dto.NoticeCommentDto;
import com.lgy.smile.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeCommentService implements NoticeCommentMapperinterface {

	@Autowired SqlSession sqlSession;
	
	@Override
	public int getCount() {
		
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		return dao.getCount();
		
	}

	@Override
	public void writecomment(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.writecomment(param);
		
	}

	@Override
	public NoticeCommentDto contentViewcomment(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		NoticeCommentDto dto = dao.contentViewcomment(param);
		return dto;
	}

	@Override
	public void modifycomment(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao =sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.modifycomment(param);
		
	}

	@Override
	public void deletecomment(HashMap<String, String> param, HttpSession session) {
	
		//UserDto user = (UserDto) session.getAttribute("userInfo");		
		
		// 삭제버튼  href="delete?identity=숫자"
		// @RequsetParam  params 에 전달됌
		// 그렇게 전달받은걸 여기서 씀
		//if ( param.get("identity").equals(user.getIdentity()) == true ) {
			
			NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
			dao.deletecomment(param, session);
		// }
		
		
		
	}


}
