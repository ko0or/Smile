package com.lgy.smile.service;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentService {
	
	/* ☆ 게시글 타입 MAIN(피드),  TRADE(중고거래),  NOTICE(공지사항) ☆ */ 
	enum boardType { MAIN , TRADE , NOTICE }
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	private DevUtils devUtils;

	
	
	// ★ 댓글 생성 (세션:로그인여부 확인,   보드타입: 어디 게시판에 적용할지) ==================== >>
	public void create(HttpSession session, boardType type) {

		
		
	}

	
	// ★ 댓글 수정 (세션:로그인여부 확인,   보드타입: 어디 게시판에 적용할지) ==================== >>
	public void update(HttpSession session, boardType type) {
		
		
		
	}

	
	// ★ 댓글 삭제 (세션:로그인여부 확인,   보드타입: 어디 게시판에 적용할지) ==================== >>
	public void delete(HttpSession session, boardType type) {
		
		
		
	}
}
