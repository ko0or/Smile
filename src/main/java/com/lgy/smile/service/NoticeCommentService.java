package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.NoticeCommentMapperinterface;
import com.lgy.smile.dto.NoticeCommentDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeCommentService implements NoticeCommentMapperinterface {

	@Autowired SqlSession sqlSession;
	@Autowired DevUtils devUtils;
	

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
			
			dao.modifycomment(param);
		
		
	}

	@Override
	public void deletecommentByIdentity(HashMap<String, String> param, HttpSession session) {
			
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.deletecommentByIdentity(param);
		
	}
	@Override
	public void deletecommentByGroup(HashMap<String, String> param, HttpSession session) {
		
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.deletecommentByGroup(param);
		
	}
	@Override
	public void orignalindexcomment(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		dao.orignalindexcomment(param);
		
	}
	
	@Override
	public void replaycomment(HashMap<String, String> param, HttpSession session) {
		
		log.info("@@ " + param.toString() );
		
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		param.put("created", devUtils.getDate());
		
		//param.put("user", String.valueOf(devUtils.getUserInfo(session).getIdentity()) );
//		위에 주석 문이랑 같은 거임
		param.put("user", devUtils.getUserIdentityToString(session) );
		
//		dto에 commentInfo를 연결해준다 그래서 commentInfo도 dto로 받는 것이다.
		NoticeCommentDto dto = dao.commentInfo(param);
		log.info("@@ " + dto.toString() );
		
//		param.put에 다 넣어줌 기본으로 가지고 오는 애들(identity,content,board) 제외하고 닉네임은 굳이 필요가 없음 ajax에서 설정 할것임
		param.put("group", String.valueOf(dto.getGroup()) );
		param.put("index", String.valueOf(dto.getIndex()) );
		param.put("target_user", String.valueOf(dto.getUser()) );
		
//		인덱스가 대댓글임 그 대댓글이 0이라는건 대댓글이 없다는 거임
		if ( dto.getIndex() == 0 ) {
			// 원본 댓글에 바로 댓글을 달고자 한다면
//			인덱스를 바꾼다 
//			다만 String.valueOf( dao.commentnew(param))를 사용한 이유는 commentnew메소드는 int타입이기때문에 String타입으로 강제로 변경해야index를 넣을 수 있음
			param.replace("index", String.valueOf( dao.commentnew(param)) );  
			dao.replaycomment(param); //넣기
			
		} else {
			// 원본 댓글의 인덱스가 0이 아니라면 (= 대댓글에 대댓글을 달고자하면)
			dao.orignalindexcomment(param);//공간을 비워주고
			dao.replaycomment(param);//넣기
		}
	}

	
	
	@Override
	public NoticeCommentDto commentInfo(@RequestParam HashMap<String, String> param) {
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		return dao.commentInfo(param);
	}
	@Override
	public int commentnew(HashMap<String, String> param) {
		NoticeCommentMapperinterface dao = sqlSession.getMapper(NoticeCommentMapperinterface.class);
		return dao.commentnew(param);			
	}
	
	
	@Override
	public void modifycomment(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletecommentByIdentity(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deletecommentByGroup(HashMap<String, String> param) {
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

	@Override
	public void replaycomment(HashMap<String, String> param) {
		// TODO Auto-generated method stub
	}



}

	

	


