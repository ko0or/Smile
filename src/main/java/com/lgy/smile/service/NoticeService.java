package com.lgy.smile.service;

import java.net.InetAddress;
import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		
		
//		매퍼인터 페이스와 매퍼인터페이스를 구현한 객체를 가져온다
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);

//		pageNum(페이지 번호)의 값의 없으면
		if ( params.get("pageNum") == null ) {
//			1을 줘라
			params.put("pageNum", "1");
		}
		
		log.info("pageNum => " + params.get("pageNum"));
		log.info("pageNum Type => " + params.get("pageNum").getClass().getName());
		
		
		int page = Integer.parseInt( params.get("pageNum") );
		int s = page * 10 - 10;
		
		String start = String.valueOf(s);

		log.info("************ start => " + start);
		
		params.put("start", start );

		
		log.info("@# NoticeService.list() end");
		return dao.list(params);
	}


	@Override
	public void write(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.write() start");
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		
//		created애는 devUtils에 있는 getDate메소드를 사용할 것 이다.
		params.put("created", devUtils.getDate() );
//		user에는 8번을 사용할것이다(관리자 인덱스)
//		params.put("user",  );
		
		log.info("@# NoticeService.write() end");
		dao.write(params);
				
	}
	

	@Override
	public NoticeDto contentView(@RequestParam HashMap<String, String> params) {
		log.info("@# NoticeService.contentView() start");
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		log.info("@# NoticeService.contentView() end");
		return dao.contentView(params);
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
		
			
			dao.delete(params);
			log.info("@# NoticeService.delete() end");
		
		
	}
	@Override
	public int getCount() {
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		return dao.getCount();
	}


	@Override
	public void viewUp(HashMap<String, String> param) {

		log.info("@@@@@ => viewUp start @");
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) { ip = req.getRemoteAddr(); }
		
		param.put("ipaddr", ip);
		log.info("@ param 매개변수들 => " + param.toString() );
		
		log.info("@@@@@ => viewUp end @");
		
		NoticeMapperinterface dao = sqlSession.getMapper(NoticeMapperinterface.class);
		dao.viewUp(param);
		
	}




	
}
