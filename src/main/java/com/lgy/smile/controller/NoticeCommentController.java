package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.NoticeCommentDto;
import com.lgy.smile.service.NoticeCommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice/comment")
public class NoticeCommentController {
	@Autowired private NoticeCommentService commentService;
	@Autowired private DevUtils devutils;
	

	
	
	@PostMapping("/write")
	@ResponseBody
	public ResponseEntity<String> noticeWrite(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# write");
		
//		identity==null이냐는 분기처리를 한 이유는 ajax에서 댓글에는 identity를 사용하지 않았기 떄문에 null
		if (params.get("identity")==null) {
			// 그래서 그냥 댓글
			commentService.writecomment(params, session);
			
		} else {
			// 대댓글
			commentService.replaycomment(params, session);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("success");
		
	}
	
	
	// ★ notice(공지) 댓글 읽기
	@GetMapping("/read")
	public ResponseEntity< ArrayList<NoticeCommentDto> > noticeRead(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		
		//		 유저가 로그인 햇냐?
		if (devutils.getUserInfo(session) != null) {
			model.addAttribute("loginuser", devutils.getUserInfo(session));
		} else {model.addAttribute("loginuser", "noLogin"); } 
		
		return ResponseEntity.status(HttpStatus.OK).body( commentService.contentViewcomment(params) );
	}
	
	
	// ★ notice(공지) 댓글 수정
	@PostMapping("/edit")
	@ResponseBody
	public ResponseEntity<String> noticeEdit(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
			commentService.modifycomment(params, session);
//			model.addAttribute("loginuser", devutils.getUserInfo(session));
//			model.addAttribute("commentuser", NoticeCommentDto.ge)
		log.info("edit 들어오니?");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// ★ notice(공지) 댓글 삭제
	@GetMapping("/delete")
	public ResponseEntity<String> noticeDelete(@RequestParam HashMap<String, String> params, HttpSession session) {
		NoticeCommentDto dto = commentService.commentInfo(params);
		
		if ( dto.getIndex() == 0 ) {
			//=> ☆ 댓글 삭제시엔 해당 대댓글들도 삭제,
			params.put("group", devutils.intToString(dto.getGroup()));
			commentService.deletecommentByGroup(params, session);
			
		} else {
			//=> ☆ 대댓글 삭제시엔, 해당 대댓글만 삭제
			commentService.deletecommentByIdentity(params, session);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}

}
