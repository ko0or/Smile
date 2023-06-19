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

	


	@Override
	public ArrayList<MainCommentDto> list(HashMap<String, String> params) {
		
		MainCommentMapperInterface dao = sqlSession.getMapper(MainCommentMapperInterface.class);
		return dao.list(params);
		
	}

	
}
