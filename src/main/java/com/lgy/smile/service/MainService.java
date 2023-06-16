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
import com.lgy.smile.dto.MainBoardDto;

@Slf4j
@Service
public class MainService implements MainBoardMapperInterface {
	
	/* ☆ 마이바티스 ☆ */
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 댓글 생성,변경,삭제 서비스  ☆ */ 
	@Autowired private CommentService commentService;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	
	
	@Override
	public void write(HashMap<String, String> params) {

		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		
		params.put("date",  devUtils.getDate() );
		params.put("userPK", "3");
		//null , ${title} , ${content} , ${date} , 0 , ${userPK}
		
		dao.write(params);
		
	}



	@Override
	public ArrayList<MainBoardDto> list() {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		return dao.list();
		
	}



	@Override
	public void delete(@RequestParam HashMap<String, String> params) {

		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		dao.delete(params);
		
	}



	@Override
	public void modify(HashMap<String, String> params) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		dao.modify(params);
		
	}



	@Override
	public MainBoardDto content_view(HashMap<String, String> params) {
		
		MainBoardMapperInterface dao = sqlSession.getMapper(MainBoardMapperInterface.class);
		return dao.content_view(params);
	}
	
	

	
	
	
	
}
