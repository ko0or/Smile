package com.lgy.smile.service;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.service.CommentService;

import lombok.extern.slf4j.Slf4j;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.NoticeMapperinterface;
import com.lgy.smile.dto.NoticeDto;
import com.lgy.smile.dto.UserDto;


@Service
@Slf4j
public class NoticeService implements NoticeMapperinterface {
	
	/* ☆ 댓글 생성,변경,삭제 서비스  ☆ */ 
	@Autowired private CommentService commentService;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	@Autowired SqlSession sqlSession;
	
	// CRUD
	
	// Create 공지 작성
	
	// Read 공지 조회
	
	// Update 공지 수정
	
	// Delete 공지 삭제
	
	
	@Override
	public ArrayList<NoticeDto> list(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.list() start");
		
		
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);

		log.info("pageNum => " + params.get("pageNum"));
		log.info("pageNum Type => " + params.get("pageNum").getClass().getName());
		
		
		int page = Integer.parseInt( params.get("pageNum") );
		int s = page * 10 - 10;
		int e = page * 10;
		
		String start = String.valueOf(s);
		String end = String.valueOf(e);

		
		params.put("start", start );
		params.put("end", end );
		
		log.info("@# NoticeService.list() end");
		return dao.list(params);
	}


	@Override
	public void write(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.write() start");
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		
		
		//null , #{title} , #{content} , #{created} , 0 , #{author} , #{user}
		params.put("created", devUtils.getDate() );
		params.put("author", "testUser" );//수정
		params.put("user", "3" );//수정
		
		log.info("@# NoticeService.write() end");
		dao.write(params);
				
	}
	

	@Override
	public NoticeDto contentView(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.contentView() start");
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		NoticeDto dto = dao.contentView(params);
		log.info("@# NoticeService.contentView() end");
		return dto;
	}


	@Override
	public void modify(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.modify() start");
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		dao.modify(params);
		log.info("@# NoticeService.modify() end");
		
	}


	@Override
	public void delete(@RequestParam HashMap<String, String> params) {
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		log.info("@# NoticeService.delete() start");
		
		//if ( userDto.getRole().equals("admin") == true ) {
			
			dao.delete(params);
			log.info("@# NoticeService.delete() end");
		
		//}
		
	}


	
}
