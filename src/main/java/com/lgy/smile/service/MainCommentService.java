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

	/* ☆ 매퍼 오버라이딩 + 오버로딩 */
	@Override public void write(HashMap<String, String> params) 	{ 		/* TODO Auto-generated method stub */	 	}
	@Override public void modify(HashMap<String, String> params) 	{	 	/* TODO Auto-generated method stub */		}
	@Override public void delete(HashMap<String, String> params) 	{ 		/* TODO Auto-generated method stub */		}

	
	
	
	

	@Override
	public ArrayList<MainCommentDto> list(HashMap<String, String> params) {
		
		//=> ★ 댓글 목록 보여주기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		return dao.list(params);
		
	}






	@Override
	public void write(HashMap<String, String> params, HttpSession session) {

		//=> ★ 댓글 새로 작성하기
		params.put("created", devUtils.getDate());
		params.put("user", devUtils.getUserIdentityToString(session));
		
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		dao.write(params);
		
	}




	@Override
	public void modify(HashMap<String, String> params, HttpSession session) {

		//=> ★ 댓글 내용 수정하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		dao.modify(params);
	}

	
	
	


	@Override
	public void delete(HashMap<String, String> params, HttpSession session) {

		//=> ★ 등록된 댓글 삭제하기
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		log.info("@# delete start()");
		dao.delete(params);
		log.info("@# delete end()");
	}
}
